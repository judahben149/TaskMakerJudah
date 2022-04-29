package com.judahben149.taskmakerjudah

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.judahben149.taskmakerjudah.ui.addTask.viewmodel.AddTaskViewModel
import com.judahben149.taskmakerjudah.ui.detailTask.viewmodel.TaskDetailViewModel
import com.judahben149.taskmakerjudah.ui.taskList.viewmodel.TaskListViewModel

class ViewModelFactory private constructor(private val taskRepository: TaskRepository) :
    ViewModelProvider.Factory {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    TaskRepository.getInstance(context)
                )
            }
    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(TaskListViewModel::class.java) -> {
                TaskListViewModel(taskRepository) as T
            }

            modelClass.isAssignableFrom(AddTaskViewModel::class.java) -> {
                AddTaskViewModel(taskRepository) as T
            }

            modelClass.isAssignableFrom(TaskDetailViewModel::class.java) -> {
                TaskDetailViewModel(taskRepository) as T
            }

            else -> throw Throwable("Unknown viewModel class" + modelClass.name)
        }
}