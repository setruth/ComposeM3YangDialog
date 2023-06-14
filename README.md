- [JetPack Compose Material3的加载动画弹窗](#jetpack-compose-material3的加载动画弹窗)
  - [介绍](#介绍)
    - [代码示例](#代码示例)
    - [特点](#特点)
    - [缺点](#缺点)
  - [版本环境要求](#版本环境要求)
  - [引入（Gradle KotlinDSL举例）](#引入gradle-kotlindsl举例)
    - [1. 设置仓库源](#1-设置仓库源)
    - [2. 引入依赖](#2-引入依赖)
  - [使用](#使用)
    - [简单使用](#简单使用)
    - [自定义显示内容](#自定义显示内容)
    - [自定义底部显示和隐藏](#自定义底部显示和隐藏)
    - [展示所有动画](#展示所有动画)

# JetPack Compose Material3的加载动画弹窗  

## 介绍

> YangDialog是一个适合Compose Material3的弹窗UI组件，由于官方的DIalog组件功能很少，所以基于基础组件Dialog进行了修改和封装，
> 哔哩哔哩教程地址 https://www.bilibili.com/video/BV1ZN411k7B1/?share_source=copy_web&vd_source=2cbd021570b82989d2171c3061a31b48

### 代码示例

<img src="imgResource\example.gif" style="zoom:25%;" />



### 特点

- 弹窗一个带有加载动画和基本弹窗结构的UI组件，加载动画包含了常用的三个类型，一个Success显示成功的图标，Error显示错误的图标，Loading为加载中的状态，并且加载状态底部提供文本按钮给用户进行加载状态的操作。
- 虽然是一个封装过的弹窗框架，但是提供了许多自定义的部分，比如自定义背景色，内容颜色，标题颜色，底部是否显示确认或者取消按钮等，根据google官方Material3控件属性的设置习惯，使用单例来配置颜色内容等属性，尽可能减少过多的学习成本。
- 弹窗的中间主要内容是提供组合函数让用户自定义展示使用的，并不会干扰弹窗的主要展示部分，可以自定义自己想显示的内容/
- 所有的动画都是已经设置好的，无需过多管理，使用AnimatedVisibility实现动画的各种情况，尽可能的让体验更加的接近原生，而且是根据最基础的Dialog组件进行扩展，所以不会出现太多过多封装造成的性能问题。

### 缺点

- 未提供弹窗边缘点击的回调，也就是无法设置点击弹窗以外的位置来关闭，只能通过弹窗中的回调事件来关闭

## 版本环境要求

- Jetpack Compose Material3
- Gradle 8.1.0以上(建议)
- AGP 8.0.2以上(建议)

## 引入（Gradle KotlinDSL举例）

### 1. 设置仓库源

> settings.gradle.kts中设置仓库源

```groovy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")//添加仓库源
    }
}
```

### 2. 引入依赖

> APP模块下的build.gradle.kts

```groovy
dependencies {
    //请去仓库的releases中使用最新的版本即可
    implementation("com.github.setruth:ComposeM3YangDialog:$releases")
	...
}
```

## 使用

###  简单使用

```kotlin
var dialogShow by remember {
    mutableStateOf(true)
}
YangDialog(
    title = "这是普通弹窗",//自定义标题内容
    isShow = dialogShow,//通过isShow设置是否显示
    onCancel = {
        dialogShow = false
    },//取消按钮点击的回调
    onConfirm = {
        dialogShow = false
    }//取消按钮点击的回调
) {
    Text(text = "hello")//显示的内容
}
```

<img src="imgResource\common.gif" style="zoom:25%;" />





### 自定义显示内容

```kotlin
var dialogShow by remember {
    mutableStateOf(true)
}
var inputContent by remember {
    mutableStateOf("")
}
YangDialog(
    title = "这是自定义内容弹窗",
    isShow = dialogShow,
    onCancel = {
        dialogShow = false
    },
    onConfirm = {
        dialogShow = false
    },
) {
    OutlinedTextField(value = inputContent, onValueChange = { inputContent = it })//自定义内容
}
```

<img src="imgResource\diyContent.gif" style="zoom:25%;" />

### 自定义底部显示和隐藏

```kotlin
var dialogShow by remember {
    mutableStateOf(true)
}
YangDialog(
    title = "自定义底部选项的弹窗",
    isShow = dialogShow,
    onCancel = {
        dialogShow = false
    },
    onConfirm = {
        dialogShow = false
    },
    //通过YangDialogDefaults.bottomConfig设置底部选项的配置
    bottomConfig = YangDialogDefaults.bottomConfig(
        showCancel = false,
        confirmTip = "确认将会关闭弹窗"
    )
) {
    Text(text = "hello")
}
```

<img src="imgResource\diyBottom.gif" style="zoom:25%;" />

### 展示所有动画

```kotlin
var dialogShow by remember {
    mutableStateOf(true)
}
var dialogType by remember {
    mutableStateOf(YangDialogLoadingType.NOT_LOADING)
}
var loadingTip by remember {
    mutableStateOf("")
}
var startLoading by remember {
    mutableStateOf(false)
}
LaunchedEffect(startLoading){
   if (startLoading){
       loadingTip="加载"
       dialogType=YangDialogLoadingType.LOADING
       delay(1500)
       dialogType=YangDialogLoadingType.ERROR
       loadingTip="失败"
       delay(1500)
       dialogType=YangDialogLoadingType.SUCCESS
       loadingTip="成功"
   }
}
YangDialog(
    loadingState = dialogType,//通过改变loadingState来显示对应的动画
    loadingTip = loadingTip,//加载动画下面的提示文本，为空不显示
    title = "这是自定义内容弹窗",
    isShow = dialogShow,
    onCancel = {
        dialogShow = false
        dialogHide()
    },
    //加载动画的底部文本的点击回调，返回当前加载的状态
    onLoadingTipClick = {
        if (it==YangDialogLoadingType.SUCCESS) {
            dialogType=YangDialogLoadingType.NOT_LOADING
        }
    },
    onConfirm = {
       startLoading=true
    },
    bottomConfig = YangDialogDefaults.bottomConfig(
        confirmTip = "开始加载动画"
    )
)
```

<img src="imgResource\allLoading.gif" style="zoom:25%;" />

