package com.study.thesuperiorstanislav.decisiontheorylabs.lab1


import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

import com.study.thesuperiorstanislav.decisiontheorylabs.R
import com.study.thesuperiorstanislav.decisiontheorylabs.UseCase
import com.study.thesuperiorstanislav.decisiontheorylabs.lab1.domain.model.Point
import kotlinx.android.synthetic.main.fragment_lab1.*
import org.jetbrains.anko.doAsync
import java.io.BufferedReader

import java.io.InputStreamReader
import java.text.DecimalFormat
import android.os.SystemClock
import android.util.Log
import android.view.Window
import com.jjoe64.graphview.series.PointsGraphSeries
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.FileReader
import com.study.thesuperiorstanislav.decisiontheorylabs.utils.GraphHelper
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.onComplete
import org.jetbrains.anko.uiThread


class Lab1Fragment : Fragment(), Lab1Contract.View {

    override var isActive: Boolean = false

    private var presenter: Lab1Contract.Presenter? = null

    private val readRequestCode = 42

    private var dialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        retainInstance = true
        return inflater.inflate(R.layout.fragment_lab1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity!!.fab.setOnClickListener {
            performFileSearch()
        }
        activity!!.fab.show()
        createLoadingDialog()
    }

    override fun onResume() {
        super.onResume()
        dialog?.show()
        presenter?.start()
    }

    override fun setPresenter(presenter: Lab1Contract.Presenter) {
        this.presenter = presenter
    }

    override fun drawGraph(pointListOriginal: List<Point>, pointListRestored: List<Point>, abc: DoubleArray) {
        dialog?.dismiss()
        no_data.visibility = View.GONE
        graph_view.visibility = View.VISIBLE

        val df = DecimalFormat("#.##")
        df.format(abc[0])
        graph_view.title = "${df.format(abc[0])} * x^2 + ${df.format(abc[1])} * x + ${df.format(abc[2])}"

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

            graph_view.viewport.isScalable = false
            graph_view.viewport.isScrollable = false
            graph_view.viewport.isXAxisBoundsManual = true
            graph_view.viewport.isYAxisBoundsManual = true

            val minMax = GraphHelper.findMinsMax(pointListOriginal,pointListRestored)

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
                            graph_view.addSeries(dataForGraphRestored)
                            graph_view.addSeries(dataForGraphOriginal)
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
        val snackBar = Snackbar.make(main_layout, error.message!!, Snackbar.LENGTH_SHORT)
        snackBar.setAction("¯\\(°_o)/¯") { _ ->  }
        snackBar.show()
    }

    override fun onLoadingError(error: UseCase.Error) {
        dialog?.dismiss()
        no_data.visibility = View.VISIBLE
        graph_view.visibility = View.GONE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  resultData: Intent?) {
        if (requestCode == readRequestCode && resultCode == Activity.RESULT_OK) {
            val uri: Uri?
            if (resultData != null) {
                uri = resultData.data!!
                val inputStream = activity?.contentResolver?.openInputStream(uri)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val data = FileReader.readTextFromUri(reader)
                if (data != null)
                    presenter?.savePoint(data)
                else
                    onError(UseCase.Error(UseCase.Error.UNKNOWN_ERROR,"Wrong file format"))
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
