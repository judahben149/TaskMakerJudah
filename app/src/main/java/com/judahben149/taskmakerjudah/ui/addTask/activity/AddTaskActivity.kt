package com.judahben149.taskmakerjudah.ui.addTask.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.judahben149.taskmakerjudah.R
import com.judahben149.taskmakerjudah.ViewModelFactory
import com.judahben149.taskmakerjudah.databinding.ActivityAddTaskBinding
import com.judahben149.taskmakerjudah.models.entities.Task
import com.judahben149.taskmakerjudah.notification.TaskReminder
import com.judahben149.taskmakerjudah.ui.addTask.viewmodel.AddTaskViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private var _binding : ActivityAddTaskBinding? = null
    val binding get() = _binding!!

    private lateinit var addTaskViewModel: AddTaskViewModel

    var dateDueMillis: Long = 0
    var timeDueMillis: Long = 0

    var taskForReminder: Task? = null

    var day = 0
    var month = 0
    var year = 0

    var hour = 0
    var minute = 0

    var selectedDay = 0
    var selectedMonth = 0
    var selectedYear = 0
    var selectedHour = 0
    var selectedMinute = 0

    var dateText = ""
    var timeText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setUpViewModel()

        binding.tvDueDateAddTaskFragment.setOnClickListener {
            val calendar: Calendar = Calendar.getInstance()
            day = calendar.get(Calendar.DAY_OF_MONTH)
            month = calendar.get(Calendar.MONTH)
            year = calendar.get(Calendar.YEAR)

            hour = calendar.get(Calendar.HOUR_OF_DAY)
            minute = calendar.get(Calendar.MINUTE)

            val datePickerDialog = DatePickerDialog(this, this, year, month, day)
            datePickerDialog.show()
        }
    }

    private fun setUpViewModel() {
        val factory = ViewModelFactory.getInstance(this)
        addTaskViewModel = ViewModelProvider(this, factory)[AddTaskViewModel::class.java]
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_task_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.save -> {
                saveNewTask()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveNewTask() {
        val taskTitle = binding.tvTaskTitleAddTaskFragment.text.toString()
        val isPriority = binding.switchPriorityAddTaskFragment.isChecked

        val task = Task(
            taskTitle = taskTitle,
            isCompleted = false,
            isPriority = isPriority,
            dateDueMillis = dateDueMillis,
            timeDueMillis = timeDueMillis
        )
        addTaskViewModel.saveNewTask(task)
        taskForReminder = task

        setTaskReminder(timeDueMillis)
        finish()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        Log.d("reminder", "On date set fxn called")
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)

        selectedDay = dayOfMonth
        selectedMonth = month
        selectedYear = year


        val simpleDateFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        dateText = simpleDateFormat.format(calendar.time)

        dateDueMillis = calendar.timeInMillis
        TimePickerDialog(this, this, hour, minute, false).show()
    }

    override fun onTimeSet(view: TimePicker?, selectedHour: Int, selectedMinute: Int) {
        Log.d("reminder", "On time set fxn called")
        val calendar = Calendar.getInstance()
        calendar.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)

        Log.d("ADD", "$selectedHour:$selectedMinute")

        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.US)
        timeText = simpleDateFormat.format(calendar.time)

        timeDueMillis = calendar.timeInMillis
        binding.tvDueDateAddTaskFragment.text = timeText + ", "+ dateText
    }

    private fun setTaskReminder(remainingTime: Long) {
        val taskReminder = TaskReminder()
        Log.d("reminder", "Task reminder set in Add task activity")

        if (taskForReminder != null) {
            taskReminder.setTaskReminder(this, taskForReminder!!, remainingTime)
            Toast.makeText(this, taskForReminder!!.id.toString(), Toast.LENGTH_SHORT).show()
            Log.d("reminder", "Task reminder function called in broadcast receiver")
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}