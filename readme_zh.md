keymob 是一个简单易用的离线广告管理库。
使用keymob能非常方便的管理应用中各个广告平台的广告，包括展示哪些平台的广告，各个平台的比例，优先顺序等。
支持admob,chartboost,inmobi.mmedia,amazon,iad等常用广告平台，后面会根据大家的反馈加入更多的常见平台的支持。
广告形式支持丰富，包括各种尺寸的banner广告，方块广告，全屏广告，视频广告，应用墙广告等当前流行的广告。
使用时把各个平台的广告ID和比例优先级顺序等信息按json格式配置，初始化keymob库 ，后面的使用就和使用单个平台一样的简单。
json配置文件可以放项目里，自己的网站服务器或者第三方管理平台服务器。
开发者自定义配置文件加载方式和存放位置，避免配置文件加载失败导致的广告无法展示，第三方广告平台的管理混乱等问题。




使用方法

1.下载安装库文件
   下载keymob库文件keymobad.jar,把keymobad.jar添加到android项目的库路径中
   下载广告平台库，keymob支持众多平台，但是只需要添加自己使用的广告平台的库到android工程中就行了。
   如下是各个广告平台对应的库文件
   admob平台： google-play-services.jar
   amazon平台:  amazon-ads.jar
   chartboost平台: chartboost.jar
   mmedia平台：  MMSDK.jar ,nmdp_speech_kit.jar
   inmobi平台:  InMobi.jar
2.添加 代码
  a.添加引用

	import com.keymob.ads.AdManager;
	import com.keymob.core.*;

    使用keymob前，先import keymob的相关类文件。keymob的大部分核心类在com.keymob.core包中，所以可以一次全部引入。AdManager作为keymob的主要类，也是必须引入的。
  
 b.设置和初始化各个广告平台信息

	AdManager.getInstance().initFromJSON(active,jsonString,new  AdEventListener());

   active是广告展示的上下文，所以是必须的，并且不能为null。
   第二个参数是包含各个平台的ID，比重等信息的json字符串，具体格式可以参考模板文件。
   第三个参数是广告事件监听器，监听器是实现接口IAdEventListener的类，如果不需要处理和监听广告事件，则可以设置为null

 c. banner广告的展示 

	AdManager.getInstance().showRelationBanner(BannerSizes.BANNER, BannerPositions.BOTTOM_CENTER,80);

    上面的意思是在设备的底部显示显示标准banner广告。第一个参数是广告尺寸，尺寸的种类在BannerSizes中可以选择的常量，包括标准banner，方块，smart banner等。
    标准banner之外的其他banner尺寸根据平台不同有细微的差别，具体效果可以调试查看。
    第二个参数是广告条展示的位置，包括顶端靠左，顶端居中，顶端靠右等等9种常见位置，各个位置的值在BannerPositions的常量中，方便使用。
    第三个参数是offsetY，即相对位置偏移，例如放在应用的底端，向上偏移80个像素，就是上面的代码效果。如果要贴到应用最底端，则偏移为0.
 
 d. 固定位置展示banner
	
	AdManager.getInstance().showBannerABS(BannerSizes.BANNER, 0, 200);

    上面是在x 0,y 200位置展示标准banner
    虽然相对定位能满足大部分的广告位置设置需求，但为满足某些特殊位置的需要，keymob提供了绝对固定位置展示banner广告的接口。
    第一个参数是banner的尺寸，同相对广告尺寸，第二个参数和第三个参数分别是广告位置的x和y值

 e. 隐藏banne广告
	
	AdManager.getInstance().removeBanner();

    removeBanner会隐藏广告，但是广告并不会销毁，这样再次展示时能迅速的展示给用户。有些广告平台会在隐藏后继续加载广告，在AdEventListener里面还会收到他们抛出的事件。
    
 f. 全屏广告的加载和展示

	AdManager.getInstance().loadInterstitial();

   加载全屏广告，广告加载成功后不会自动展示，这样能更好的控制全屏广告在合适的时机展示给用户，
   如果要在加载成功时立即展示可以在 eventlistener的 receive事件中调用showInterstitial展示广告。

	AdManager.getInstance().showInterstitial();

   展示全屏广告，调用showInterstitial后广告会立即出现。但是请保证广告已经加载完成。

	AdManager.getInstance().isInterstitialReady();

   检查全屏广告是否加载完成了。如果广告没有加载完成直接调用showInterstitial会发生不可预料的事件，有的广告平台可能会导致应用奔溃。
   所以每次展示前都需要判断是否加载完成。整体就是下面的样子。

   	if(AdManager.getInstance().isInterstitialReady()){
		AdManager.getInstance().showInterstitial();
	}

