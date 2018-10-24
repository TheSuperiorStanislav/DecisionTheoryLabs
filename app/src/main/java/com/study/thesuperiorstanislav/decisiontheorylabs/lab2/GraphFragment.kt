package com.study.thesuperiorstanislav.decisiontheorylabs.lab2

import android.os.Bundle
import android.os.SystemClock
import com.google.android.material.snackbar.Snackbar
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import com.jjoe64.graphview.series.PointsGraphSeries

import com.study.thesuperiorstanislav.decisiontheorylabs.R
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.Graph.GraphHelper
import kotlinx.android.synthetic.main.fragment_graph.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import org.jetbrains.anko.uiThread


class GraphFragment: Fragment(),GraphContract.View {

    override var isActive: Boolean = false

    private var presenter: GraphContract.Presenter? = null

    override fun setPresenter(presenter: GraphContract.Presenter) {
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

    @Suppress("UNCHECKED_CAST")
    override fun drawGraph(pointListOriginal: List<Point>, pointListRestored: List<Point>) {
        graph_view.title = ""

        doAsync {
            val dataForGraphOriginal = PointsGraphSeries<DataPoint>(arrayOf<DataPoint>())
            dataForGraphOriginal.color = ContextCompat.getColor(context!!,
                    R.color.colorGraphOriginal)
            dataForGraphOriginal.size = 12f
            dataForGraphOriginal.shape = PointsGraphSeries.Shape.POINT

            val dataForGraphRestored = LineGraphSeries<DataPoint>(arrayOf<DataPoint>())
            dataForGraphRestored.color = ContextCompat.getColor(context!!,
                    R.color.colorGraphRestored)
            dataForGraphRestored.thickness = 10

            val minMax = GraphHelper.findMinMax(pointListOriginal, pointListRestored)

            for (i in 0 until pointListOriginal.size) {
                SystemClock.sleep(50)
                val dataPointOriginal = DataPoint(pointListOriginal[i].x, pointListOriginal[i].y)
                val dataPointRestored = DataPoint(pointListRestored[i].x, pointListRestored[i].y)

                uiThread {
                    if (it.isResumed) {
                        graph_view.viewport.isScalable = false
                        graph_view.viewport.isScrollable = false
                        graph_view.viewport.isXAxisBoundsManual = true
                        graph_view.viewport.isYAxisBoundsManual = true

                        graph_view.viewport.setMinX(minMax[0])
                        graph_view.viewport.setMaxX(minMax[1])

                        graph_view.viewport.setMinY(minMax[2])
                        graph_view.viewport.setMaxY(minMax[3])

                        graph_view.removeAllSeries()
                        graph_view.addSeries(dataForGraphRestored)
                        graph_view.addSeries(dataForGraphOriginal)


                        try {
                            (graph_view.series[0] as LineGraphSeries<DataPoint>)
                                    .appendData(dataPointRestored, false, pointListOriginal.size)
                            (graph_view.series[1] as PointsGraphSeries<DataPoint>)
                                    .appendData(dataPointOriginal, false, pointListOriginal.size)
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
