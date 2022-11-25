package com.example.sample.ui.bep.home.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.model.BP_Dish


class DishListRecyclerViewAdapter (
    val dagiaoListener: (BP_Dish) -> Unit,
    val batdaulamListener: (BP_Dish) -> Unit
    ): ListAdapter<BP_Dish, DishListRecyclerViewAdapter.BpDishItemViewHolder>(
    BpDishDiffItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : BpDishItemViewHolder = BpDishItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: BpDishItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, dagiaoListener, batdaulamListener)
    }

    class BpDishItemViewHolder(val rootView: CardView): RecyclerView.ViewHolder(rootView) {
        val tv_tenmon = rootView.findViewById<TextView>(R.id.tv_tenmon)
        val tv_ghichu = rootView.findViewById<TextView>(R.id.tv_ghichu)
        val tv_soluong = rootView.findViewById<TextView>(R.id.tv_soluong)
        val tv_tenban = rootView.findViewById<TextView>(R.id.tv_tenban)
        val btn_batdaulam = rootView.findViewById<Button>(R.id.btn_batdaulam)
        val btn_dagiao = rootView.findViewById<Button>(R.id.btn_dagiao)
        companion object {
            fun inflateFrom(parent: ViewGroup): BpDishItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.bp_item_dishitem, parent, false) as CardView
                return BpDishItemViewHolder(view)
            }
        }
        fun bind(item: BP_Dish,
                 dagiaoListener: (BP_Dish) -> Unit,
                 batdaulamListener: (BP_Dish) -> Unit
        ) {
            tv_tenmon.text = item.ten
            tv_ghichu.text = "Ghi chú: " + if (item.ghichu != null) item.ghichu else ""
            tv_soluong.text = "SL: " + item.soluong.toString()
            tv_tenban.text = "Bàn số: " + item.ban.toString()
            btn_dagiao.setOnClickListener {dagiaoListener(item)}
            Log.d("ITEM STATUS", "${item.ten} ${item.trangthai}")
            if (item.trangthai.toString() == "Đang làm") {
                btn_batdaulam.text = "Đang làm"
                btn_batdaulam.isEnabled = false
                btn_batdaulam.isClickable = false
                btn_batdaulam.setBackgroundColor(Color.YELLOW)
            } else {
                btn_batdaulam.text = "Bắt đầu làm"
                btn_batdaulam.isEnabled = true
                btn_batdaulam.isClickable = true
                btn_batdaulam.setBackgroundColor(Color.CYAN)
            }
            btn_batdaulam.setOnClickListener {
                batdaulamListener(item)
            }
        }
    }

}