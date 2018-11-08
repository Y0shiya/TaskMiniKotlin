package com.jobstheoretica.tasksview.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jobstheoretica.tasksview.R
import com.jobstheoretica.tasksview.behaviors.ClickVertMenuViewOnTask
import com.jobstheoretica.tasksview.behaviors.DoubleClickOnTask
import com.jobstheoretica.tasksview.viewmodels.TasksViewModel
import com.jobstheoretica.entity.bindable.Task

internal class TasksAdapter(tasksFragment: TasksFragment, viewModel: TasksViewModel, list: MutableList<Task>):RecyclerView.Adapter<TasksAdapter.TaskViewHolder>() {

    private val tasksFragment = tasksFragment
    private val viewModel = viewModel
    internal var list = list

    class TaskViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){

        val idView:TextView = itemView.findViewById(R.id.idView)
        val noteView = itemView.findViewById<TextView>(R.id.noteView)
    }

    override fun getItemCount(): Int {
        return this.list.count()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TaskViewHolder {
        val viewItem = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_task, viewGroup, false)
        DoubleClickOnTask(viewItem, this.tasksFragment, this.viewModel).eventToCommand()
        //ClickVertMenuViewOnTask(viewItem, this, this.tasksFragment, this.viewModel).eventToCommand().updateViewState()
        return TaskViewHolder(viewItem)
    }

    override fun onBindViewHolder(viewHolder: TaskViewHolder, position: Int) {
        val task = this.list[position]

        viewHolder.idView.text = task.id
        viewHolder.noteView.text = task.note
    }
}