g. 视频广告的加载和展示

	AdManager.getInstance().loadVideo();

   加载视频广告，广告加载成功后不会自动展示，这样能更好的控制视频广告在合适的时机展示给用户，
   如果要在加载成功时立即展示可以在 eventlistener的 receive事件中调用showVideo展示广告。

	AdManager.getInstance().showVideo();

   展示视频广告，调用showVideo后广告会立即出现。但是请保证广告已经加载完成。

	AdManager.getInstance().isVideoReady();

   检查视频广告是否加载完成了。如果广告没有加载完成直接调用showVideo会发生不可预知的事件，如有的广告平台可能会导致奔溃。
   所以每次展示前都需要判断是否加载完成。片段如下面的样子。

   	if(AdManager.getInstance().isVideoReady()){
		AdManager.getInstance().showVideo();
	}

h. 应用墙广告的加载和展示

	AdManager.getInstance().loadAppWall();

   加载应用墙广告，广告加载成功后不会自动展示，这样能更好的控制应用墙广告在合适的时机展示给用户，
   如果要在加载成功时立即展示可以在 eventlistener的 receive事件中调用showAppWall展示广告。

	AdManager.getInstance().showAppWall();

   展示应用墙广告，调用showAppWall后广告会立即出现。但是请保证应用墙广告已经加载完成。

	AdManager.getInstance().isAppWallReady();

   检查应用墙广告是否加载完成了。如果广告没有加载完成直接调用showAppWall会发生不可预知的事件，如有的广告平台可能会导致奔溃。
   所以每次展示前都需要判断是否加载完成。片段如下面的样子。

   	if(AdManager.getInstance().isAppWallReady()){
		AdManager.getInstance().showAppWall();
	}

3.设置配置文件

  a.配置权限

	<!-- base permission -->
	<uses-permission android:name="android.permission.INTERNET"/>
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
	<uses-permission android:name="android.permission.READ_PHONE_STATE"/>
	<!-- base permission for location-->
	<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
	<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />			    
	<!-- base permission  required by chartboost-->
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
	<!-- permission required by mmedia -->
	<uses-permission android:name="android.permission.RECORD_AUDIO" />
	<uses-feature android:name="android.hardware.microphone" android:required="false" />

  上面是广告需要的权限，基础权限是对任何广告平台都要的，location是某些平台需要，为了省事起见，可以把这俩块都加进配置中。
  WRITE_EXTERNAL_STORAGE为chartboost所需要的权限，如果加了chartboost，则需要添加此权限。
  后面的audio和microphone是mmedia要求的权限，如果使用了mmedia平台，需要添加


  b.配置平台相关activity和service

	<!-- Admob Mobile Ads -->
	<meta-data android:name="com.google.android.gms.version" android:value="6587000" />
	<activity android:name="com.google.android.gms.ads.AdActivity" android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize" android:theme="@android:style/Theme.Translucent"/>
	<!-- Amazon Mobile Ads -->
	<activity android:name="com.amazon.device.ads.AdActivity" android:configChanges="keyboardHidden|orientation|screenSize"/>
	<!-- InMobi -->
	<activity android:name="com.inmobi.androidsdk.IMBrowserActivity" android:configChanges="keyboardHidden|orientation|keyboard|smallestScreenSize|screenSize" android:theme="@android:style/Theme.Translucent.NoTitleBar" android:hardwareAccelerated="true" />
	<!-- Millennial Media -->
	<activity android:name="com.millennialmedia.android.MMActivity" android:theme="@android:style/Theme.Translucent.NoTitleBar" android:configChanges="keyboardHidden|orientation|keyboard|screenSize" />

  上面是各个广告平台要求配置的activity，根据自己选择使用的广告平台，添加对应的activity配置到androidmanifest.xml中。

4.广告平台配置文件模板
	{
		"isTesting":true,//是否是测试模式
		"rateModel":1,//广告平台排序规则，0表示rate是权重，各个平台按比例显示广告，1表示rate是顺序，各个平台按顺序展示广告
		"platforms":[
		{"adapter":"AdmobAdapter","rate":90,"key1":"ca-app-pub-xxx/xxx","key2":"ca-app-pub-xxx/xxx","types":[0,1]},//admob 平台 ,key1 banner ID，key2全屏id
		{"adapter":"AmazonAdapter","rate":20,"key1":"xxx","types":[0,1]},//amazon 平台 ,key1 appkey
		{"adapter":"ChartboostAdapter","rate":40,"key1":"xxx","key2":"xxx","types":[1,2,3]},//chartboost 平台 ,key1 appID，key2 signature
		{"adapter":"InmobiAdapter","rate":50,"key1":"xxx","types":[0,1]},//inmobi 平台 ,key1 appid 
		{"adapter":"IadAdapter","rate":50,"types":[0,1]},//iad 平台 ,android上会被自动忽略
		{"adapter":"MMediaAdapter","rate":10,"key1":"xxx","key2":"xxx","types":[0,1]}//mmedia 平台 ,key1 banner ID，key2全屏id
		]
	}

rate会根据ratemodel不同而成为比重或者排序号,adapter不能修改和types的值不能修改。adapter表示平台实现，types表示本平台支持的广告类型。
根据自己的选择使用平台，可以删除不用的平台。也可以自己实现其他的平台，然后添加到列表中。创建自己的广告平台扩展教程将会在后面逐渐完善。

项目地址：https://github.com/keymobdev/Ad-Network-Mediation-lib-for-android