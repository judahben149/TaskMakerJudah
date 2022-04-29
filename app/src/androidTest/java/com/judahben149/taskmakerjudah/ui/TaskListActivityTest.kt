package com.judahben149.taskmakerjudah.ui

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.judahben149.taskmakerjudah.R
import com.judahben149.taskmakerjudah.ui.taskList.activity.TaskListActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TaskListActivityTest {

    @Test
    fun appLaunchesSuccessfully() {
        ActivityScenario.launch(TaskListActivity::class.java)
    }

    @Test
    fun taskListActivityIsDisplayedCorrectly() {
        ActivityScenario.launch(TaskListActivity::class.java)

        onView(withId(R.id.fab_add_new_task))
            .check(matches(isDisplayed()))
    }


}