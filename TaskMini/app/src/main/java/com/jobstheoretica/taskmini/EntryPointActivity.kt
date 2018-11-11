package com.jobstheoretica.taskmini

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.navigation.factories.NavigatorFactory
import com.example.navigation.interfaces.INavigator
import com.jobstheoretica.taskmini.behaviors.ClickCommonBackArrowView
import com.jobstheoretica.tasksview.views.TasksFragment
import com.jobstheoretica.taskwriterview.views.TaskWriterFragment
import com.jobstheoretica.trashview.views.TrashFragment
import io.realm.Realm

class EntryPointActivity : AppCompatActivity(), TasksFragment.OnFragmentInteractionListener, TaskWriterFragment.OnFragmentInteractionListener,TrashFragment.OnFragmentInteractionListener{

    private var navController:NavController? = null

    private val kvOfViews:Map<String, Int> = mapOf(
            "TaskEditor" to R.id.tasksToTaskEditor
            , "TaskWriter" to R.id.tasksToTaskWriter
            , "Trash" to R.id.tasksToTrash)

    private var navigator:INavigator? = null

    private var commonBackArrowView:ImageView? = null
    private var tasksHeaderLabelView:TextView? = null
    private var addHeaderLabelView:TextView? = null
    private var editHeaderLabelView:TextView? = null
    private var trashHeaderLabelView:TextView? = null
    private var vertMenuVewOnTasksHeader:ImageView? = null
    private var saveViewOnTaskWriterHeader:ImageView? = null
    private var vertMenuViewOnTrashHeader:ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry_point)

        Realm.init(applicationContext)

        this.commonBackArrowView = findViewById(R.id.commonBackArrowView)
        this.tasksHeaderLabelView = findViewById(R.id.tasksHeaderLabelView)
        this.addHeaderLabelView = findViewById(R.id.addHeaderLabelView)
        this.editHeaderLabelView = findViewById(R.id.editHeaderLabelView)
        this.trashHeaderLabelView = findViewById(R.id.trashHeaderLabelView)
        this.vertMenuVewOnTasksHeader = findViewById(R.id.vertMenuViewOnTasksHeader)
        this.saveViewOnTaskWriterHeader = findViewById(R.id.saveViewOnTaskWriterHeader)
        this.vertMenuViewOnTrashHeader = findViewById(R.id.vertMenuViewOnTrashHeader)

        this.navController = findNavController(R.id.naviHostFragment)
        this.navController?.addOnNavigatedListener { controller, destination ->
            when(destination.id){
                R.id.tasksFragment -> {
                    this.commonBackArrowView?.visibility = View.INVISIBLE

                    this.tasksHeaderLabelView?.visibility = View.VISIBLE
                    this.addHeaderLabelView?.visibility = View.GONE
                    this.editHeaderLabelView?.visibility = View.GONE
                    this.trashHeaderLabelView?.visibility = View.GONE

                    this.vertMenuVewOnTasksHeader?.visibility = View.VISIBLE
                    this.saveViewOnTaskWriterHeader?.visibility = View.GONE
                    this.vertMenuViewOnTrashHeader?.visibility = View.GONE
                }
                R.id.taskWriterFragment -> {
                    this.commonBackArrowView?.visibility = View.VISIBLE

                    this.tasksHeaderLabelView?.visibility = View.GONE
                    this.addHeaderLabelView?.visibility = View.VISIBLE
                    this.editHeaderLabelView?.visibility = View.GONE
                    this.trashHeaderLabelView?.visibility = View.GONE

                    this.vertMenuVewOnTasksHeader?.visibility = View.GONE
                    this.saveViewOnTaskWriterHeader?.visibility = View.VISIBLE
                    this.vertMenuViewOnTrashHeader?.visibility = View.GONE
                }
                R.id.taskEditorFragment -> {
                    this.commonBackArrowView?.visibility = View.VISIBLE

                    this.tasksHeaderLabelView?.visibility = View.GONE
                    this.addHeaderLabelView?.visibility = View.GONE
                    this.editHeaderLabelView?.visibility = View.VISIBLE
                    this.trashHeaderLabelView?.visibility = View.GONE

                    this.vertMenuVewOnTasksHeader?.visibility = View.GONE
                    this.saveViewOnTaskWriterHeader?.visibility = View.VISIBLE
                    this.vertMenuViewOnTrashHeader?.visibility = View.GONE
                }
                R.id.trashFragment -> {
                    this.commonBackArrowView?.visibility = View.VISIBLE

                    this.tasksHeaderLabelView?.visibility = View.GONE
                    this.addHeaderLabelView?.visibility = View.GONE
                    this.editHeaderLabelView?.visibility = View.GONE
                    this.trashHeaderLabelView?.visibility = View.VISIBLE

                    this.vertMenuVewOnTasksHeader?.visibility = View.GONE
                    this.saveViewOnTaskWriterHeader?.visibility = View.GONE
                    this.vertMenuViewOnTrashHeader?.visibility = View.VISIBLE
                }
            }
        }
        this.navigator = NavigatorFactory(this.navController!!, this.kvOfViews).create()

        ClickCommonBackArrowView(this, this.navController!!).updateViewState()
    }

    override fun onFragmentInteraction(uri: Uri) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}