package com.setruth.yangdialog.ui.components

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.setruth.yangdialog.YangDialog
import com.setruth.yangdialog.YangDialogDefaults


@Composable
fun CommonDialog(dialogHide: () -> Unit) {
    var dialogShow by remember {
        mutableStateOf(true)
    }
    YangDialog(
        title = "这是普通弹窗",
        isShow = dialogShow,
        onCancel = {
            dialogShow = false
            dialogHide()
        },
        bottomConfig = YangDialogDefaults.bottomConfig(
            confirmActive = false
        ),
        onDismissRequest = {
            dialogShow = false
            dialogHide()
        },
    ) {
        Text(text = "hello")
    }
}