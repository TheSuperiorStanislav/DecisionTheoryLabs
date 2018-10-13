package com.study.thesuperiorstanislav.decisiontheorylabs.lecture1

import android.app.Dialog
import android.os.Bundle
import android.os.SystemClock
import android.text.SpannableStringBuilder
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

    private var isRunning: Boolean? = null

    private var dialog: Dialog? = null

    private var function :String? = null
    private var alpha :Double? = null
    private var value :Double? = null

    private var presenter: Lecture1Contract.Presenter? = null

    override fun setPresenter(presenter: Lecture1Contract.Presenter) {
        this.presenter = presenter
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null) {
            isRunning = savedInstanceState.get("isRunning") as Boolean?

            function = savedInstanceState.getString("function")
            alpha = savedInstanceState.getDouble("alpha",0.0)
            value = savedInstanceState.getDouble("value",0.0)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lecture1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataForGraphU = LineGraphSeries<DataPoint>()
        dataForGraphU.color = ContextCompat.getColor(context!!,
                R.color.colorGraphOriginal)
        dataForGraphU.thickness = 13

        val dataForGraphValue = LineGraphSeries<DataPoint>()
        dataForGraphValue.color = ContextCompat.getColor(context!!,
                R.color.colorGraphRestored)
        dataForGraphValue.thickness = 10

        graph_view.addSeries(dataForGraphU)
        graph_view.addSeries(dataForGraphValue)

        bottom_app_bar.inflateMenu(R.menu.fragment_lecture1_menu)
        bottom_app_bar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.app_bar_edit -> {
                    setUpDialog()
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
            if (isRunning != null) {
                isRunning = !isRunning!!
                if (!isRunning!!) {
                    bottom_fab.setImageResource(R.drawable.ic_menu_pause)
                    presenter?.setRunStats(isRunning!!)
                    presenter?.stopTheThing()
                } else {
                    bottom_fab.setImageResource(R.drawable.ic_menu_play)
                    presenter?.setRunStats(isRunning!!)
                    presenter?.getData()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (isRunning == null)
            bottom_fab.setImageResource(R.drawable.ic_menu_play)
        else if (!isRunning!!)
            bottom_fab.setImageResource(R.drawable.ic_menu_pause)
    }

    override fun onResume() {
        super.onResume()
        if (isRunning != null)
            presenter?.setRunStats(isRunning!!)
        presenter?.start()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isRunning != null)
            outState.putBoolean("isRunning", isRunning!!)
        outState.putString("function", function)
        if (alpha != null)
            outState.putDouble("alpha", alpha!!)
        if (value != null)
            outState.putDouble("value", value!!)
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
                } else {
                    return@forEachReversedWithIndex
                }
            }

            SystemClock.sleep(100)
            uiThread {
                if (it.isResumed) {
                    val df = DecimalFormat("#.#####")
                    graph_view.title = "u= ${df.format(pointList.last().x)} " +
                            "uValue = ${df.format(fUNew)}, " +
                            "alpha = ${df.format(alpha)}"

                    graph_view.viewport.isScrollable = true
                    graph_view.viewport.isXAxisBoundsManual = true

                    graph_view.viewport.setMinX(pointListValue.first().x)
                    graph_view.viewport.setMaxX(pointListValue.last().x)

                    (graph_view.series[0] as LineGraphSeries<DataPoint>).resetData(
                            dataPointsU.toTypedArray())

                    (graph_view.series[1] as LineGraphSeries<DataPoint>).resetData(
                            dataPointsValue.toTypedArray())
                }

            }
            onComplete {
                if (isRunning != null)
                    if (isRunning!!
                            && Math.abs(fUNew - value) > 0.00001
                            && fUNew != Double.NaN
                            && fUNew != Double.POSITIVE_INFINITY
                            && fUNew != Double.NEGATIVE_INFINITY) {
                        presenter?.doTheThing(function, pointList.toMutableList(), alpha, value)
                    } else {
                        presenter?.setRunStats(isRunning!!)
                        if (isRunning!!) {
                            isRunning = null
                        }
                    }
            }
        }
    }

    override fun onError(error: UseCase.Error) {
        val snackBar = Snackbar.make(main_layout, error.message!!, Snackbar.LENGTH_SHORT)
        snackBar.setAction("¯\\(°_o)/¯") { _ -> }
        snackBar.show()
    }

    override fun onLoadingError(error: UseCase.Error) {
        val snackBar = Snackbar.make(main_layout, error.message!!, Snackbar.LENGTH_SHORT)
        snackBar.setAction("¯\\(°_o)/¯") { _ -> }
        snackBar.show()
    }

    private fun setUpDialog(){
        dialog = Dialog(context!!)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setContentView(R.layout.dialog_lecture1)

        dialog?.toolbar?.setNavigationOnClickListener {
            dialog?.dismiss()
        }

        dialog?.setOnDismissListener {
            if (isRunning != null) {
                if (isRunning == false) {
                    isRunning = true
                    presenter?.setRunStats(true)
                    presenter?.getData()
                }
            }
        }

        dialog?.start_action?.setOnClickListener {
            try {
                function = dialog?.function?.text.toString()
                alpha = dialog?.alpha?.text?.toString()?.toDouble()
                value = dialog?.value?.text?.toString()?.toDouble()
                presenter?.startTheThing(function!!, alpha!!, value!!)
                dialog?.dismiss()
                isRunning = true
            } catch (e: Exception) {
                onError(UseCase.Error(UseCase.Error.UNKNOWN_ERROR, e.localizedMessage))
            }
        }

        isRunning = false
        presenter?.stopTheThing()
        dialog?.show()


        if (function != null)
            dialog?.function?.text = SpannableStringBuilder(function)
        if (alpha != null)
            dialog?.alpha?.text = SpannableStringBuilder(alpha.toString())
        if (value != null)
            dialog?.value?.text = SpannableStringBuilder(value.toString())
    }
}
