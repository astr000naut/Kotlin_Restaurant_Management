package com.example.sample.ui.quanly.tablemanage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.model.Area
import com.example.sample.ui.quanly.tablemanage.TM_AreaListDirections


class TM_AreaListRecyclerViewAdapter() : ListAdapter<Area, TM_AreaListRecyclerViewAdapter.AreaItemViewHolder>(
    TM_AreaDiffItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : AreaItemViewHolder = AreaItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: AreaItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class AreaItemViewHolder(val rootView: CardView): RecyclerView.ViewHolder(rootView) {
        val tv_tenkhuvuc = rootView.findViewById<TextView>(R.id.tv_tenkhuvuc)
        companion object {
            fun inflateFrom(parent: ViewGroup): AreaItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.ql_item_area, parent, false) as CardView
                return AreaItemViewHolder(view)
            }
        }
        fun bind(item: Area) {
            tv_tenkhuvuc.text = "Khu vực: " + item.khuvuc
            rootView.setOnClickListener{
                val action = TM_AreaListDirections.actionTMAreaListToTMAreaInfo(item.khuvuc, item.soban2, item.soban4, item.soban6, item.soban8)
                rootView.findNavController().navigate(action)
            }
        }
    }

}