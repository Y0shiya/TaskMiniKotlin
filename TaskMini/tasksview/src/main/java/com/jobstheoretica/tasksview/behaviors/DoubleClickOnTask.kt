package com.jobstheoretica.tasksview.behaviors

import android.opengl.Visibility
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.behavior.bases.BehaviorBase
import com.jobstheoretica.tasksview.R
import com.jobstheoretica.tasksview.TasksActivity
import com.jobstheoretica.tasksview.viewmodels.TasksViewModel
import com.jobstheoretica.tasksview.views.TasksAdapter
import com.jobstheoretica.tasksview.views.TasksFragment
import java.util.*

internal class DoubleClickOnTask(itemView:View, fragmentActivity: TasksFragment, viewModel: TasksViewModel):BehaviorBase() {

    val fragmentActivity = fragmentActivity
    val sharedNavigator = fragmentActivity.sharedNavigator
    val viewModel = viewModel
    val checkedBoxView:ImageView
    val unCheckedBoxView:ImageView
    val idView:TextView
    val noteView:TextView
    val rv:RecyclerView
    private val clickedTimeHolders:MutableMap<Int, Calendar?>
    init {
        this.checkedBoxView = itemView.findViewById(R.id.checkedBoxView)
        this.unCheckedBoxView = itemView.findViewById(R.id.unCheckedBoxView)
        this.idView = itemView.findViewById(R.id.idView)
        this.noteView = itemView.findViewById(R.id.noteView)

        this.rv = fragmentActivity.view!!.findViewById(R.id.tasksRecyclerView)

        this.clickedTimeHolders = mutableMapOf(
                this.checkedBoxView.hashCode() to null
                ,this.noteView.hashCode() to null
        )
    }

    override fun subBehave() {
        if(!this.unCheckedBoxView.hasOnClickListeners()){
            this.unCheckedBoxView.setOnClickListener {
                if(!this.hasDoubleClicked(it))
                    return@setOnClickListener

                this.doubleClicked(it)
            }
        }

        if(!this.noteView.hasOnClickListeners()){
            this.noteView.setOnClickListener {
                if(!this.hasDoubleClicked(it))
                    return@setOnClickListener

                this.doubleClicked(it)
            }
        }
    }

    private fun clearClickedTime(key:Int){
        this.clickedTimeHolders[key] = null
    }

    private fun updateClickedTime(key:Int, dateTime:Calendar){
        this.clickedTimeHolders[key] = dateTime
    }

    private fun hasDoubleClicked(it:View):Boolean{

        val key = it.hashCode()

        if(this.clickedTimeHolders.get(key) == null){
            this.updateClickedTime(key, Calendar.getInstance())
            return false
        }
        else{
            val nowDateTime = Calendar.getInstance()
            val prevDateTime = this.clickedTimeHolders.get(key)!!

            if(nowDateTime.get(Calendar.DATE) == prevDateTime.get(Calendar.DATE) + 1){
                this.clearClickedTime(key)
                return false
            }

            prevDateTime.add(Calendar.SECOND, 2)
            if(nowDateTime.time <= prevDateTime.time){
                this.clearClickedTime(key)
                return true
            }
            else{
                this.updateClickedTime(key, nowDateTime)
                return false
            }
        }
    }

    private fun doubleClicked(it: View){
        if(it is TextView){
            val txtView = it as TextView
            if(txtView != null){

                val id = this.idView.text.toString()
                val bundleToAccountWriter = Bundle()
                bundleToAccountWriter.putString("id", id)
                this.sharedNavigator?.forward("TaskEditor", bundleToAccountWriter)
            }
        }
        else{
            this.checkedBoxView.visibility = View.VISIBLE
            this.unCheckedBoxView.visibility = View.GONE

            //Thread.sleep(1000)

            val id = this.idView.text.toString()
            if(this.viewModel.removeTaskCommand.canExecute(id)){
                val rvAdpt = this.rv.adapter as TasksAdapter
                val removeTask = rvAdpt.list.singleOrNull { it -> it.id == id }
                if(removeTask != null){
                    val removeIndex = rvAdpt.list.indexOf(removeTask)
                    rvAdpt.list.remove(removeTask)
                    rvAdpt.notifyItemRemoved(removeIndex)
                }
                this.viewModel.removeTaskCommand.execute(id)
            }
        }
    }
}