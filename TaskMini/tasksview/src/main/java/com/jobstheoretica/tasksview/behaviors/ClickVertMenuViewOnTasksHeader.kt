package com.jobstheoretica.tasksview.behaviors

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import com.example.behavior.bases.BehaviorBase
import com.example.navigation.interfaces.INavigator
import com.jobstheoretica.tasksview.databinding.FragmentTasksBinding
import com.jobstheoretica.tasksview.viewmodels.TasksViewModel
import com.jobstheoretica.tasksview.views.TasksFragment
import kotlinx.coroutines.experimental.newFixedThreadPoolContext

internal class ClickVertMenuViewOnTasksHeader(sharedNavigator:INavigator
        , vertMenuView:ImageView
        , fragment: TasksFragment
        , dataBinding: FragmentTasksBinding):BehaviorBase (){

    private val sharedNavigator = sharedNavigator
    private val vertMenuView = vertMenuView
    private val fragment = fragment
    private val dataBinding = dataBinding
    private val viewModel:TasksViewModel
    init {
        this.viewModel = dataBinding.tasksViewModel!!
    }

    override fun subBehave() {
        this.vertMenuView.setOnClickListener {
            val popupMenu = PopupMenu(this.fragment.activity, this.vertMenuView)
            popupMenu.menu.add("Add")
            //popupMenu.menu.add("Settings")
            popupMenu.menu.add("Trash")

            popupMenu.setOnMenuItemClickListener {
                if(this.viewModel.cancelJobCommand.canExecute(null)){
                    this.viewModel.cancelJobCommand.execute(null)
                }

                when(it.title){
                    "Add" -> {
                        val bundleToTaskWriter = Bundle()
                        bundleToTaskWriter.putString("id", null)
                        this.sharedNavigator.forward("TaskWriter", bundleToTaskWriter)
                    }
                    "Trash" -> {
                        this.sharedNavigator.forward("Trash")
                    }
                }
                true
            }

            popupMenu.show()
        }
    }
}