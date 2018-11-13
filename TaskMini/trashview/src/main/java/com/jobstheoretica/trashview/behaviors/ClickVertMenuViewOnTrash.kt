package com.jobstheoretica.trashview.behaviors

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.example.behavior.bases.BehaviorBase
import com.jobstheoretica.trashview.R
import com.jobstheoretica.trashview.viewmodels.TrashViewModel
import com.jobstheoretica.trashview.views.TrashAdapter
import com.jobstheoretica.trashview.views.TrashFragment
import kotlinx.android.synthetic.main.item_trash.view.*
import kotlin.concurrent.timerTask

internal class ClickVertMenuViewOnTrash(itemView: View
                                        , rvAdpt:TrashAdapter
                                        , trashFragment: TrashFragment
                                        , viewModel:TrashViewModel) :BehaviorBase(){

    private val itemView = itemView
    private val vertMenuView:ImageView
    private val rvAdpt = rvAdpt
    private val trashFragment = trashFragment
    private val viewModel = viewModel
    private val idView:TextView
    private val rv:RecyclerView
    init {
        this.idView = itemView.findViewById(R.id.idView)
        this.vertMenuView = itemView.findViewById(R.id.vertMenuViewOnTrash)
        this.rv = this.trashFragment.view!!.findViewById(R.id.trashRecyclerView)
    }

    override fun subBehave() {
        this.vertMenuView.setOnClickListener {
            val popUpMenu = PopupMenu(this.trashFragment.context, this.vertMenuView)
            popUpMenu.menu.add("Revert")
            popUpMenu.menu.add("Delete")

            popUpMenu.setOnMenuItemClickListener {
                when(it.title){
                    "Revert" -> {
                        val id = this.idView.text.toString()
                        val revertTrash = this.rvAdpt.list.singleOrNull { it.id == id }
                        if((revertTrash != null) && (this.viewModel.revertTrashCommand.canExecute(revertTrash))){
                            val revertIndex = this.rvAdpt.list.indexOf(revertTrash)
                            this.rvAdpt.list.remove(revertTrash)
                            this.rvAdpt.notifyItemRemoved(revertIndex)

                            this.viewModel.revertTrashCommand.execute(revertTrash)
                        }
                    }
                    "Delete" -> {
                        val id = this.idView.text.toString()
                        val deleteTrash = this.rvAdpt.list.singleOrNull { it -> it.id == id }
                        if((deleteTrash != null) && (this.viewModel.deleteCommand.canExecute((deleteTrash)))){
                            val deleteIndex = this.rvAdpt.list.indexOf(deleteTrash)
                            this.rvAdpt.list.remove(deleteTrash)
                            this.rvAdpt.notifyItemRemoved(deleteIndex)

                            this.viewModel.deleteCommand.execute(deleteTrash)
                        }
                    }
                }
                true
            }

            popUpMenu.show()
        }
    }
}