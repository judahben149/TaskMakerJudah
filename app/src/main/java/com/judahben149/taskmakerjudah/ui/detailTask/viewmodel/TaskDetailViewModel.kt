package com.judahben149.taskmakerjudah.ui.detailTask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.judahben149.taskmakerjudah.TaskRepository

class TaskDetailViewModel(private val repository: TaskRepository): ViewModel() {

    var _taskId = MutableLiveData<Int>()

    val task = _taskId.switchMap { taskId ->
        repository.getTaskById(taskId)
    }

    fun setTaskId(taskId: Int) {
        if (_taskId.value == taskId) {
            return
        }
        _taskId.value = taskId
    }
}