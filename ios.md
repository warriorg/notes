* SDWebImage：图片加载
* TTTAttributedLabel：富文本的label，可点击链接
* RegexKitLite：正则表达式
* hpple：html解析
* MBProgressHUD：hud提示框
* ODRefreshControl：下拉刷新
* TPKeyboardAvoiding：有文字输入时，能根据键盘是否弹出来调整自身显示内容的位置
* JDStatusBarNotification：状态栏提示框
* BlocksKit：block工具包。将很多需要用delegate实现的方法整合成了block的形式
* ReactiveCocoa：基于响应式编程思想的oc实践（是个好东西呢）

#TabBar
---
###[RDVTabBarController](https://github.com/robbdimitrov/RDVTabBarController)
>Highly customizable tabBar and tabBarController for iOS

>![image](images/RDVTabBarController.png)

```objective-c
UIViewController *firstViewController = [[RDVFirstViewController alloc] init];
UIViewController *firstNavigationController = [[UINavigationController alloc] initWithRootViewController:firstViewController];

UIViewController *secondViewController = [[RDVSecondViewController alloc] init];
UIViewController *secondNavigationController = [[UINavigationController alloc] initWithRootViewController:secondViewController];

UIViewController *thirdViewController = [[RDVThirdViewController alloc] init];
UIViewController *thirdNavigationController = [[UINavigationController alloc] initWithRootViewController:thirdViewController];

RDVTabBarController *tabBarController = [[RDVTabBarController alloc] init];
[tabBarController setViewControllers:@[firstNavigationController, secondNavigationController,thirdNavigationController]];

self.viewController = tabBarController;

```


#Message Alert
---
###[TSMessages](https://github.com/KrauseFx/TSMessages)
>This library provides an easy to use class to show little notification views on the top of the screen

>![image](images/TSMessages_1.png)

>![image](images/TSMessages_2.png)

-


#UITextField
----
###[TextFieldEffects](https://github.com/raulriera/TextFieldEffects)
>*你不觉得标准的UITextField有些枯燥么？我也这样想——来认识一下TextFieldEffects吧！废话不多说，只要看几个例子*

>![image](images/TextFieldEffects_1.gif)

>![image](images/TextFieldEffects_2.gif)

>![image](images/TextFieldEffects_3.gif)

-

###[WTReTextField](https://github.com/warriorg/WTReTextField)

>*RegEx input validation and formatting as you type*

>![image](images/WTReTextField_1.png)

>![image](images/WTReTextField_2.png)

-

###[JVFloatLabeledTextField](https://github.com/jverdi/JVFloatLabeledTextField)
>*JVFloatLabeledTextField is the first implementation a UX pattern that has come to be known the "Float Label Pattern".*

>![image](images/JVFloatLabeledTextField.gif)

-

###[DemoInPutPasswordView](https://github.com/lqcjdx/DemoInPutPasswordView)

>*仿微信支付密码输入框*

>![image](images/DemoInPutPasswordView.gif)

-

###[BEMCheckBox](https://github.com/Boris-Em/BEMCheckBox)

>![image](images/BEMCheckBox.gif)

-

#UITableView
---
###[DZNEmptyDataSet](https://github.com/dzenbot/DZNEmptyDataSet)
>*DZNEmptyDataSet算是一个很标准的iOS内建方式，适合用来处理空的table view和collection view。默认情况下，如果你的table view为空，屏幕就为空，这样一来用户体验并不理想。*
![image](images/DZNEmptyDataSet.jpg)

-

###[RETableViewManager](https://github.com/romaonthego/RETableViewManager)
>*可以帮助你进行动态创建与管理table views。它给我们提供了预定义cells（bool类型、文本、日期等等——请看下面的截图），但是你还可以创建自定义views，并与默认视图一同使用。*
![image](images/RETableViewManager.png)

-

##UITableViewCell
-
###[MGSwipeTableCell](https://github.com/MortimerGoro/MGSwipeTableCell)
>*这是另一个常见于很多应用中的UI组件，苹果应该考虑在标准的iOS SDK中加入一些类似的内容。Swipeable表格cell是这个pod的最佳描述，也是最好的。*

>![image](images/MGSwipeTableCell_1.gif)

>![image](images/MGSwipeTableCell_2.gif)

>![image](images/MGSwipeTableCell_3.gif)

-
###[UIView-FDCollapsibleConstraints](https://github.com/forkingdog/UIView-FDCollapsibleConstraints)
>*Builds to collapse a view and its relevant layout constraints, simulating a "Flow Layout" mode*
>![image](images/UIView-FDCollapsibleConstraints-1.gif)

>![image](images/UIView-FDCollapsibleConstraints-2.gif)

-

#Image
----
###[Concorde](https://github.com/contentful-labs/Concorde) `Swift`

>你在应用中载入了一堆jpeg吗？有了Concorde，你可以用更好的方式来解决，这是一个很大的进步。
>![image](images/Concorde.gif)

-

###[ETFoursquareImages](https://github.com/EugeneTrapeznikov/ETFoursquareImages)
>When you scroll, image's shown area will become bigger. Like it appears on places' page in Foursquare app.
>![image](images/ETFoursquareImages.gif)

-

###[RSKImageCropper](https://github.com/ruslanskorb/RSKImageCropper)

>RSKImageCropper通过剪切图片作为头像。通过移动图片，剪切圆形框所显示的部分。
>![image](images/RSKImageCropper.png)

