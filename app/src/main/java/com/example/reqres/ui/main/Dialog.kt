package com.example.reqres.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DialogShow(title: String,context:String) {
    val dialogState = remember {
        mutableStateOf(true)
    }
    if (dialogState.value) {
       AlertDialog(onDismissRequest = {
           dialogState.value = false
       }, title = {
           Text(text = title)
       }, text = {
           Text(text = context)
       }, buttons = {
           Row(
               modifier = Modifier.padding(all = 8.dp),
               horizontalArrangement = Arrangement.Center
           ) {
               Button(
                   modifier = Modifier.fillMaxWidth(),
                   onClick = { dialogState.value = false }
               ) {
                   Text("Dismiss")
               }
           }
       })
    }
}