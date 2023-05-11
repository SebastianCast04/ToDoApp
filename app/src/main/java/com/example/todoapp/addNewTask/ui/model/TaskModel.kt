package com.example.todoapp.addNewTask.ui.model

data class TaskModel(
    val task: String,
    var selected: Boolean = false,
    val id: Int = System.currentTimeMillis().hashCode()
)