package com.study.thesuperiorstanislav.decisiontheorylabs.lab2

import android.os.Bundle
import android.os.SystemClock
import androidx.core.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

import com.study.thesuperiorstanislav.decisiontheorylabs.R
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.Graph.GraphHelper
import kotlinx.android.synthetic.main.fragment_graph.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import org.jetbrains.anko.uiThread


class CsGraphFragment : Fragment(),CsGraphContract.View {

    override var isActive: Boolean = false

    private var presenter: CsGraphContract.Presenter? = null

    override fun setPresenter(presenter: CsGraphContract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_graph, container, false)
    }

    override fun onResume() {
        super.onResume()
        presenter?.start()
    }


    override fun drawGraph(pointListCs: List<Point>) {
        graph_view.title = ""

        doAsync {
            val dataForGraphCs = LineGraphSeries<DataPoint>(arrayOf<DataPoint>())
            dataForGraphCs.color = ContextCompat.getColor(context!!,
                    R.color.colorGraphRestored)
            dataForGraphCs.thickness = 10

            graph_view.viewport.isScalable = false
            graph_view.viewport.isScrollable = false
            graph_view.viewport.isXAxisBoundsManual = true
            graph_view.viewport.isYAxisBoundsManual = true

            val minMax = GraphHelper.findMinMax(pointListCs)

            graph_view.viewport.setMinX(minMax[0])
            graph_view.viewport.setMaxX(minMax[1])

            graph_view.viewport.setMinY(minMax[2])
            graph_view.viewport.setMaxY(minMax[3])

            for (i in 0 until pointListCs.size) {
                SystemClock.sleep(50)
                val dataPointCs = DataPoint(pointListCs[i].x, pointListCs[i].y)

                uiThread {
                    if (it.isResumed) {
                        try {
                            dataForGraphCs.appendData(dataPointCs,
                                    false, pointListCs.size)
                            graph_view.removeAllSeries()
                            graph_view.addSeries(dataForGraphCs)
                        } catch (e: Exception) {
                            Log.e("GraphView", e.localizedMessage)
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
        val snackBar = Snackbar.make(main_layout, error.message!!, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT)
        snackBar.setAction("¯\\(°_o)/¯") { _ ->  }
        snackBar.show()
    }

}
