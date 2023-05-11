package com.example.todoapp.addNewTask.domain

import com.example.todoapp.addNewTask.data.TaskRepository
import com.example.todoapp.addNewTask.ui.model.TaskModel
import javax.inject.Inject


class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    
    suspend operator fun invoke (taskModel: TaskModel){

        taskRepository.add(taskModel)
    }
}