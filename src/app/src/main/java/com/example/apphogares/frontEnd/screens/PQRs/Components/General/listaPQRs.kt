package com.example.apphogares.frontEnd.screens.PQRs.Components.General

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apphogares.backEnd.core.models.PQR.PQRDocument


@Composable
fun  listaPQRs(items: List<PQRDocument>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        items(items) { item ->
            ListItemPQRView(item)
        }
    }
}