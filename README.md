# keymob is an easy-to-use offline advertising management library
Keymob is used to  manage  ad advertising platforms of mobile applications, including ad impressions which platforms, the proportion of each platform, and other priorities.<br/>
Support admob, chartboost, inmobi.mmedia, amazon, iad by default,other common advertising platform will been  supported based on  feedback.<br/>
Support rich forms of advertising, including the current popular advertising banner with variety of sizes, rectange ads, Interstitial ads, video ads, more-app ad.<br/>
Config the ad ID , proportion, rate in json format , initialize keymob library. And then  use as a single platform.<br/>
json profile can put in their own web server or third-party management platform server.<br/>
Free choice of storage location and loading configuration files, avoid configuration file failed to load resulting in loss of revenue, but also to avoid third-party management platform, resulting in reduced revenue confusion.<br/>



## Usage

### 1. Download and install the library files<br/>
  Download keymob library keymobad.jar,  add it to the library path of android project<br/>
  Download advertising platform libraries, keymob supports many platforms, but only need to add one default advertising platform library to been used when load ad from remote fail.<br/>
   The following are the library files of each advertising platform<br/>
   * admob：AdmobAdapter.jar
   * amazon:  AmazonAdapter.jar
   * chartboost: ChartboostAdapter.jar
   * mmedia：  MMediaAdapter.jar
   * inmobi:  InmobiAdapter.jar
   * baidu:  BaiduAdapter.jar

<br/>   warning：just need add one platform lib to assets/com_keymob_sdks  .

###  2.Add Code

#### a.add import<br/>
```
	import com.keymob.ads.AdManager;
	import com.keymob.core.*;
```
Before using keymob, first import keymob related class files. Most of the core classes in com.keymob.core package, so you can import all at once. AdManager as the main class of keymob , also need to be imported.<br/>
  
#### b.Setup and initialize keymob with json string
```
	AdManager.getInstance().initFromJSON(active,jsonString,new  AdEventListener());
```
   The first parameter  is context active, it as necessary, and can not be null.<br/>
  The second parameter is the config info of each platform in json string format,json format reference template.<br/>
  The third parameter is   event listener of advertising,witch is a class that implements interface IAdEventListener, if you do not want to deal with advertising events, you can set it to null.<br/>

    You can Setup and   initialize keymob with Keymob.com service also,that will been more easy to use.
```
	AdManager.getInstance().initFromKeymobService(this, "1", new AdEventListener(), false);
```
   The first parameter  is context active, it as necessary, and can not be null.
   The second parameter is ID got from Keymob.com
   The third parameter is   event listener of advertising,witch is a class that implements interface IAdEventListener, if you do not want to deal with advertising events, you can set it to null.<br/>
   The fourth parameter is test mode switch，if you are testing ad set  true，change to false when publish
   
   Tip: ID can be got  from www.keymob.com  .


#### c. Display banner advertising
```
	AdManager.getInstance().showRelationBanner(BannerSizes.BANNER, BannerPositions.BOTTOM_CENTER,80);
```
    The above means that displays the standard banner ad at the bottom of the device . The first parameter is the ad size, the type size can be selected in BannerSizes constants, including the standard banner, rectange banner, smart banner and so on.<br/>
  Other banner size outside  standard size(320*50) may have  small differences in the different platforms, run to see the effects.<br/>
  The second parameter is the position of the banner displayed,  the value of each position is  in BannerPositions constants,including the top left, top center, top right-hand and so on ,9 kinds of common position total.<br/>
  The third parameter is offsetY, i.e., the relative positional deviation, e.g., on the bottom of the application, the upward offset 80 pixels, that is, the effect of the above code. If you want to stick to the bottom of the application, set the offsetY 0.<br/>
 
#### d. display banner at Fixed location
```	
	AdManager.getInstance().showBannerABS(BannerSizes.BANNER, 0, 200);
```
The above code is display standard banner at point(0,200)<br/>
     Although the relative positioning to meet the needs of the majority of advertising location settings, but to meet the needs of some special position, keymob provides absolute fixed position display banner advertising api.<br/>
     The first parameter is the size of the banner, the second argument and third parameters are the position x and y values of banner.<br/>

#### e. Hide banne ad
```	
	AdManager.getInstance().removeBanner();
```
   "removeBanner" hidden banner advertising, but advertising will not be destroyed so show can be quickly presented to the user next time. Some advertising platform will continue to  load ad after hidden , so the event will dispatched also.<br/>
    
#### f. Load and display full-screen ads
```
	AdManager.getInstance().loadInterstitial();
```
   Load Interstitial ads, does not automatically show after load successfully, this can better control Interstitial ad at the right time to show to the user,<br/>
    If you want to show immediate after load,just handler onLoadedSuccess  in eventListener and call showInterstitial.<br/>
```
	AdManager.getInstance().showInterstitial();
```
   Display Interstitial advertising, ads will appear immediately after the call showInterstitial. However, please ensure that advertising has finished loading.

	AdManager.getInstance().isInterstitialReady();

   Check the Interstitial ad is loaded complete. If call showInterstitial directly when an ad  has not finished loading unpredictable events will occur, som advertising platform could lead to crash.<br/>
    So make sure the Interstitial is ready before every show.Below is the overall look.
```
   	if(AdManager.getInstance().isInterstitialReady()){
		AdManager.getInstance().showInterstitial();
	}
```
#### g. Load and display video ads
```
	AdManager.getInstance().loadVideo();
```
  Load video ads, does not automatically show after load successfully, this can better control video ad at the right time to show to the user,<br/>
    If you want to show immediate after load,just handler onLoadedSuccess  in eventListener and call showVideo.
