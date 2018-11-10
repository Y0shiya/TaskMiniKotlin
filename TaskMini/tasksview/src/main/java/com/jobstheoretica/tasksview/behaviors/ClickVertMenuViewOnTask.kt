package com.jobstheoretica.tasksview.behaviors

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.example.behavior.bases.BehaviorBase
import com.example.navigation.interfaces.INavigator
import com.jobstheoretica.entity.bindable.Task
import com.jobstheoretica.tasksview.R
import com.jobstheoretica.tasksview.viewmodels.TasksViewModel
import com.jobstheoretica.tasksview.views.TasksAdapter
import com.jobstheoretica.tasksview.views.TasksFragment
import java.lang.Exception

internal class ClickVertMenuViewOnTask(itemView: View, rvAdpt: TasksAdapter, tasksFragment: TasksFragment, viewModel: TasksViewModel):BehaviorBase() {

    private val itemView = itemView
    private val rvAdpt = rvAdpt
    private val tasksFragment = tasksFragment
    private val sharedNavigator:INavigator
    private val viewModel = viewModel
    private val vertMenuViewOnCd:ImageView
    private val idView:TextView
    private val rv:RecyclerView
    init {
        this.sharedNavigator = this.tasksFragment.sharedNavigator!!
        this.vertMenuViewOnCd = this.itemView.findViewById(R.id.vertMenuViewOnTask)
        this.idView = this.itemView.findViewById<TextView>(R.id.idView)
        this.rv = this.tasksFragment.view!!.findViewById(R.id.tasksRecyclerView)
    }

    override fun subBehave() {
        this.vertMenuViewOnCd.setOnClickListener {
            val popUpMenu = PopupMenu(this.tasksFragment.context, this.vertMenuViewOnCd)
            popUpMenu.menu.add("Edit")
            popUpMenu.menu.add("Remove")

            popUpMenu.setOnMenuItemClickListener {
                when(it.title){
                    "Edit" -> {
                        val id = this.idView.text.toString()
                        val bundleToAccountWriter = Bundle()
                        bundleToAccountWriter.putString("id", id)
                        this.sharedNavigator.forward("TaskEditor", bundleToAccountWriter)
                    }
                    "Remove" -> {
                        val id = this.idView.text.toString()
                        if(this.viewModel.removeTaskCommand.canExecute(id)){
                            val removeTask = this.rvAdpt.list.singleOrNull { it -> it.id == id }
                            if(removeTask != null){
                                val removeIndex = this.rvAdpt.list.indexOf(removeTask)
                                this.rvAdpt.list.remove(removeTask)
                                this.rvAdpt.notifyItemRemoved(removeIndex)
                            }
                            this.viewModel.removeTaskCommand.execute(id)
                        }
                    }
                }
                true
            }

            popUpMenu.show()
        }
    }
}