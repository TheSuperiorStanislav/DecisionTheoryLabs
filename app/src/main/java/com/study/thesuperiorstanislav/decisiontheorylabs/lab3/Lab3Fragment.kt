package com.study.thesuperiorstanislav.decisiontheorylabs.lab3


import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window

import com.study.thesuperiorstanislav.decisiontheorylabs.R
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.lab2.CsGraphFragment
import com.study.thesuperiorstanislav.decisiontheorylabs.lab2.CsGraphPresenter
import com.study.thesuperiorstanislav.decisiontheorylabs.lab2.adapter.GraphAdapter
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.File.FileReader
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.fragment_lab3.*
import java.io.BufferedReader
import java.io.InputStreamReader

class Lab3Fragment : Fragment(),Lab3Contract.View {
    override var isActive: Boolean = false

    private var presenter :Lab3Contract.Presenter? = null

    private var dialog: Dialog? = null

    private val readRequestCode = 42

    override fun setPresenter(presenter: Lab3Contract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lab3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.fab.setOnClickListener {
            performFileSearch()
        }
        activity!!.fab.show()
        createLoadingDialog()
        dialog?.show()
    }

    override fun onResume() {
        super.onResume()
        presenter?.start()
    }

    override fun onDetach() {
        dialog?.dismiss()
        isActive = false
        activity!!.tabs.visibility = View.GONE
        activity!!.fab.setOnClickListener {  }
        super.onDetach()
    }

    override fun showData(pointListRestored: List<PointMD>, pointListCs: List<Point>) {
        dialog?.dismiss()

        no_data.visibility = View.GONE

        val adapter = GraphAdapter(childFragmentManager)

        val fragmentGraph = GraphListFragment()
        fragmentGraph.setPresenter(GraphListPresenter(fragmentGraph,pointListRestored))
        adapter.addFragment(fragmentGraph, "GraphList")

        val fragmentGraphCs = CsGraphFragment()
        fragmentGraphCs.setPresenter(CsGraphPresenter(fragmentGraphCs,pointListCs))
        adapter.addFragment(fragmentGraphCs, "Cs and W")

        viewpager.adapter = adapter

        activity!!.tabs.setupWithViewPager(viewpager)
        activity!!.tabs.visibility = View.VISIBLE
    }

    override fun onError(error: UseCase.Error) {
        dialog?.dismiss()
        val snackBar = Snackbar.make(main_layout, error.message!!, Snackbar.LENGTH_SHORT)
        snackBar.setAction("¯\\(°_o)/¯") { _ ->  }
        snackBar.show()
    }

    override fun onLoadingError(error: UseCase.Error) {
        dialog?.dismiss()
        no_data.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  resultData: Intent?) {
        if (requestCode == readRequestCode && resultCode == Activity.RESULT_OK) {
            val uri: Uri?
            if (resultData != null) {
                uri = resultData.data!!
                val inputStream = activity?.contentResolver?.openInputStream(uri)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val data = FileReader.readPointMDFromUri(reader)
                if (data != null)
                    presenter?.savePoint(data)
                else
                    onError(UseCase.Error(UseCase.Error.UNKNOWN_ERROR,"Wrong file format"))
                SystemClock.sleep(50)
            }
        }
    }

    private fun createLoadingDialog(){
        dialog = Dialog(context!!)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.dialog_loading)
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
    }

    private fun performFileSearch() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "text/plain"
        startActivityForResult(intent, readRequestCode)
    }


}
