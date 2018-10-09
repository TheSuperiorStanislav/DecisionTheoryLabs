package com.study.thesuperiorstanislav.decisiontheorylabs.lab3.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.study.thesuperiorstanislav.decisiontheorylabs.R
import com.study.thesuperiorstanislav.decisiontheorylabs.lab3.domain.model.PointMD

class GraphPointAdapter (private val items: List<PointMD>, private val itemLayout: Int) :
        RecyclerView.Adapter<GraphPointAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater
                .from(parent.context)
                .inflate(itemLayout,
                        parent,
                        false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: PointMD = items[position]

        var text = "U: "

        item.u.forEachIndexed { index, d ->
            text += if (index == item.u.size - 1)
                d.toString() +";"
            else
                d.toString() +", "
        }

        holder.u_text.text = text

        text = "X: "

        text += item.x

        holder.x_text.text = text

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        var x_text: TextView = itemView.findViewById(R.id.x_text)
        var u_text: TextView = itemView.findViewById(R.id.u_text)
    }
}
