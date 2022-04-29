package com.judahben149.taskmakerjudah.ui.taskList.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.judahben149.TaskFilter
import com.judahben149.taskmakerjudah.TaskRepository
import com.judahben149.taskmakerjudah.models.entities.Task
import kotlinx.coroutines.launch

class TaskListViewModel(private val repository: TaskRepository): ViewModel() {

    val _filter = MutableLiveData<TaskFilter>()

    fun updateTaskFilter(taskFilter: TaskFilter) {
        _filter.value = taskFilter
    }

    fun updateTask(task: Task) {
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    val tasks: LiveData<PagedList<Task>> = _filter.switchMap { taskFilter ->
        when (taskFilter) {
            TaskFilter.ALL_TASKS -> {
                repository.getAllTasks()
            }

            TaskFilter.ACTIVE_TASKS -> {
                repository.getActiveTasks()
            }

            else -> {
                repository.getCompletedTasks()
            }
        }
    }

    init {
        _filter.value = TaskFilter.ALL_TASKS
    }
}