package com.jobstheoretica.taskwriterview.behaviors

import android.arch.lifecycle.Observer
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.behavior.bases.BehaviorBase
import com.example.navigation.interfaces.INavigator
import com.jobstheoretica.taskwriterview.databinding.FragmentTaskWriterBinding
import com.jobstheoretica.taskwriterview.viewmodels.TaskWriterViewModel
import com.jobstheoretica.taskwriterview.views.TaskWriterFragment

internal class ClickSaveViewOnTaskWriterHeader(saveView: ImageView, fragment: TaskWriterFragment, dataBinding: FragmentTaskWriterBinding):BehaviorBase() {

    private val saveView = saveView
    private val fragment = fragment
    private val sharedINavigator:INavigator
    private val dataBinding:FragmentTaskWriterBinding
    private val viewModel: TaskWriterViewModel
    private val requiredViews:Map<String, EditText>
    init {
        this.sharedINavigator = fragment.sharedNavigator!!
        this.dataBinding = dataBinding!!
        this.viewModel = dataBinding.myViewModel!!
        this.requiredViews = mapOf("Task or Note..." to this.dataBinding.noteView)
    }

    override fun subUpdateViewState() {
        this.viewModel.notSaveLiveData.observe(this.fragment.viewLifecycleOwner, Observer {
            if(it != null){
                //未入力必須項目へフォーカス
                this.requiredViews.get(it)?.requestFocus()

                //入力してください。：必須項目
                Toast.makeText(this.fragment.context, "入力してください。：" + it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun subEventToCommand() {

        this.saveView.setOnClickListener {
            val bindingTask = this.dataBinding.task
            if(bindingTask != null){
                if(this.viewModel.saveTaskCommand.canExecute(bindingTask)){
                    this.viewModel.saveTaskCommand.execute(bindingTask)

                    this.sharedINavigator?.back()
                }
            }
        }
    }
}