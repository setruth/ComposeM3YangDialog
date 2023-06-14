package com.setruth.yangdialog.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.setruth.yangdialog.YangDialog
import com.setruth.yangdialog.YangDialogDefaults

@Composable
fun BottomDIYDialog(dialogHide: () -> Unit) {
    var dialogShow by remember {
        mutableStateOf(true)
    }
    YangDialog(
        title = "自定义底部选项的弹窗",
        isShow = dialogShow,
        onCancel = {
            dialogShow = false
            dialogHide()
        },
        onConfirm = {
            dialogShow = false
            dialogHide()
        },
        bottomConfig = YangDialogDefaults.bottomConfig(
            showCancel = false,
            confirmTip = "确认将会关闭弹窗"
        )
    ) {
        Text(text = "hello")
    }
}