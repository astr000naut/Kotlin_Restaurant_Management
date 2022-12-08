package com.example.sample.ui.thungan.billhistrory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.model.BP_Dish


class BH_BpDishRecyclerViewAdapter : ListAdapter<BP_Dish, BH_BpDishRecyclerViewAdapter.BillItemViewHolder>(
    BH_BPDishDiffItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : BillItemViewHolder = BillItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: BillItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class BillItemViewHolder(val rootView: CardView): RecyclerView.ViewHolder(rootView) {
        val tv_tenmon = rootView.findViewById<TextView>(R.id.tv_tenmon)
        val tv_gia = rootView.findViewById<TextView>(R.id.tv_gia)
        val tv_ghichu = rootView.findViewById<TextView>(R.id.tv_ghichu)
        val tv_soluong = rootView.findViewById<TextView>(R.id.tv_soluong)
        companion object {
            fun inflateFrom(parent: ViewGroup): BillItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.tn_item_danhsachmon, parent, false) as CardView
                return BillItemViewHolder(view)
            }
        }
        fun bind(item: BP_Dish) {
            tv_tenmon.text = item.ten
            tv_gia.text = "Giá: " + item.gia.toString()
            tv_soluong.text = "SL: " + item.soluong.toString()
            tv_ghichu.text = "Ghi chú: " + (item.ghichu ?: "")

        }
    }

}