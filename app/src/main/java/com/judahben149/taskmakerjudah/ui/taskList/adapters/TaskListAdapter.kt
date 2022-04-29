package com.judahben149.taskmakerjudah.ui.taskList.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.taskmakerjudah.R
import com.judahben149.taskmakerjudah.databinding.TaskItemBinding
import com.judahben149.taskmakerjudah.models.entities.Task
import com.judahben149.taskmakerjudah.ui.detailTask.activity.TaskDetailActivity
import com.judahben149.taskmakerjudah.utils.DateConverter
import com.judahben149.taskmakerjudah.utils.TimeConverter

class TaskListAdapter: PagedListAdapter<Task, TaskListAdapter.TaskListViewHolder>(DIFF_CALLBACK){

    inner class TaskListViewHolder(private var binding: TaskItemBinding): RecyclerView.ViewHolder(binding.root) {
        var taskTitle = binding.tvTaskTitle
        var checkboxCompleted = binding.checkboxCompleted
        var priority = binding.ivStarPriority
        var dateDue = binding.tvDueDate
        val timeDue = binding.tvDueTime

        fun bindItem(task: Task) {
            taskTitle.text = task.taskTitle
            checkboxCompleted.isChecked = task.isCompleted

            priority.setImageResource(if (task.isPriority) R.drawable.ic_star_filled_svgrepo_com else R.drawable.ic_star_outline_svgrepo_com)
            dateDue.text = DateConverter.convertTimeMillisToString(task.dateDueMillis)
            timeDue.text = TimeConverter.formatTime(task.timeDueMillis)

            itemView.setOnClickListener {
                val taskDetailIntent = Intent(itemView.context, TaskDetailActivity::class.java)
                taskDetailIntent.putExtra("TASK_ID", task.id)
                itemView.context.startActivity(taskDetailIntent)
            }


//            TODO("Add logic to mark a task as completed when you check the checkbox")
        }
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val task = getItem(position) as Task
        holder.bindItem(task)

        when {
            task.isCompleted -> {
                holder.checkboxCompleted.isChecked = true
            }
            else -> {
                holder.checkboxCompleted.isChecked = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        var binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskListViewHolder(binding)
    }



    companion object {
        var DIFF_CALLBACK = object : DiffUtil.ItemCallback<Task>() {
            override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
                return oldItem == newItem
            }

        }
    }
}