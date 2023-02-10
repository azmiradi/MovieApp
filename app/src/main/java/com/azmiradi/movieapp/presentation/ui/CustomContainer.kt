package com.azmiradi.movieapp.presentation.ui

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import com.azmiradi.movieapp.presentation.BaseViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CustomContainer(
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    modifier: Modifier,
    baseViewModel: BaseViewModel,
    topBar: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    ProgressBar(isShow = baseViewModel.isLoading())

    val context = LocalContext.current

    if (baseViewModel.toastMessage().isNotEmpty()) {
        LaunchedEffect(key1 = baseViewModel.toastMessage())
        {
            Toast.makeText(context, baseViewModel.toastMessage(), Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(topBar = {
        if (topBar != null) {
            topBar()
        }
    }, content = {
        Column(
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            modifier = modifier,
            content = content
        )
    })

    OnLifecycleEvent(onEvent = { owner, event ->
        when (event) {
            Lifecycle.Event.ON_STOP -> {
                baseViewModel.resetState()
            }
            else -> {}
        }
    })
}