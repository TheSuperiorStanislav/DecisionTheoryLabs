package com.study.thesuperiorstanislav.decisiontheorylabs.lecture1

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

import com.study.thesuperiorstanislav.decisiontheorylabs.R
import kotlinx.android.synthetic.main.fragment_lecture1.*




class Lecture1Fragment : Fragment() {

    private var dialog: Dialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lecture1, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottom_app_bar.inflateMenu(R.menu.fragment_lecture1_menu)
        bottom_app_bar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.app_bar_edit -> {
                    dialog = Dialog(context!!)
                    dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
                    dialog?.setContentView(R.layout.dialog_lecture1)
                    dialog?.show()
                    true
                }
                R.id.app_bar_inc -> {
                    val snackBar = Snackbar.make(main_layout, "test", Snackbar.LENGTH_SHORT)
                    snackBar.setAction("¯\\(°_o)/¯") { _ ->  }
                    snackBar.show()
                    true
                }
                R.id.app_bar_dec -> {
                    val snackBar = Snackbar.make(main_layout, "test", Snackbar.LENGTH_SHORT)
                    snackBar.setAction("¯\\(°_o)/¯") { _ ->  }
                    snackBar.show()
                    true
                }
                else -> {
                    false
                }
            }
        }
        bottom_fab.setOnClickListener {
            val snackBar = Snackbar.make(main_layout, "test", Snackbar.LENGTH_SHORT)
            snackBar.setAction("¯\\(°_o)/¯") { _ ->  }
            snackBar.show()
        }
    }
}
