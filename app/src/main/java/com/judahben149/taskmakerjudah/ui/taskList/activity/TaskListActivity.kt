package com.judahben149.taskmakerjudah.ui.taskList.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.taskmakerjudah.R
import com.judahben149.taskmakerjudah.ViewModelFactory
import com.judahben149.taskmakerjudah.databinding.ActivityTaskListBinding
import com.judahben149.taskmakerjudah.ui.addTask.activity.AddTaskActivity
import com.judahben149.taskmakerjudah.ui.settings.SettingsActivity
import com.judahben149.taskmakerjudah.ui.taskList.adapters.TaskListAdapter
import com.judahben149.taskmakerjudah.ui.taskList.viewmodel.TaskListViewModel

class TaskListActivity : AppCompatActivity() {

    private var _binding: ActivityTaskListBinding? = null
    private val binding get() = _binding!!


    private lateinit var taskListViewModel: TaskListViewModel

//    private val adapter by lazy {
//        TaskListAdapter { task ->
//            taskListViewModel.updateTask(task)
//        }
//    }

    private lateinit var adapter: TaskListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupRecyclerView()


        binding.fabAddNewTask.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        taskListViewModel = ViewModelProvider(this, factory)[TaskListViewModel::class.java]
    }

    private fun setupRecyclerView() {
        recyclerView = binding.recyclerViewTaskList
        adapter = TaskListAdapter()

        taskListViewModel.tasks.observe(this) { pagingData ->
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)

            adapter.submitList(pagingData)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings_menu) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}