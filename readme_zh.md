keymob 是一个简单易用的广告管理库。
使用keymob能非常方便的管理应用中各个广告平台的广告，包括展示哪些平台的广告，各个平台的比例，优先顺序等。
支持admob,chartboost,inmobi.mmedia,amazon,iad,adcolony,baidu,广点通等常用广告平台，后面会根据大家的反馈加入更多的常见平台的支持。
广告形式支持丰富，包括各种尺寸的banner广告，方块广告，全屏广告，视频广告，应用墙广告等当前流行的广告。
使用时把各个平台的广告ID和比例优先级顺序等信息配置在www.keymob.com 管理处或者按json格式配置，json配置文件可以放项目里，自己的网站服务器或者第三方管理平台服务器。


交流群：310513042

使用方法

1.下载安装库文件
要在手机应用里面展示广告需要在应用里面添加广告管理库，Keymob广告管理库目前的版本是20150801 下载解压后可以看到android工程的目录结构，大部分常规的android工程文件和目录，下面资源是Keymob管理库相关的。<br/> 
libs\keymobad.jar keymob 广告管理核心库
assets\com_keymob_sdks keymob 备用平台
assets\biduad_plugin keymob 百度平台需要的资源
assets\gdt_plugin keymob 广点通平台需要的资源
README.md keymob 英文快速集成文档
README_zh.md keymob 中文快速集成文档

使用方法是把下载到的keymob相关资源复制到自己android项目中相应目录下

注意：assets目录下的三个文件夹以及文件夹下面的文件都不能修改名称

com_keymob_sdks 目录下面有个 AdmobAdapter.jar 表示Keymob使用admob作为无法连接Keymob时的备用广告平台 如果想改用别的平台 可以下载更多的备用平台。 
除上面下载的资源外，使用keymob官方支持的平台，无需再单独添加各个平台的代码。更多备用下载地址https://github.com/keymobdev/admob-adapter

2.添加 代码
  a.引入代码

	import com.keymob.networks.AdManager;
	import com.keymob.networks.core.*;
	import com.keymob.sdk.core.AdTypes;

    使用keymob前，先import keymob的相关类文件。keymob的大部分核心类在com.keymob.core包中，所以可以一次全部引入。AdManager作为keymob的主要类，也是必须引入的。
  
 b.通过json配置文件设置和初始化各个广告平台信息

	AdManager.getInstance().initFromJSON(active,jsonString,new  AdEventListener());

   active是广告展示的上下文，所以是必须的，并且不能为null。
   第二个参数是包含各个平台的ID，比重等信息的json字符串，具体格式可以参考模板文件，不能为空。
   第三个参数是广告事件监听器，监听器是实现接口IAdEventListener的类，如果不需要处理和监听广告事件，则可以设置为null

   c.通过Keymob.com网站服务设置和初始化各个广告平台信息

	AdManager.getInstance().initFromKeymobService(this, "1", new AdEventListener(), false);

   active是广告展示的上下文，所以是必须的，并且不能为null。
   第二个参数是Keymob.com网站创建获取到的应用ID
   第三个参数是广告事件监听器，监听器是实现接口IAdEventListener的类，如果不需要处理和监听广告事件，则可以设置为null
   第四个参数是是否是测试，如果在开发中，设置true，最后发布的时候改成false

   注意：使用此方法需先前往www.keymob.com创建应用，配置广告平台信息

 d. banner广告的展示 

	AdManager.getInstance().showRelationBanner(BannerSizeType.BANNER, BannerPositions.BOTTOM_CENTER,0,this);

    上面的意思是在设备的底部显示显示标准banner广告。第一个参数是广告尺寸，尺寸的种类在BannerSizeType中可以选择的常量，包括标准banner，方块，smart banner等。
    标准banner之外的其他banner尺寸根据平台不同有细微的差别，具体效果可以调试查看。
    第二个参数是广告条展示的位置，包括顶端靠左，顶端居中，顶端靠右等等9种常见位置，各个位置的值在BannerPositions的常量中，方便使用。
    第三个参数是offsetY，即相对位置偏移，例如放在应用的底端，向上偏移0个像素，就是上面的代码效果。如果要贴到应用最底端上移60，则偏移为60.
 
 d. 固定位置展示banner
	
	AdManager.getInstance().showBannerABS(BannerSizeType.BANNER, 0, 200,this);

    上面是在x 0,y 200位置展示标准banner
    虽然相对定位能满足大部分的广告位置设置需求，但为满足某些特殊位置的需要，keymob提供了绝对固定位置展示banner广告的接口。
    第一个参数是banner的尺寸，同相对广告尺寸，第二个参数和第三个参数分别是广告位置的x和y值

 e. 隐藏banne广告
	
	AdManager.getInstance().removeBanner();

    removeBanner会隐藏广告，但是广告并不会销毁，这样再次展示时能迅速的展示给用户。有些广告平台会在隐藏后继续加载广告，在AdEventListener里面还会收到他们抛出的事件。
    
 f. 全屏广告的加载和展示

	AdManager.getInstance().loadInterstitial(this);

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

	AdManager.getInstance().loadVideo(this);

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

	AdManager.getInstance().loadAppWall(this);

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
	<!-- base permission  required by chartboost and baidu-->
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
	<!-- permission required by mmedia -->
	<uses-permission android:name="android.permission.RECORD_AUDIO" />
	<uses-feature android:name="android.hardware.microphone" android:required="false" />

  上面是广告需要的权限，基础权限是对任何广告平台都要的，location是某些平台需要，为了省事起见，可以把这俩块都加进配置中。
  WRITE_EXTERNAL_STORAGE为chartboost,baidu所需要的权限，如果加了chartboost，baidu则需要添加此权限。
  后面的audio和microphone是mmedia要求的权限，如果使用了mmedia平台，需要添加


  b.配置平台相关activity和service

	<!-- Admob -->
        <meta-data
            android:name="com.google.android.gms.version"
            android:value="8115000" />

        <activity
            android:name="com.google.android.gms.ads.AdActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
            android:theme="@android:style/Theme.Translucent" />

        <!-- Amazon Mobile Ads -->
        <activity
            android:name="com.amazon.device.ads.AdActivity"
            android:configChanges="keyboardHidden|orientation|screenSize" />

        <!-- InMobi -->
        <activity
            android:name="com.inmobi.rendering.InMobiAdActivity"
            android:configChanges="keyboardHidden|orientation|keyboard|smallestScreenSize|screenSize"
            android:hardwareAccelerated="true"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"
            />

        <!-- Millennial Media -->
        <activity
            android:name="com.millennialmedia.internal.MMActivity"
            
            android:theme="@android:style/Theme.Translucent.NoTitleBar" />
        <activity
            android:name="com.millennialmedia.internal.MMIntentWrapperActivity"
            android:label="sdk" />
        

        <!-- Keymob -->
        <activity
            android:name="com.keymob.sdk.core.KeymobActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
            android:theme="@android:style/Theme.Dialog" />
        <!-- baidu -->
        <activity
            android:name="com.baidu.mobads.AppActivity"
            android:configChanges="keyboard|keyboardHidden|orientation" />

        <!-- adcolony -->
        <activity
            android:name="com.jirbo.adcolony.AdColonyOverlay"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen" />
        <activity
            android:name="com.jirbo.adcolony.AdColonyFullscreen"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:theme="@android:style/Theme.Black.NoTitleBar.Fullscreen" />
        <activity
            android:name="com.jirbo.adcolony.AdColonyBrowser"
            android:configChanges="keyboardHidden|orientation|screenSize"
            android:theme="@android:style/Theme.Black.NoTitleBar.Fullscreen" />

        <!-- guang dian tong -->
        <service
            android:name="com.qq.e.comm.DownloadService"
            android:exported="false" />
            <activity
            android:name="com.qq.e.ads.ADActivity"
            android:configChanges="keyboard|keyboardHidden|orientation|screenSize" />

        <!-- chartboost -->
        	<activity android:name="com.chartboost.sdk.CBImpressionActivity"
            	   android:excludeFromRecents="true"
                   android:hardwareAccelerated="true"
            	   android:theme="@android:style/Theme.Translucent.NoTitleBar.Fullscreen"
                   android:configChanges="keyboardHidden|orientation|screenSize"/>
	   
  上面是各个广告平台要求配置的activity，根据自己选择使用的广告平台，添加对应的activity配置到androidmanifest.xml中。

