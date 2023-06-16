package com.setruth.yangdialog


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

/**
 * @author setruth
 * @date 2023/6/12
 * @time 19:31
 */

/**
 * TODO 更多的动画加载和自定义内容弹窗
 * @param isShow Boolean 是否显示(必填)
 * @param title String 标题内容
 * @param loadingState YangDialogLoadingType 在[YangDialogLoadingState]中对应
 * 了四种状态，包括不加载，不加载时就会显示弹窗内容，加载分为成功，失败，加载中
 * @param loadingTip String 加载状态下的文字提示，可以点击，点击的回调函数是loadingTipClick
 * @param onLoadingTipClick Function1<[YangDialogLoadingState], Unit> 传递当前加载的状态
 * @param onCancel Function0<Unit> 点击取消按钮的回调
 * @param onConfirm Function0<Unit> 点击确认按钮的回调
 * @param onDismissRequest Function0<Unit> 点击弹窗外空白区域的回调
 * @param bottomConfig [YangDialogBottomConfig] 底部配置，包括底部确认取消按钮显示，显示的文字内容等
 * @param colorConfig [YangDialogColorConfig] 颜色配置，包括背景色，标题颜色，内容颜色等
 * @param dialogContent [@androidx.compose.runtime.Composable] Function0<Unit> 弹窗显示的内容
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun YangDialog(
    isShow: Boolean,
    title: String = "标题",
    loadingState: YangDialogLoadingState = YangDialogLoadingState.NOT_LOADING,
    loadingTip: String = "加载中",
    onLoadingTipClick: (YangDialogLoadingState) -> Unit = {},
    onCancel: () -> Unit = {},
    onConfirm: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
    bottomConfig: YangDialogBottomConfig = YangDialogDefaults.bottomConfig(),
    colorConfig: YangDialogColorConfig = YangDialogDefaults.colorConfig(),
    dialogContent: @Composable () -> Unit = {},
) {
    if (isShow) {
        Dialog(onDismissRequest = { onDismissRequest() }) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp)
                        .animateContentSize(),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = colorConfig.containerColor().value,
                        contentColor = colorConfig.contentColor().value
                    ),
                ) {
                    Text(
                        text = title,
                        modifier = Modifier.padding(15.dp),
                        fontWeight = FontWeight.Bold,
                        color = colorConfig.titleColor().value
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(15.dp),
                    ) {
                        Column {
                            AnimatedVisibility(
                                visible = loadingState == YangDialogLoadingState.NOT_LOADING,
                                enter = expandVertically(expandFrom = Alignment.Bottom) + fadeIn(),
                                exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
                            ) {
                                Column {
                                    dialogContent()
                                    Spacer(modifier = Modifier.height(24.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        if (bottomConfig.showCancel().value) {
                                            TextButton(
                                                onClick = {
                                                    onCancel()
                                                },
                                                enabled = bottomConfig.cancelActive().value
                                            ) {
                                                Text(bottomConfig.cancelTip().value)
                                            }
                                        }
                                        Spacer(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                        )
                                        if (bottomConfig.showConfirm().value) {
                                            TextButton(
                                                onClick = {
                                                    onConfirm()
                                                },
                                                enabled = bottomConfig.confirmActive().value
                                            ) {
                                                Text(bottomConfig.confirmTip().value)
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        Column {
                            AnimatedVisibility(
                                visible = loadingState != YangDialogLoadingState.NOT_LOADING,
                                enter = scaleIn(),
                                exit = scaleOut()
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Row {
                                        AnimatedVisibility(
                                            visible = loadingState == YangDialogLoadingState.LOADING,
                                            enter = scaleIn(),
                                            exit = scaleOut()
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                CircularProgressIndicator()
                                            }
                                        }
                                        AnimatedVisibility(
                                            visible = loadingState == YangDialogLoadingState.ERROR,
                                            enter = scaleIn(),
                                            exit = scaleOut()
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Icon(
                                                    modifier = Modifier.size(50.dp),
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = loadingTip,
                                                    tint = MaterialTheme.colorScheme.error
                                                )
                                            }
                                        }
                                        AnimatedVisibility(
                                            visible = loadingState == YangDialogLoadingState.SUCCESS,
                                            enter = scaleIn(),
                                            exit = scaleOut()
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Icon(
                                                    modifier = Modifier.size(50.dp),
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = loadingTip,
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        }
                                    }
                                    TextButton(
                                        enabled = loadingTip != "",
                                        onClick = { onLoadingTipClick(loadingState) },
                                    ) {
                                        val textColor = when (loadingState) {
                                            YangDialogLoadingState.LOADING -> MaterialTheme.colorScheme.onSurface
                                            YangDialogLoadingState.SUCCESS -> MaterialTheme.colorScheme.primary
                                            YangDialogLoadingState.ERROR -> MaterialTheme.colorScheme.error
                                            YangDialogLoadingState.NOT_LOADING -> MaterialTheme.colorScheme.onSurface
                                        }
                                        Text(
                                            text = loadingTip,
                                            color = textColor
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * 弹窗的配置内容
 */
