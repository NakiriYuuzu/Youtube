package core.presentation.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YuuzuDialog(
    openDialog: MutableState<Boolean>,
    content: @Composable () -> Unit,
    confirmText: String = "confirm",
    onConfirm: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    if (openDialog.value) {
        BasicAlertDialog(
            onDismissRequest = { openDialog.value = false }
        ) {
            Surface(
                modifier = modifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // 自定義內容區域
                    content()

                    Spacer(modifier = Modifier.height(24.dp))
                    TextButton(
                        onClick = {
                            onConfirm()
                            openDialog.value = false
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text(confirmText)
                    }
                }
            }
        }
    }
}