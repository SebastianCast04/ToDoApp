package com.example.todoapp.addNewTask.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.addNewTask.domain.AddTaskUseCase
import com.example.todoapp.addNewTask.domain.DeleteTaskUseCase
import com.example.todoapp.addNewTask.domain.GetTaskUseCase
import com.example.todoapp.addNewTask.domain.UpdateTaskUseCase
import com.example.todoapp.addNewTask.ui.TaskUiState.Success
import com.example.todoapp.addNewTask.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTaskUseCase: GetTaskUseCase
) : ViewModel() {

    val uiState: StateFlow<TaskUiState> = getTaskUseCase().map(::Success)
        .catch { TaskUiState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TaskUiState.Loading)

    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

    //private val _tasks = mutableStateListOf<TaskModel>()
   // val tasks: List<TaskModel> = _tasks

    fun onDialogClose() {

        _showDialog.value = false

    }

    fun onTaskCreated(task: String) {

        _showDialog.value = false

        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onShowDialogSelected() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {

        /* ACTUALIZAR CHECK

        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected)
        }
         */

        viewModelScope.launch {
            updateTaskUseCase(taskModel.copy(selected = ! taskModel.selected))
        }

    }

    fun onItemRemove(taskModel: TaskModel) {

        viewModelScope.launch {

            deleteTaskUseCase(taskModel)
        }

    }
}