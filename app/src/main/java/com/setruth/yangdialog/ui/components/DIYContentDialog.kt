package com.setruth.yangdialog.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.setruth.yangdialog.YangDialog

@Composable
fun DIYContentDialog(dialogHide: () -> Unit) {
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
            dialogHide()
        },
        onConfirm = {
            dialogShow = false
            dialogHide()
        },
    ) {
        OutlinedTextField(value = inputContent, onValueChange = { inputContent = it })
    }
}