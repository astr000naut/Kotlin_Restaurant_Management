package com.example.sample.ui.phucvu.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.model.BP_Dish


class BillInfoRecyclerViewAdapter : ListAdapter<BP_Dish, BillInfoRecyclerViewAdapter.BillItemViewHolder>(
    BpDishDiffItemCallback()
) {
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
        val tv_trangthai = rootView.findViewById<TextView>(R.id.tv_trangthai)
        val layout = rootView.findViewById<ConstraintLayout>(R.id.card_constrain)
        companion object {
            fun inflateFrom(parent: ViewGroup): BillItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.pv_item_danhsachmon, parent, false) as CardView
                return BillItemViewHolder(view)
            }
        }
        fun bind(item: BP_Dish) {
            tv_tenmon.text = item.ten
            tv_ghichu.text = "Ghi chú: " + if (item.ghichu != null) item.ghichu else ""
            tv_soluong.text = "SL: " + item.soluong.toString()
            tv_gia.text = "Giá: " + item.gia.toString()
            tv_trangthai.text = item.trangthai
            when (item.trangthai.toString()) {
                "Đang làm" -> layout.setBackgroundColor(Color.TRANSPARENT)
                "Đã xong" -> layout.setBackgroundColor(Color.GREEN)
                "Chưa làm" -> layout.setBackgroundColor(Color.YELLOW)
            }
        }
    }

}