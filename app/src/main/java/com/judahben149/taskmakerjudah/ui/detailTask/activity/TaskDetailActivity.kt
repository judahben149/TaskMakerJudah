package com.judahben149.taskmakerjudah.ui.detailTask.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.judahben149.taskmakerjudah.R
import com.judahben149.taskmakerjudah.ViewModelFactory
import com.judahben149.taskmakerjudah.databinding.ActivityTaskDetailBinding
import com.judahben149.taskmakerjudah.models.entities.Task
import com.judahben149.taskmakerjudah.ui.detailTask.viewmodel.TaskDetailViewModel
import com.judahben149.taskmakerjudah.utils.DateConverter
import com.judahben149.taskmakerjudah.utils.TimeConverter

class TaskDetailActivity : AppCompatActivity() {

    private var _binding: ActivityTaskDetailBinding? = null
    val binding get() = _binding!!

    private lateinit var taskDetailViewModel: TaskDetailViewModel
//    var taskInQuestion: Task? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val taskId = intent.extras?.getInt("TASK_ID")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        setupViewModel()
        Toast.makeText(this, taskId.toString(), Toast.LENGTH_SHORT).show()
        if (taskId != null) {
            taskDetailViewModel.setTaskId(taskId)
        }

        taskDetailViewModel.task.observe(this) {task->
            if (task != null) {

                binding.taskInfo.text = task.taskTitle
                binding.dateInfo.text = TimeConverter.formatTime(task.timeDueMillis) + ", "+ DateConverter.convertTimeMillisToString(task.dateDueMillis)

                if (task.isPriority) {
                    binding.ivPriorityStar.setImageResource(R.drawable.ic_star_filled_svgrepo_com)
                } else binding.ivPriorityStar.setImageResource(R.drawable.ic_star_outline_svgrepo_com)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        taskDetailViewModel = ViewModelProvider(this, factory)[TaskDetailViewModel::class.java]
    }
}