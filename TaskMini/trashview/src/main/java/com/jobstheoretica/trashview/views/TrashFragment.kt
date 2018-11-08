package com.jobstheoretica.trashview.views

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
import android.widget.ImageView
import com.example.navigation.factories.SharedNavigatorFactory
import com.example.navigation.interfaces.INavigator
import com.example.utility.impls.ActivityReflector
import com.jobstheoretica.entity.bindable.Trash

import com.jobstheoretica.trashview.R
import com.jobstheoretica.trashview.behaviors.ClickVertMenuViewOnTrashHeader
import com.jobstheoretica.trashview.databinding.FragmentTrashBinding
import com.jobstheoretica.trashview.viewmodels.TrashViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TrashFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TrashFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TrashFragment : Fragment() {
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

    internal var sharedNavigator: INavigator? = null
    internal var vertMenuViewOnHeader: ImageView? = null
        get() {
            if(field == null){
                field = ActivityReflector<ImageView>(this.activity!!).getDeclaredField("vertMenuViewOnTrashHeader")
            }
            return field
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if(this.sharedNavigator == null){
            this.sharedNavigator = SharedNavigatorFactory(this).create()
        }

        val view = inflater.inflate(R.layout.fragment_trash, container, false)

        val fragmentTrashBinding = FragmentTrashBinding.bind(view)
        val vm = ViewModelProviders.of(this).get(TrashViewModel::class.java)
        fragmentTrashBinding.myViewModel = vm

        val rv = view.findViewById<RecyclerView>(R.id.trashRecyclerView)
        val trash = mutableListOf<Trash>()

        val adpt = TrashAdapter(this, vm, trash)
        rv.setHasFixedSize(false)
        rv.layoutManager = LinearLayoutManager(this.context)
        rv.adapter = adpt

        ClickVertMenuViewOnTrashHeader(this.vertMenuViewOnHeader!!
                , this
                , fragmentTrashBinding)
                .behave()

        vm.trashLiveData.observe(this, Observer {
            if(it != null){
                trash.clear()
                trash.addAll(it.toTypedArray())
                adpt.notifyDataSetChanged()
            }
        })

        vm.readTrashCommand.execute(null)

        return fragmentTrashBinding.root
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
         * @return A new instance of fragment TrashFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TrashFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
