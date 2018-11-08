package com.jobstheoretica.taskmini.behaviors

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.navigation.NavController
import com.example.behavior.bases.BehaviorBase
import com.jobstheoretica.taskmini.EntryPointActivity
import com.jobstheoretica.taskmini.R

internal class ClickVertMenuViewOnTasksHeader(entryPointActivity: EntryPointActivity, navController: NavController):BehaviorBase() {

    private val entryPointActivity = entryPointActivity
    private val navController = navController
    private val vertMenuView: ImageView
    init {
        this.vertMenuView = this.entryPointActivity.findViewById(R.id.vertMenuViewOnTasksHeader)
    }

    override fun subUpdateViewState() {

        this.vertMenuView.setOnClickListener {
            val popUpMenu = PopupMenu(this.entryPointActivity, this.vertMenuView)
            popUpMenu.menu.add("Add")
            //popUpMenu.menu.add("Settings")
            popUpMenu.menu.add("Trash")

            popUpMenu.setOnMenuItemClickListener {
                when(it!!.title){
                    "Add" -> {
                        val bundleToAccountWriter = Bundle()
                        bundleToAccountWriter.putString("id", null)
                        this.navController.navigate(R.id.tasksToTaskWriter, bundleToAccountWriter)
                    }
                    "Settings" -> {

                    }
                    "Trash" -> {
                        this.navController.navigate(R.id.tasksToTrash)
                    }
                }
                //this.vertImageView.visibility = View.GONE
                true
            }

            popUpMenu.show()
        }
    }
}