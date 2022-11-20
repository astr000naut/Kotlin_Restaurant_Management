package com.example.sample.ui.phucvu.home

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.model.Dish



class BillInfoRecyclerViewAdapter : ListAdapter<Dish, BillInfoRecyclerViewAdapter.BillItemViewHolder>(DishDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : BillItemViewHolder = BillItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: BillItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class BillItemViewHolder(val rootView: CardView): RecyclerView.ViewHolder(rootView) {
        val tv_tenmon = rootView.findViewById<TextView>(R.id.tv_tenmon)
        val tv_ghichu = rootView.findViewById<TextView>(R.id.tv_ghichu)
        val tv_soluong = rootView.findViewById<TextView>(R.id.tv_soluong)
        val tv_gia = rootView.findViewById<TextView>(R.id.tv_gia)
        val btn_capnhat = rootView.findViewById<Button>(R.id.btn_capnhatmon)
        companion object {
            fun inflateFrom(parent: ViewGroup): BillItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.pv_item_danhsachmon, parent, false) as CardView
                return BillItemViewHolder(view)
            }
        }
        fun bind(item: Dish) {
            tv_tenmon.text = item.ten
            tv_ghichu.text = item.ghichu
            tv_soluong.text = item.soluong.toString()
            tv_gia.text = item.gia.toString()
        }
    }

}