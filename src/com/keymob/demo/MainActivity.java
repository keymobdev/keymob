package com.keymob.demo;

import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.keymob.networks.AdManager;
import com.keymob.networks.core.BannerPositions;
import com.keymob.networks.core.BannerSizeType;
import com.keymob.networks.core.IAdEventListener;
import com.keymob.networks.core.IInterstitialPlatform;
import com.keymob.networks.core.PlatformAdapter;
import com.keymob.sdk.core.AdTypes;


public class MainActivity extends Activity {
	public static final String TAG = "com.keymob.networks";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		initKeymobFromFile();
		initKeymobFromKeymobService();
//		AdManager.getInstance().showRelationBanner(BannerSizeType.BANNER, BannerPositions.TOP_CENTER,0);
	}
	private void initKeymobFromKeymobService(){
		
		AdManager.getInstance().initFromKeymobService(this, "2", new AdEventListener(), false);
	}
	private void initKeymobFromFile(){
		 String res=null;
	      try
	      {
	    	  InputStream is=getAssets().open("ads.json");
	            byte[] buf = new byte[is.available()];         
	            is.read(buf);
	            res = new String(buf,"UTF-8");     
	            is.close();
	      } catch (Exception e)
	      {
	    	  e.printStackTrace();
	      }
	      if(res!=null){
	    	  AdManager.setEnableLog(true);
	    		try {
					AdManager.getInstance().initFromJSON(this,res,new  AdEventListener());
				} catch (Exception e) {
					e.printStackTrace();
				}
	      }
	}
	
	public void clickInterstitial(View view) {
		if(AdManager.getInstance().isInterstitialReady()){
			AdManager.getInstance().showInterstitial();
		}else{
			AdManager.getInstance().loadInterstitial();
		}
	}
	public void clickTop(View view) {
		AdManager.getInstance().showRelationBanner(BannerSizeType.BANNER, BannerPositions.TOP_CENTER,0);
	}
	public void clickBottom(View view) {
		AdManager.getInstance().showRelationBanner(BannerSizeType.BANNER, BannerPositions.BOTTOM_CENTER,88);
	}
	public void clickTop100(View view) {
		AdManager.getInstance().showBannerABS(BannerSizeType.BANNER, 0, 200);
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
			Log.d(TAG, arg2+" onLoadedSuccess for type "+arg0 +" withdata "+arg1);
			if(arg0==AdTypes.INTERSTITIAL){
				((IInterstitialPlatform)arg2).showInterstitial(); 
			}
		}

		@Override
		public void onLoadedFail(int arg0, Object arg1, PlatformAdapter arg2) {
			Log.d(TAG, arg2+" onLoadedFail for type "+arg0 +" withdata "+arg1);
		}

		@Override
		public void onAdOpened(int arg0, Object arg1, PlatformAdapter arg2) {
			Log.d(TAG, arg2+" onAdOpened for type "+arg0 +" withdata "+arg1);
		}

		@Override
		public void onAdClosed(int arg0, Object arg1, PlatformAdapter arg2) {
			Log.d(TAG, arg2+" onAdClosed for type "+arg0 +" withdata "+arg1);
		}

		@Override
		public void onAdClicked(int arg0, Object arg1, PlatformAdapter arg2) {
			Log.d(TAG, arg2+" onAdClicked for type "+arg0 +" withdata "+arg1);
		}

		@Override
		public void onOtherEvent(String eventName, int adtype, Object data,
				PlatformAdapter adapter) {
			Log.d(TAG, adapter+" onLoadedSuccess for type"+adtype +" withEvent "+eventName);
		}
	}
}
