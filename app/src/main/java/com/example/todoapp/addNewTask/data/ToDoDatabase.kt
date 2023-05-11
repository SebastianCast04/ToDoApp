package com.example.todoapp.addNewTask.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1)

abstract class ToDoDatabase:RoomDatabase() {

    //DAO
    abstract fun taskDao():TaskDao
}