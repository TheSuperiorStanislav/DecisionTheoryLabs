package com.study.thesuperiorstanislav.decisiontheorylabs.lecture1

import android.app.Dialog
import android.os.Bundle
import android.os.SystemClock
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

import com.study.thesuperiorstanislav.decisiontheorylabs.R
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import kotlinx.android.synthetic.main.dialog_lecture1.*
import kotlinx.android.synthetic.main.fragment_lecture1.*
import org.jetbrains.anko.collections.forEachReversedWithIndex
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import org.jetbrains.anko.uiThread
import java.text.DecimalFormat
import kotlin.Exception


class Lecture1Fragment : Fragment(),Lecture1Contract.View {
    override var isActive: Boolean = false

    private var isRunning: Boolean = false
    private var isPaused: Boolean = false

    private var dialog: Dialog? = null

    private var presenter: Lecture1Contract.Presenter? = null

    override fun setPresenter(presenter: Lecture1Contract.Presenter) {
        this.presenter = presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lecture1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottom_app_bar.inflateMenu(R.menu.fragment_lecture1_menu)
        bottom_app_bar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.app_bar_edit -> {
                    dialog = Dialog(context!!)
                    dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog?.setContentView(R.layout.dialog_lecture1)

                    dialog?.toolbar?.setNavigationOnClickListener {
                        dialog?.dismiss()
                    }

                    dialog?.start_action?.setOnClickListener {
                        try {
                            val function =  dialog?.function?.text.toString()
                            val value = dialog?.value?.text?.toString()?.toDouble()
                            isRunning = true
                            presenter?.startTheThing(function,value!!)
                            graph_view.removeAllSeries()
                            dialog?.dismiss()
                        }catch (e:Exception){
                            onError(UseCase.Error(UseCase.Error.UNKNOWN_ERROR,e.localizedMessage))
                        }

                    }
                    dialog?.show()
                    true
                }
                R.id.app_bar_inc -> {
                    presenter?.changeAlpha(true)
                    true
                }
                R.id.app_bar_dec -> {
                    presenter?.changeAlpha(false)
                    true
                }
                else -> {
                    false
                }
            }
        }
        bottom_fab.setOnClickListener {
            if (!(!isPaused && !isRunning)) {
                isRunning = !isRunning
                isPaused = if (!isRunning) {
                    bottom_fab.setImageResource(R.drawable.ic_menu_pause)
                    presenter?.setRunStats(true, isRunning)
                    presenter?.stopTheThing()
                    true
                } else {
                    bottom_fab.setImageResource(R.drawable.ic_menu_play)
                    presenter?.setRunStats(false, isRunning)
                    presenter?.getData()
                    false
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter?.start()
    }

    @Suppress("UNCHECKED_CAST")
    override fun showData(function: String, pointList: List<Point>, value: Double, alpha: Double) {
        doAsync {
            val dataPointsU = mutableListOf<DataPoint>()
            val dataPointsValue = mutableListOf<DataPoint>()
            val newPointListValue = mutableListOf<Point>()
            val pointListValue = mutableListOf<Point>()

            val fUNew = pointList.last().y

            pointList.forEachReversedWithIndex { index, point ->
                if ((pointList.size - index) < 20) {
                    dataPointsU.add(0, DataPoint(index.toDouble(), point.y))
                    dataPointsValue.add(0, DataPoint(index.toDouble(), value))
                    pointListValue.add(0, Point(index.toDouble(), value))
                    newPointListValue.add(0, point)
                }
            }

            val lastIndex = pointList.size.toDouble()

            val dataPointU = DataPoint(lastIndex - 1.0, fUNew)
            val dataPointValue =  DataPoint(lastIndex - 1.0, value)

            val dataForGraphU = LineGraphSeries<DataPoint>(dataPointsU.toTypedArray())
            dataForGraphU.color = ContextCompat.getColor(context!!,
                    R.color.colorGraphOriginal)
            dataForGraphU.thickness = 13

            val dataForGraphValue = LineGraphSeries<DataPoint>(dataPointsValue.toTypedArray())
            dataForGraphValue.color = ContextCompat.getColor(context!!,
                    R.color.colorGraphRestored)
            dataForGraphValue.thickness = 10

            SystemClock.sleep(75)
            uiThread {
                val df = DecimalFormat("#.#####")
                graph_view.title = "uValue = ${df.format(fUNew)}, alpha = ${df.format(alpha)}"

                graph_view.viewport.isScrollable = true
                graph_view.viewport.isXAxisBoundsManual = true

                graph_view.viewport.setMinX(pointListValue.first().x)
                graph_view.viewport.setMaxX(pointListValue.last().x)

                if (graph_view.series.size == 0) {
                    graph_view.addSeries(dataForGraphU)
                    graph_view.addSeries(dataForGraphValue)
                }else{
                    (graph_view.series[0] as LineGraphSeries<DataPoint>)
                            .appendData(dataPointU,true,pointList.size)
                    (graph_view.series[1] as LineGraphSeries<DataPoint>)
                            .appendData(dataPointValue,true,pointList.size)
                }

            }
            onComplete {
                if (isRunning
                        && Math.abs(fUNew - value) > 0.0001
                        && fUNew != Double.NaN
                        && fUNew != Double.POSITIVE_INFINITY
                        && fUNew != Double.NEGATIVE_INFINITY)
                    presenter?.doTheThing(function, pointList.toMutableList(), alpha, value)
            }
        }
    }

    override fun onError(error: UseCase.Error) {
        val snackBar = Snackbar.make(main_layout, error.message!!, Snackbar.LENGTH_SHORT)
        snackBar.setAction("¯\\(°_o)/¯") { _ ->  }
        snackBar.show()
    }

    override fun onLoadingError(error: UseCase.Error) {
        val snackBar = Snackbar.make(main_layout, error.message!!, Snackbar.LENGTH_SHORT)
        snackBar.setAction("¯\\(°_o)/¯") { _ ->  }
        snackBar.show()
    }
}
