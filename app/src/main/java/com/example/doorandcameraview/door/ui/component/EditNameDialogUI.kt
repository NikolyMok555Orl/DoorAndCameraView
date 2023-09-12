package com.example.doorandcameraview.door.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.doorandcameraview.door.data.model.Door
import com.example.doorandcameraview.ui.theme.DoorAndCameraViewTheme


@Composable
fun EditNameDialogUI(
    door: Door,
    onClose: () -> Unit, onSave: (id: Int, name: String) -> Unit
) {
    val name = remember(door) {
        mutableStateOf(door.name)
    }


    EditNameDialogUI(name.value, {
        name.value = it
    }, onClose, {
        onSave(door.id, name.value)
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditNameDialogUI(
    text: String, onNameChange: (newName: String) -> Unit,
    onClose: () -> Unit, onSave: () -> Unit
) {
    Dialog(onDismissRequest = { }) {
        Card {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Измените имя двери")
                OutlinedTextField(
                    value = text, onValueChange = onNameChange,
                    modifier = Modifier.fillMaxWidth()
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(onClick = onClose, modifier = Modifier.padding(4.dp)) {
                        Text(text = "Закрыть")
                    }
                    Button(onClick = onSave, modifier = Modifier.padding(4.dp)) {
                        Text(text = "Сохранить")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditNameDialogUIPreview() {
    DoorAndCameraViewTheme {
        Box(modifier = Modifier.fillMaxSize()) {
            EditNameDialogUI(text = "", onNameChange = {}, onClose = {}, onSave = {})
        }
    }
}