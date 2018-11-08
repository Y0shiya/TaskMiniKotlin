package com.jobstheoretica.trashview.behaviors

import android.support.design.widget.CoordinatorLayout
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.PopupMenu
import com.example.behavior.bases.BehaviorBase
import com.example.behavior.interfaces.IBehavior
import com.jobstheoretica.entity.bindable.Trash
import com.jobstheoretica.trashview.R
import com.jobstheoretica.trashview.databinding.FragmentTrashBinding
import com.jobstheoretica.trashview.viewmodels.TrashViewModel
import com.jobstheoretica.trashview.views.TrashAdapter
import com.jobstheoretica.trashview.views.TrashFragment
import kotlinx.android.synthetic.main.fragment_trash.view.*

internal class ClickVertMenuViewOnTrashHeader(vertMenuView:ImageView
                                              , trashFragment: TrashFragment
                                              , dataBinding: FragmentTrashBinding):BehaviorBase() {

    private val vertMenuView = vertMenuView
    private val trashFragment = trashFragment
    private val dataBinding:FragmentTrashBinding
    private val viewModel:TrashViewModel
    private val rv:RecyclerView
    private val adpt:TrashAdapter
    init {
        this.dataBinding = dataBinding
        this.viewModel = dataBinding.myViewModel!!
        this.rv = dataBinding.trashRecyclerView
        this.adpt = this.rv.adapter as TrashAdapter
    }

    override fun subBehave() {
        this.vertMenuView.setOnClickListener {
            val popUpMenu = PopupMenu(this.trashFragment.context, this.vertMenuView)
            popUpMenu.menu.add("Delete all")

            popUpMenu.setOnMenuItemClickListener {
                when(it.title){
                    "Delete all" -> {
                        this.viewModel.deleteCommand.execute(null)

                        val itemSize = this.adpt.list.size
                        this.adpt.list.clear()
                        this.adpt.notifyItemRangeRemoved(0, itemSize)
                    }
                }

                true
            }

            popUpMenu.show()
        }
    }

    override fun subUpdateViewState() {

    }

    override fun subEventToCommand() {

    }
}