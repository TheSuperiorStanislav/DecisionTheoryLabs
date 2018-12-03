package com.study.thesuperiorstanislav.decisiontheorylabs.lab5


import android.app.Dialog
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries

import com.study.thesuperiorstanislav.decisiontheorylabs.R
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.Graph.GraphHelper
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.dialog_lab5.*
import kotlinx.android.synthetic.main.fragment_lab5.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import org.jetbrains.anko.uiThread


class Lab5Fragment : Fragment(),Lab5Contract.View {
    override var isActive: Boolean = false

    private var presenter: Lab5Contract.Presenter? = null

    private var dialog: Dialog? = null
    private var dialogProgress: Dialog? = null

    override fun setPresenter(presenter: Lab5Contract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lab5, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.fab.setOnClickListener {
            showInitDialog()
        }
        activity!!.fab.show()
    }

    override fun onResume() {
        super.onResume()
        dialogProgress?.show()
        presenter?.start()
    }

    override fun onDetach() {
        dialog?.dismiss()
        dialogProgress?.dismiss()
        isActive = false
        activity!!.fab.setOnClickListener {  }
        activity!!.fab.hide()
        super.onDetach()
    }

    override fun drawGraph(pointListOriginal: List<Point>, pointListRestored: List<Point>) {
        dialogProgress?.dismiss()
        no_data.visibility = View.GONE
        graph_view.visibility = View.VISIBLE

        doAsync {
            val dataForGraphOriginal = LineGraphSeries<DataPoint>(arrayOf<DataPoint>())
            dataForGraphOriginal.color = ContextCompat.getColor(context!!,
                    R.color.colorGraphOriginal)
            dataForGraphOriginal.thickness = 10

            val dataForGraphRestored = PointsGraphSeries<DataPoint>(arrayOf<DataPoint>())
            dataForGraphRestored.color = ContextCompat.getColor(context!!,
                    R.color.colorGraphRestored)
            dataForGraphRestored.size = 12f


            graph_view.viewport.isScalable = false
            graph_view.viewport.isScrollable = false
            graph_view.viewport.isXAxisBoundsManual = true
            graph_view.viewport.isYAxisBoundsManual = true

            val minMax = GraphHelper.findMinMax(pointListOriginal,pointListRestored)

            graph_view.viewport.setMinX(minMax[0])
            graph_view.viewport.setMaxX(minMax[1])

            graph_view.viewport.setMinY(minMax[2])
            graph_view.viewport.setMaxY(minMax[3])

            for (i in 0 until pointListOriginal.size) {

                SystemClock.sleep(50)
                val dataPointOriginal = DataPoint(pointListOriginal[i].x, pointListOriginal[i].y)
                val dataPointRestored = DataPoint(pointListRestored[i].x, pointListRestored[i].y)
                uiThread {
                    if (it.isResumed) {
                        try {
                            dataForGraphOriginal.appendData(dataPointOriginal, false, pointListOriginal.size)
                            dataForGraphRestored.appendData(dataPointRestored, false, pointListRestored.size)
                            graph_view.removeAllSeries()
                            graph_view.addSeries(dataForGraphOriginal)
                            graph_view.addSeries(dataForGraphRestored)
                        }catch (e: Exception){
                            Log.e("GraphView",e.localizedMessage)
                        }
                    }
                }
                onComplete {
                    if (it != null)
                        if (it.isResumed) {
                            graph_view.viewport.isScalable = true
                            graph_view.viewport.isScrollable = true
                        }
                }
            }
        }
    }

    override fun onError(error: UseCase.Error) {
        dialog?.dismiss()
        dialogProgress?.dismiss()
        val snackBar = Snackbar.make(main_layout, error.message!!, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
        snackBar.setAction("¯\\(°_o)/¯") { }
        snackBar.show()
    }

    override fun onLoadingError(error: UseCase.Error) {
        dialog?.dismiss()
        dialogProgress?.dismiss()
        no_data.visibility = View.VISIBLE
        graph_view.visibility = View.GONE
    }

    private fun showInitDialog() {
        dialog = Dialog(context!!)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.dialog_lab5)

        dialog?.toolbarLab5?.setNavigationOnClickListener {
            dialog?.dismiss()
        }

        dialog?.start_action?.setOnClickListener {
            try {
                val function = dialog?.function?.text.toString()
                val alpha = dialog?.alpha?.text?.toString()?.toDouble()!!
                val n = dialog?.n?.text?.toString()?.toInt()!!
                val delta = dialog?.delta?.text?.toString()?.toDouble()!!
                presenter?.doTheThing(n, delta, function, alpha)
                dialog?.dismiss()
                createLoadingDialog()
                dialogProgress?.show()
            } catch (e: Exception) {
                onError(UseCase.Error(UseCase.Error.UNKNOWN_ERROR, e.localizedMessage))
            }
        }

        dialog?.show()
    }

    private fun createLoadingDialog(){
        dialogProgress = Dialog(context!!)
        dialogProgress?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogProgress?.setContentView(R.layout.dialog_loading)
        dialogProgress?.setCancelable(false)
        dialogProgress?.setCanceledOnTouchOutside(false)
    }
}
