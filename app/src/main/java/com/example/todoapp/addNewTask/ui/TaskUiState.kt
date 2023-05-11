package com.example.todoapp.addNewTask.ui

import com.example.todoapp.addNewTask.ui.model.TaskModel

sealed interface TaskUiState {

    object Loading:TaskUiState
    data class Error(val throwable: Throwable):TaskUiState
    data class Success(val task: List<TaskModel>): TaskUiState

}