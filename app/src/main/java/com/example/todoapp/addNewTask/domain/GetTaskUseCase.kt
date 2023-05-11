package com.example.todoapp.addNewTask.domain

import com.example.todoapp.addNewTask.data.TaskRepository
import com.example.todoapp.addNewTask.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {

    operator fun invoke(): Flow<List<TaskModel>>{

        return taskRepository.task
    }
}