4.广告平台配置文件模板
	{
		"isTesting":true,//Whether it is in test mode
		"rateModel":1,//0 said priority is  represents the weight of each platform ,1 said the priority is the order of each platform to display ads
		"platforms":[
		{"class":"AdmobAdapter","priority":10,"key1":"ca-app-pub-xxx/xxx","key2":"ca-app-pub-xxx/xxx"},//admob  ,key1 banner ID，key2 Interstitial id
		{"class":"BaiduAdapter","priority":10,"key1":"apid","key2":"banner id","param":"{\"interstitialID\":\"interstitial ID\",\"videoID\":\"video ID\"}"},//baidu platform,param is a json string.remove video ID key value for ios
		{"class":"AmazonAdapter","priority":10,"key1":"xxx"},//amazon ,key1 appkey
		{"class":"ChartboostAdapter","priority":10,"key1":"xxx","key2":"xxx"},//chartboost ,key1 appID，key2 signature
		{"class":"InmobiAdapter","priority":10,"key1":"xxx","key2":"","param":" interstitial placement"},//inmobi ,key1 appid ,key2 banner placement,param interstitial placement
		{"class":"IadAdapter","priority":10,"key1":"appid"},//iad ,will be automatically ignored on android
		{"class":"GDTAdapter","priority":10,"key1":"appid","key2":"banner id", "param":"{\"interstitialID\":\"interstitial ID\",\"appWallID\":\"app Wall ID\"}"},//gdt platform
		{"class":"AdcolonyAdapter","priority":10,"key1":"appid","key2":"zone interstitia","param":"video zone"},//adcolony platform
		{"class":"MMediaAdapter","priority":10,"key1":"xxx","key2":"xxx"}//mmedia ,key1 banner apID，key2 Interstitial apid
		]
	}

priority会根据ratemodel不同而成为比重或者排序号。class表示平台实现，不能随意修改。

项目地址：https://github.com/keymobdev/keymob
ios project: https://github.com/keymobdev/Keymob-Ad-Lib-for-IOS<br/>