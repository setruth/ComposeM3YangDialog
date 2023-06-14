package com.setruth.yangdialog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.setruth.yangdialog.ui.components.AnimateDialog
import com.setruth.yangdialog.ui.components.BottomDIYDialog
import com.setruth.yangdialog.ui.components.CommonDialog
import com.setruth.yangdialog.ui.components.DIYContentDialog
import com.setruth.yangdialog.ui.theme.YangDialogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showDialogState by remember {
                mutableStateOf(ShowDialogType.No)
            }
            var show by remember {
                mutableStateOf(false)
            }
            when (showDialogState) {
                ShowDialogType.Common -> CommonDialog {
                    showDialogState = ShowDialogType.No
                }

                ShowDialogType.DIYContent -> DIYContentDialog {
                    showDialogState = ShowDialogType.No
                }

                ShowDialogType.BottomHide -> BottomDIYDialog {
                    showDialogState = ShowDialogType.No
                }

                ShowDialogType.AllLoading -> AnimateDialog {
                    showDialogState = ShowDialogType.No
                }

                ShowDialogType.No -> {}
            }

            YangDialogTheme {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { showDialogState = ShowDialogType.Common }) {
                        Text(text = "普通弹窗")
                    }
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Button(onClick = { showDialogState = ShowDialogType.DIYContent }) {
                        Text(text = "自定义内容")
                    }
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Button(onClick = { showDialogState = ShowDialogType.BottomHide }) {
                        Text(text = "底部隐藏")
                    }
                    Spacer(modifier = Modifier.padding(vertical = 10.dp))
                    Button(onClick = { showDialogState = ShowDialogType.AllLoading }) {
                        Text(text = "动画加载")
                    }
                }
            }


        }
    }
}

enum class ShowDialogType {
    Common,
    DIYContent,
    BottomHide,
    AllLoading,
    No
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YangDialogTheme {

    }
}