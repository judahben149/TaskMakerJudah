package com.judahben149.taskmakerjudah.database

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.judahben149.taskmakerjudah.models.entities.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): DataSource.Factory<Int, Task>

    @Query("SELECT * FROM tasks")
    fun getAllTasksAsList(): List<Task>

    @Query("SELECT * FROM tasks WHERE isCompleted=0")
    fun getActiveTasks(): DataSource.Factory<Int, Task>

    @Query("SELECT * FROM tasks WHERE isCompleted=1")
    fun getCompletedTasks(): DataSource.Factory<Int, Task>

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    fun getTaskById(taskId: Int): LiveData<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)
}