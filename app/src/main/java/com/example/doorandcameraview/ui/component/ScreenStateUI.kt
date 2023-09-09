package com.example.doorandcameraview.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/** Данные пустые*/
@Composable
fun DataIsEmpty(modifier: Modifier = Modifier, text: String="Данных нет"){
    Box(modifier = modifier.fillMaxSize(), contentAlignment= Alignment.Center) {
        Text(text, textAlign = TextAlign.Center, modifier = modifier.fillMaxWidth())
    }
}
/** Произошла ошибка*/
@Composable
fun DataIsError(modifier: Modifier = Modifier, text: String="Ошибка загрузки"){
    Box(modifier = modifier.fillMaxSize(), contentAlignment= Alignment.Center) {
        Text(text, textAlign = TextAlign.Center, modifier = modifier.fillMaxWidth())
    }
}

@Composable
fun DataIsLoading(modifier: Modifier = Modifier){
    Box(modifier = modifier.fillMaxSize(), contentAlignment= Alignment.Center) {
        CircularProgressIndicator(modifier = modifier.size(50.dp))
    }
}