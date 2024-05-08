package com.autthapol.wellnessapp

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WellnessScreen(
    modifier: Modifier = Modifier,
    viewModel: WellnessViewModel = viewModel(),
) {
    Column(modifier = modifier) {
        StatefulCounter()

        WellnessTaskList(list = viewModel.tasks, onCloseTask = { task ->
            viewModel.remove(task)
        }, onCheckedTask = { task, checked ->
            viewModel.changeTaskChecked(task, checked)
        })
    }
}