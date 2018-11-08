package com.jobstheoretica.taskmini.behaviors

import android.widget.ImageView
import androidx.navigation.NavController
import com.example.behavior.bases.BehaviorBase
import com.jobstheoretica.taskmini.R
import com.jobstheoretica.taskmini.EntryPointActivity
import kotlinx.android.synthetic.main.activity_entry_point.view.*

internal class ClickCommonBackArrowView(entryPointActivity: EntryPointActivity, navController: NavController):BehaviorBase() {

    private val entryPointActivity = entryPointActivity
    private val navController = navController
    private val backArrowView:ImageView
    init {
        this.backArrowView = this.entryPointActivity.findViewById(R.id.commonBackArrowView)
    }
    override fun subUpdateViewState() {
        this.backArrowView.setOnClickListener {
            this.navController.popBackStack()
        }
    }
}