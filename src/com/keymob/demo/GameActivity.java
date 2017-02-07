package com.keymob.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.keymob.networks.AdManager;
import com.keymob.networks.core.BannerPositions;
import com.keymob.networks.core.BannerSizeType;

public class GameActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
	}
	public void clickTop(View view) {
		AdManager.getInstance().showRelationBanner(BannerSizeType.BANNER, BannerPositions.TOP_CENTER,0,this);
	}
	public void clickTopFull(View view) {
		if(AdManager.getInstance().isInterstitialReady()){
			AdManager.getInstance().showInterstitial(this);
		}else{
			AdManager.getInstance().loadInterstitial(this);
		}
	}
}
