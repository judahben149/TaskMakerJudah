package com.judahben149.taskmakerjudah.ui.addTask.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.taskmakerjudah.TaskRepository
import com.judahben149.taskmakerjudah.models.entities.Task
import kotlinx.coroutines.launch

class AddTaskViewModel(private val repository: TaskRepository): ViewModel() {

    fun saveNewTask(task: Task) {
        viewModelScope.launch {
            repository.addTask(task)
        }
    }

}