package com.example.todoapp.addNewTask.data.dependencyInject

import android.content.Context
import androidx.room.Room
import com.example.todoapp.addNewTask.data.TaskDao
import com.example.todoapp.addNewTask.data.ToDoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    fun provideFunTaskDao(toDoDatabase: ToDoDatabase): TaskDao{

        return toDoDatabase.taskDao()
    }

    @Provides
    @Singleton
    fun provideToDoDataBase(@ApplicationContext appContext: Context): ToDoDatabase{

        return Room.databaseBuilder(appContext, ToDoDatabase::class.java, "TaskDataBase").build()
    }

}