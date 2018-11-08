package com.jobstheoretica.trashview.views

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jobstheoretica.entity.bindable.Trash
import com.jobstheoretica.trashview.R
import com.jobstheoretica.trashview.behaviors.ClickVertMenuViewOnTrash
import com.jobstheoretica.trashview.viewmodels.TrashViewModel
import kotlinx.android.synthetic.main.item_trash.view.*

internal class TrashAdapter(trashFragment: TrashFragment, viewModel: TrashViewModel, list:MutableList<Trash>):RecyclerView.Adapter<TrashAdapter.TrashViewHolder>() {

    private val trashFragment = trashFragment
    private val viewModel = viewModel
    internal val list = list

    class TrashViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val idView = itemView.findViewById<TextView>(R.id.idView)
        val noteView = itemView.findViewById<TextView>(R.id.noteView)
    }

    override fun getItemCount(): Int {
        return this.list.count()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TrashViewHolder {
        val viewItem = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_trash, viewGroup, false)
        ClickVertMenuViewOnTrash(viewItem, this, this.trashFragment, this.viewModel)
                .behave()
        return TrashAdapter.TrashViewHolder(viewItem)
    }

    override fun onBindViewHolder(viewHolder: TrashViewHolder, position: Int) {
        val trash = this.list[position]

        viewHolder.idView.text = trash.id
        viewHolder.noteView.text = trash.note
    }
}