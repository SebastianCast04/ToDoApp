package com.example.todoapp.addNewTask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.example.todoapp.addNewTask.ui.model.TaskModel


@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {

    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(false)

    val uiState by produceState<TaskUiState>(
        initialValue = TaskUiState.Loading,
        key1 = lifeCycle,
        key2 = taskViewModel
    ){
        lifeCycle.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            taskViewModel.uiState.collect{value = it} // Se actualiza siempre y el valor actual lo tendrá el uiState
        }
    }

    when (uiState){
        is TaskUiState.Error -> {}
        TaskUiState.Loading -> { CircularProgressIndicator()}
        is TaskUiState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {

                AddTaskDialog(
                    showDialog,
                    onDismiss = { taskViewModel.onDialogClose() },
                    onTaskAdded = { taskViewModel.onTaskCreated(it) })
                FabDialog(Modifier.align(Alignment.BottomEnd), taskViewModel)

                TaskList((uiState as TaskUiState.Success).task, taskViewModel)

            }
        }
    }
}

@Composable
fun TaskList(task: List<TaskModel>, taskViewModel: TaskViewModel) {

    LazyColumn {

        items(task, key = { it.id }) {

            ItemTask(taskModel = it, taskViewModel = taskViewModel)

        }

    }
}


@Composable
fun ItemTask(taskModel: TaskModel, taskViewModel: TaskViewModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {

                detectTapGestures(onLongPress = {

                    taskViewModel.onItemRemove(taskModel)
                })

            },
        elevation = 8.dp
    ) {

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = taskModel.task, modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
            )
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = { taskViewModel.onCheckBoxSelected(taskModel) })

        }
    }

}

@Composable
fun FabDialog(modifier: Modifier, taskViewModel: TaskViewModel) {
    FloatingActionButton(
        onClick = { taskViewModel.onShowDialogSelected() },
        modifier = modifier.padding(16.dp)
    ) {

        Icon(Icons.Filled.Add, contentDescription = "")

    }
}

@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {

    var myTask by remember { mutableStateOf("") }

    if (show) {

        Dialog(onDismissRequest = { onDismiss() }) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {

                Text(
                    text = "Añade tu nueva tarea",
                    fontSize = 16.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(16.dp))
                TextField(
                    value = myTask,
                    onValueChange = { myTask = it },
                    singleLine = true,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.size(16.dp))
                Button(onClick = {
                    onTaskAdded(myTask)
                    myTask = ""
                }, modifier = Modifier.fillMaxWidth()) {

                    Text(text = "Añadir tarea")

                }

            }

        }
    }


}
