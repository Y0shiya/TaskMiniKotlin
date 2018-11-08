package com.jobstheoretica.taskwriterview.behaviors

import android.arch.lifecycle.Observer
import com.example.behavior.bases.BehaviorBase
import com.jobstheoretica.taskwriterview.databinding.FragmentTaskWriterBinding
import com.jobstheoretica.taskwriterview.views.TaskWriterFragment

internal class ViewInitializer(id:String?, fragment:TaskWriterFragment, dataBinding:FragmentTaskWriterBinding):BehaviorBase() {

    private val id = id
    private val fragment = fragment
    private val dataBinding = dataBinding
    private val viewModel = dataBinding.myViewModel!!

    override fun subUpdateViewState() {

        this.viewModel.taskLiveData.observe(this.fragment.viewLifecycleOwner, Observer {
            if(it != null){
                this.dataBinding.task = it
            }
        })
    }

    override fun subEventToCommand() {
        if(this.viewModel.readTaskCommand.canExecute(this.id)){
            this.viewModel.readTaskCommand.execute(this.id)
        }
    }
}