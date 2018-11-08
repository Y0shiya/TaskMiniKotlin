package com.jobstheoretica.tasksview.views

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.navigation.factories.SharedNavigatorFactory
import com.example.navigation.interfaces.INavigator
import com.jobstheoretica.entity.bindable.Task

import com.jobstheoretica.tasksview.R
import com.jobstheoretica.tasksview.databinding.FragmentTasksBinding
import com.jobstheoretica.tasksview.viewmodels.TasksViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TasksFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TasksFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
public class TasksFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    internal var sharedNavigator:INavigator? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if(this.sharedNavigator == null){
            this.sharedNavigator = SharedNavigatorFactory(this).create()
        }

        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        val fragmentAccountsBinding = FragmentTasksBinding.bind(view);
        val vm = ViewModelProviders.of(this).get(TasksViewModel::class.java)
        fragmentAccountsBinding.tasksViewModel = vm

        val rv = view.findViewById<RecyclerView>(R.id.tasksRecyclerView)
        val tasks = mutableListOf<com.jobstheoretica.entity.bindable.Task>()

        val adpt = TasksAdapter(this, vm, tasks)
        rv.setHasFixedSize(false)
        rv.layoutManager = LinearLayoutManager(this.context)
        rv.adapter = adpt

        vm.tasksLiveData.observe(this, Observer {
            if(it != null){
                val oldSize = adpt.list.size
                adpt.list.clear()
                adpt.list.addAll(it.toTypedArray())
                adpt.notifyDataSetChanged()

            }
        })

        vm.readTasksCommand.execute(null)

        //for debug
        vm.debugViewModelMsgLiveData.observe(this, Observer {
            if(it != null){
                Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
            }
        })
        vm.debugModelMsgLiveData.observe(this, Observer {
            if(it != null){
                Toast.makeText(this.context, it, Toast.LENGTH_SHORT).show()
            }
        })

        return fragmentAccountsBinding.root
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TasksFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
