package com.jobstheoretica.taskwriterview.views

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.navigation.factories.SharedNavigatorFactory
import com.example.navigation.interfaces.INavigator
import com.example.utility.impls.ActivityReflector

import com.jobstheoretica.taskwriterview.R
import com.jobstheoretica.taskwriterview.behaviors.ClickSaveViewOnTaskWriterHeader
import com.jobstheoretica.taskwriterview.behaviors.ViewInitializer
import com.jobstheoretica.taskwriterview.databinding.FragmentTaskWriterBinding
import com.jobstheoretica.taskwriterview.viewmodels.TaskWriterViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AccountWriterFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AccountWriterFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
public class TaskWriterFragment : Fragment() {
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
    internal var saveView:ImageView? = null
        get() {
            if(field == null){
                field = ActivityReflector<ImageView>(this.activity!!).getDeclaredField("saveViewOnTaskWriterHeader")
            }
            return field
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if(this.sharedNavigator == null){
            this.sharedNavigator = SharedNavigatorFactory(this).create()
        }

        val view = inflater.inflate(R.layout.fragment_task_writer, container, false)

        val fragmentTaskWriterBinding = FragmentTaskWriterBinding.bind(view)
        val vm = ViewModelProviders.of(this).get(TaskWriterViewModel::class.java)
        fragmentTaskWriterBinding.myViewModel = vm

        ViewInitializer(this.arguments?.getString("id"), this, fragmentTaskWriterBinding)
                .eventToCommand()
                .updateViewState()

        ClickSaveViewOnTaskWriterHeader(this.saveView!!, this, fragmentTaskWriterBinding)
                .eventToCommand()
                .updateViewState()

        return fragmentTaskWriterBinding.root
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
         * @return A new instance of fragment AccountWriterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TaskWriterFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
