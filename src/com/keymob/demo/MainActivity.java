package com.keymob.demo;

import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.keymob.ads.AdManager;
import com.keymob.core.AdTypes;
import com.keymob.core.BannerPositions;
import com.keymob.core.BannerSizes;
import com.keymob.core.IAdEventListener;
import com.keymob.core.IInterstitialPlatform;
import com.keymob.core.PlatformAdapter;

public class MainActivity extends Activity {
	public static final String TAG = "com.keymob.ads";
	
	private String readFileToString(String fileName)
    {    
	  String res;
      try
      {
    	  InputStream is=getAssets().open(fileName);
            byte[] buf = new byte[is.available()];         
            is.read(buf);
            res = new String(buf,"UTF-8");     
            is.close();
      } catch (Exception e)
      {
         res="";  
      }
       return(res);   
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String config=readFileToString("ads.json");
		
		if(config==null||config.equals("")){
			Log.d(TAG, "error config"+config);
			return ;
		}else{
				try {
					AdManager.getInstance().initFromJSON(this,config,new  AdEventListener());
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}
	public void clickFull(View view) {
		if(AdManager.getInstance().isInterstitialReady()){
			AdManager.getInstance().showInterstitial();
		}else{
			AdManager.getInstance().loadInterstitial();
		}
	}
	public void clickTop(View view) {
		AdManager.getInstance().showRelationBanner(BannerSizes.BANNER, BannerPositions.TOP_CENTER,0);
	}
	public void clickBottom(View view) {
		AdManager.getInstance().showRelationBanner(BannerSizes.BANNER, BannerPositions.BOTTOM_CENTER,88);
	}
	public void clickTop100(View view) {
		AdManager.getInstance().showBannerABS(BannerSizes.BANNER, 0, 200);
	}
	public void clickVideo(View view) {
		if(AdManager.getInstance().isVideoReady()){
			AdManager.getInstance().showVideo();
		}else{
			AdManager.getInstance().loadVideo();
		}
	}
	public void clickAppWall(View view) {
		if(AdManager.getInstance().isAppWallReady()){
			AdManager.getInstance().showAppWall();
		}else{
			AdManager.getInstance().loadAppWall();
		}
	}
	public void hideBanner(View view) {
		AdManager.getInstance().removeBanner();
	}
	
	class AdEventListener implements IAdEventListener {
		@Override
		public void onLoadedSuccess(int arg0, Object arg1,
				PlatformAdapter arg2) {
			Log.d(TAG, arg2+" onLoadedSuccess for type"+arg0 +" withdata"+arg1);
			if(arg0==AdTypes.INTERSTITIAL){
				((IInterstitialPlatform)arg2).showInterstitial(); 
			}
		}

		@Override
		public void onLoadedFail(int arg0, Object arg1, PlatformAdapter arg2) {
			Log.d(TAG, arg2+" onLoadedFail for type"+arg0 +" withdata"+arg1);
		}

		@Override
		public void onAdOpened(int arg0, Object arg1, PlatformAdapter arg2) {
			Log.d(TAG, arg2+" onAdOpened for type"+arg0 +" withdata"+arg1);
		}

		@Override
		public void onAdClosed(int arg0, Object arg1, PlatformAdapter arg2) {
			Log.d(TAG, arg2+" onAdClosed for type"+arg0 +" withdata"+arg1);
		}

		@Override
		public void onAdClicked(int arg0, Object arg1, PlatformAdapter arg2) {
			Log.d(TAG, arg2+" onAdClicked for type"+arg0 +" withdata"+arg1);
		}

		@Override
		public void onOtherEvent(String eventName, int adtype, Object data,
				PlatformAdapter adapter) {
			Log.d(TAG, adapter+" onLoadedSuccess for type"+adtype +" withEvent "+eventName);
		}
	}
}
