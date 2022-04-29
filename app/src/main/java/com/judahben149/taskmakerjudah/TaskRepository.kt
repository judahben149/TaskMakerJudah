package com.judahben149.taskmakerjudah

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.judahben149.taskmakerjudah.database.TaskDao
import com.judahben149.taskmakerjudah.database.TaskDatabase
import com.judahben149.taskmakerjudah.models.entities.Task

class TaskRepository(private val taskDao: TaskDao) {

    companion object {
        const val PAGE_SIZE = 30
        const val PLACEHOLDERS = true

        private val PAGING_CONFIG = PagedList.Config.Builder().apply {
            setEnablePlaceholders(PLACEHOLDERS)
            setPageSize(PAGE_SIZE)
        }.build()

        @Volatile
        private var INSTANCE: TaskRepository? = null

        fun getInstance(context: Context): TaskRepository {
            return INSTANCE ?: synchronized(this) {
                if (INSTANCE == null) {
                    val taskDatabase = TaskDatabase.getInstance(context)
                    INSTANCE = TaskRepository(taskDatabase.taskDao())
                }
                return INSTANCE as TaskRepository
            }
        }
    }

    fun getAllTasks(): LiveData<PagedList<Task>> {
        val pagingSource = taskDao.getAllTasks()
        return  LivePagedListBuilder(pagingSource, PAGING_CONFIG).build()
    }

    fun getAllTasksAsList(): List<Task> {
        return taskDao.getAllTasksAsList()
    }

    fun getActiveTasks(): LiveData<PagedList<Task>> {
        val pagingSource = taskDao.getActiveTasks()
        return LivePagedListBuilder(pagingSource, PAGING_CONFIG).build()
    }

    fun getCompletedTasks(): LiveData<PagedList<Task>> {
        val pagingSource = taskDao.getCompletedTasks()
        return LivePagedListBuilder(pagingSource, PAGING_CONFIG).build()
    }

    fun getTaskById(taskId: Int): LiveData<Task> {
        return taskDao.getTaskById(taskId)
    }

    suspend fun addTask(newTask: Task) {
        return taskDao.addTask(newTask)
    }

    suspend fun updateTask(task: Task) {
        return taskDao.updateTask(task)
    }

}