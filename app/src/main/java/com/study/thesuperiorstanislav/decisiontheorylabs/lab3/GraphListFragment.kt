package com.study.thesuperiorstanislav.decisiontheorylabs.lab3


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.study.thesuperiorstanislav.decisiontheorylabs.R
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.adapter.GraphPointAdapter
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD
import kotlinx.android.synthetic.main.fragment_graph_list.*

class GraphListFragment : Fragment(),GraphListContact.View {
    override var isActive: Boolean = false

    private var presenter: GraphListContact.Presenter? = null

    override fun setPresenter(presenter: GraphListContact.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_graph_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.layoutManager = LinearLayoutManager(context)
        val adapterSubject = GraphPointAdapter(arrayListOf(), R.layout.layout_graph_point)
        recycler_view.adapter = adapterSubject


    }

    override fun onResume() {
        super.onResume()
        presenter?.start()
    }

    override fun setData(pointListRestored: List<PointMD>) {
        recycler_view.layoutManager = LinearLayoutManager(context)
        val adapterSubject = GraphPointAdapter(pointListRestored, R.layout.layout_graph_point)
        recycler_view.adapter = adapterSubject
    }

    override fun onError(error: UseCase.Error) {
        val snackBar = Snackbar.make(main_layout, error.message!!, Snackbar.LENGTH_SHORT)
        snackBar.setAction("¯\\(°_o)/¯") { _ ->  }
        snackBar.show()
    }



}
