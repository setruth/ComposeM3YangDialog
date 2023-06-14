package com.setruth.yangdialog.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.setruth.yangdialog.YangDialog
import com.setruth.yangdialog.YangDialogDefaults
import com.setruth.yangdialog.YangDialogLoadingState
import kotlinx.coroutines.delay

@Composable
fun AnimateDialog(dialogHide: () -> Unit) {
    var dialogShow by remember {
        mutableStateOf(true)
    }
    var dialogType by remember {
        mutableStateOf(YangDialogLoadingState.NOT_LOADING)
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
           dialogType=YangDialogLoadingState.LOADING
           delay(1500)
           dialogType=YangDialogLoadingState.ERROR
           loadingTip="失败"
           delay(1500)
           dialogType=YangDialogLoadingState.SUCCESS
           loadingTip="成功"
       }
    }
    YangDialog(
        loadingState = dialogType,
        loadingTip = loadingTip,
        title = "这是自定义内容弹窗",
        isShow = dialogShow,
        onCancel = {
            dialogShow = false
            dialogHide()
        },
        onLoadingTipClick = {
            if (it==YangDialogLoadingState.SUCCESS) {
                dialogType=YangDialogLoadingState.NOT_LOADING
            }
        },
        onConfirm = {
           startLoading=true
        },
        bottomConfig = YangDialogDefaults.bottomConfig(
            confirmTip = "开始加载动画"
        )
    )
}