```
	AdManager.getInstance().showVideo();
```
   Display video ads, ads will appear immediately after the call showVideo. However, please ensure that advertising has finished loading.
```
	AdManager.getInstance().isVideoReady();
```
  Check the video ad is loaded complete. If call showVideo directly when an ad  has not finished loading unpredictable events will occur, some advertising platform could lead to crash.<br/>
    So make sure the video is ready before every show.Below is the overall look.
```
   	if(AdManager.getInstance().isVideoReady()){
		AdManager.getInstance().showVideo();
	}
```
#### h. Application load and display more app advertising
```
	AdManager.getInstance().loadAppWall();
```
   Load more app ads, does not automatically show after load successfully, this can better control video ad at the right time to show to the user,<br/>
    If you want to show immediate after load,just handler onLoadedSuccess  in eventListener and call showAppWall.<br/>
```
	AdManager.getInstance().showAppWall();
```
   Display more app ads, ads will appear immediately after the call showAppWall. However, please ensure that advertising has finished loading.
```
	AdManager.getInstance().isAppWallReady();
```
   Check the More App ad is loaded complete. If call showAppWall directly when an ad  has not finished loading unpredictable events will occur, some advertising platform could lead to crash.<br/>
    So make sure the More App is ready before every show.Below is the overall look.<br/>
```
   	if(AdManager.getInstance().isAppWallReady()){
		AdManager.getInstance().showAppWall();
	}
```
### 3.Setting Profiles

#### a.Configuring Permissions

```
	<!-- base permission -->
	<uses-permission android:name="android.permission.INTERNET"/>
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
	<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
	<!-- base permission for location-->
	<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
	<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />			    
	<!-- base permission  required by chartboost and baidu-->
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
	<!-- permission required by mmedia -->
	<uses-permission android:name="android.permission.RECORD_AUDIO" />
	<uses-feature android:name="android.hardware.microphone" android:required="false" />

```
  The above are permissions advertising platform needs, basic permissions are required by all advertising platform, location is required by some platforms, in order to save the sake , both blocks can be added to this configuration.<br/>
 WRITE_EXTERNAL_STORAGE  permission is required by chartboost, if added chartboost, you need to add this permission.<br/>
 The audio and microphone permissions are required by  mmedia, if used mmedia platform,  add it to configuration<br/>


#### b.Config platform  services and activity
```
	<!-- Admob Mobile Ads -->
	<meta-data android:name="com.google.android.gms.version" android:value="7327000" />
	<activity android:name="com.google.android.gms.ads.AdActivity" android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize" android:theme="@android:style/Theme.Translucent"/>
	<!-- Amazon Mobile Ads -->
	<activity android:name="com.amazon.device.ads.AdActivity" android:configChanges="keyboardHidden|orientation|screenSize"/>
	<!-- InMobi -->
	<activity android:name="com.inmobi.androidsdk.IMBrowserActivity" android:configChanges="keyboardHidden|orientation|keyboard|smallestScreenSize|screenSize" android:theme="@android:style/Theme.Translucent.NoTitleBar" android:hardwareAccelerated="true" />
	<!-- Millennial Media -->
	<activity android:name="com.millennialmedia.android.MMActivity" android:theme="@android:style/Theme.Translucent.NoTitleBar" android:configChanges="keyboardHidden|orientation|keyboard|screenSize" />
	<!-- Keymob -->      
        <activity android:name="com.keymob.sdk.core.KeymobActivity"   android:theme="@android:style/Theme.Dialog" android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"  />
	<!-- baidu -->     
	<activity android:name="com.baidu.mobads.AppActivity" android:configChanges="keyboard|keyboardHidden|orientation"/> 
```
  The service and activity required by advertising platform must been add to configuration,add the corresponding activity and services in  androidmanifest.xml.<br/>


### 4.Advertising platform configuration file template

```
	{
		"isTesting":true,//Whether it is in test mode
		"rateModel":1,//0 said priority is  represents the weight of each platform ,1 said the priority is the order of each platform to display ads
		"platforms":[
		{"class":"AdmobAdapter","priority":10,"key1":"ca-app-pub-xxx/xxx","key2":"ca-app-pub-xxx/xxx"},//admob  ,key1 banner ID，key2 Interstitial id
		{"class":"AmazonAdapter","priority":10,"key1":"xxx"},//amazon ,key1 appkey
		{"class":"ChartboostAdapter","priority":10,"key1":"xxx","key2":"xxx"},//chartboost ,key1 appID，key2 signature
		{"class":"InmobiAdapter","priority":10,"key1":"xxx"},//inmobi ,key1 appid 
		{"class":"IadAdapter","priority":10,"key1":"appid"},//iad ,will be automatically ignored on android
		{"class":"MMediaAdapter","priority":10,"key1":"xxx","key2":"xxx"}//mmedia ,key1 banner apID，key2 Interstitial apid
		]
	}
```
Depending rate model priority will become the sort number or proportion.All keyName in config can not been modified."class" can not be modified. "class", said platform implement, types indicates that the platform supports the type of ad.<br/>
Using the platform of you  choice, delete unused platform. You can also add your own platforms, then config in the list. Create your own advertising platform extensions tutorials will gradually improve later.<br/>

project home：https://github.com/keymobdev/keymob   <br/>
ios project: https://github.com/keymobdev/Keymob-Ad-Lib-for-IOS