-

###[ImagePickerSheetController](https://github.com/larcus94/ImagePickerSheetController)`swift`

>ImagePickerSheetController is similar to UIAlertController in its usage.

>![image](images/ImagePickerSheetController.gif)

```swift
let controller = ImagePickerSheetController(mediaType: .ImageAndVideo)
controller.addAction(ImagePickerAction(title: NSLocalizedString("Take Photo Or Video", comment: "Action Title"), secondaryTitle: NSLocalizedString("Add comment", comment: "Action Title"), handler: { _ in
    presentImagePickerController(.Camera)
}, secondaryHandler: { _, numberOfPhotos in
    println("Comment \(numberOfPhotos) photos")
}))
controller.addAction(ImagePickerAction(title: NSLocalizedString("Photo Library", comment: "Action Title"), secondaryTitle: { NSString.localizedStringWithFormat(NSLocalizedString("ImagePickerSheet.button1.Send %lu Photo", comment: "Action Title"), $0) as String}, handler: { _ in
    presentImagePickerController(.PhotoLibrary)
}, secondaryHandler: { _, numberOfPhotos in
    println("Send \(controller.selectedImageAssets)")
}))
controller.addAction(ImagePickerAction(title: NSLocalizedString("Cancel", comment: "Action Title"), style: .Cancel, handler: { _ in
    println("Cancelled")
}))

presentViewController(controller, animated: true, completion: nil)
```
-

###[SDCycleScrollView](https://github.com/gsdios/SDCycleScrollView)
>无限循环自动图片轮播器

>![image](images/SDCycleScrollView-1.gif)
>
>![image](images/SDCycleScrollView-2.gif)

```objective-c
// 网络加载图片的轮播器
 cycleScrollView.imageURLStringsGroup = imagesURLStrings;

 // 本地加载图片的轮播器
 SDCycleScrollView *cycleScrollView = [SDCycleScrollView cycleScrollViewWithFrame: imagesGroup:图片数组];
 
```
>现已支持cocoapods导入：pod 'SDCycleScrollView','~> 1.3'

>如需更详细的设置，参考如下：

>cycleScrollView.pageControlAliment = SDCycleScrollViewPageContolAlimentRight; // 设置pageControl居右，默认居中

>cycleScrollView.titlesGroup = 标题数组（数组元素个数必须和图片数组元素个数保持一致）; // 如果设置title数组，则会在图片下面添加标题

>cycleScrollView.delegate = ; // 如需监听图片点击，请设置代理，实现代理方法

>cycleScrollView.autoScrollTimeInterval = ;// 自定义轮播时间间隔


#Calendar
---
###[PDTSimpleCalendar](https://github.com/jivesoftware/PDTSimpleCalendar)
 >*PDTSimpleCalendar也许可以算是iOS最棒的日历组件了。你可以在各个方面对它进行定制，无论是运行逻辑还是外观方面。*
>![image](images/PDTSimpleCalendar.png)

-

#Font
---
###[FontAwesomeKit](https://github.com/PrideChung/FontAwesomeKit)
>*有了它你可以很容易地给项目加字体，使用方式繁多。*
![image](images/FontAwesomeKit.png)

-

#Animation
---
###[JxbLovelyLogin](https://github.com/warriorg/JxbLovelyLogin)

>*一个可爱的登陆界面，动画效果仿自国外网站readme.io*

>![image](images/JxbLovelyLogin.gif)

-

#Button
---
###[RadioButton](https://github.com/onegray/RadioButton-ios)`Objective-c`
##### CocoaPods

	pod 'RadioButton'
	
>*Pretty simple class that extends standard UIButton functionality. Default and selected states can be configured for every button.*

>![image](images/RadioButton-1.gif)

>![image](images/RadioButton-2.gif)

Alternatively group the buttons using single line of code:

	radio1.groupButtons = @[radio1, radio2, radio3];


Select any button, and all other button in the same group become deselected automatically:

	radio2.selected = YES; // radio1 and radio3 become deselected


Any button from the group knows which one is selected:

	RadioButton* r1 = radio1.selectedButton;
	RadioButton* r2 = radio2.selectedButton;
	RadioButton* r3 = radio3.selectedButton;
	NSAssert (r1==r2 && r2==r3, @"Must be equal");

And a helpful method to select button by tag:

	[radio1 setSelectedWithTag:kTagRadio3];

#Text
###[cbpowell/MarqueeLabel](https://github.com/cbpowell/MarqueeLabel)`Objective-c`
>*实现跑马灯效果
>
>MarqueeLabel is a UILabel subclass adds a scrolling marquee effect when the text of the label outgrows the available width. The label scrolling direction and speed/rate can be specified as well. All standard UILabel properties (where it makes sense) are available in MarqueeLabel and it behaves just like a UILabel.*
>![image](images/MarqueeLabel.gif)

```Objective-C
MarqueeLabel *scrollyLabel = [[MarqueeLabel alloc] initWithFrame:labelCGRectFrame duration:8.0 andFadeLength:10.0f];
```

###[Instructions](https://github.com/ephread/Instructions)
>Create walkthroughs and coach mark tours in a simple way.<br/>
>专门帮助开发者简化引导教程实现的框架，它以比较灵活的方式用标准的 Cocoa 设计思想来实现了简化引导界面的制作
>![image](images/Instructions.gif)