object YangDialogDefaults {
    /**
     * 弹窗的底部配置
     * @param showCancel Boolean 是否显示取消按钮
     * @param showConfirm Boolean 是否显示确认按钮
     * @param confirmTip String 取消按钮的文字内容
     * @param cancelTip String 确认按钮的文字内容
     * @return YangDialogBottomConfig 弹窗底部的配置定义[YangDialogBottomConfig]
     */
    @Composable
    fun bottomConfig(
        showCancel: Boolean = true,
        showConfirm: Boolean = true,
        confirmTip: String = "确认",
        cancelTip: String = "取消",
        cancelActive: Boolean = true,
        confirmActive: Boolean = true,
    ): YangDialogBottomConfig =
        YangDialogBottomConfig(
            showCancel,
            showConfirm,
            confirmTip,
            cancelTip,
            cancelActive,
            confirmActive
        )

    /**
     * 弹窗的颜色配置
     * @param containerColor Color 背景色
     * @param titleColor Color 标题颜色
     * @param contentColor Color 内容颜色
     * @return YangDialogColorConfig 弹窗颜色配置定义 [YangDialogColorConfig]
     */
    @Composable
    fun colorConfig(
        containerColor: Color = MaterialTheme.colorScheme.surface,
        titleColor: Color = MaterialTheme.colorScheme.primary,
        contentColor: Color = MaterialTheme.colorScheme.onSurface,
    ): YangDialogColorConfig =
        YangDialogColorConfig(
            containerColor = containerColor,
            titleColor = titleColor,
            contentColor = contentColor
        )
}

/**
 * 弹窗底部的配置定义
 * @property showCancel Boolean 是否显示取消按钮
 * @property showConfirm Boolean 是否显示确认按钮
 * @property confirmTip String 取消按钮的文字内容
 * @property cancelTip String 确认按钮的文字内容
 * @constructor
 */
@Immutable
class YangDialogBottomConfig internal constructor(
    private val showCancel: Boolean,
    private val showConfirm: Boolean,
    private val confirmTip: String,
    private val cancelTip: String,
    private val cancelActive:Boolean,
    private val confirmActive:Boolean,
) {
    @Composable
    internal fun showConfirm(): State<Boolean> {
        return rememberUpdatedState(showConfirm)
    }

    @Composable
    internal fun showCancel(): State<Boolean> {
        return rememberUpdatedState(showCancel)
    }

    @Composable
    internal fun confirmTip(): State<String> {
        return rememberUpdatedState(confirmTip)
    }

    @Composable
    internal fun cancelTip(): State<String> {
        return rememberUpdatedState(cancelTip)
    }
    @Composable
    internal fun cancelActive(): State<Boolean> {
        return rememberUpdatedState(cancelActive)
    }
    @Composable
    internal fun confirmActive(): State<Boolean> {
        return rememberUpdatedState(confirmActive)
    }
}

/**
 * 弹窗颜色配置定义
 * @property containerColor Color 背景色
 * @property titleColor Color 标题颜色
 * @property contentColor Color 内容颜色
 * @constructor
 */
@Immutable
class YangDialogColorConfig internal constructor(
    private val containerColor: Color,
    private val titleColor: Color,
    private val contentColor: Color
) {
    @Composable
    internal fun containerColor(): State<Color> {
        return rememberUpdatedState(containerColor)
    }

    @Composable
    internal fun contentColor(): State<Color> {
        return rememberUpdatedState(contentColor)
    }

    @Composable
    internal fun titleColor(): State<Color> {
        return rememberUpdatedState(titleColor)
    }
}

/**
 * 弹窗当前的状态
 */
enum class YangDialogLoadingState {
    LOADING, //加载中
    SUCCESS,//通过
    ERROR,//错误
    NOT_LOADING //没有处于加载状态
}