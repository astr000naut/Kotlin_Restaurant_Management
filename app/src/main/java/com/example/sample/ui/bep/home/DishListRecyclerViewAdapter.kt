package com.example.sample.ui.bep.home

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
    val dagiaoListener: (bpDish: BP_Dish) -> Unit
        ): ListAdapter<BP_Dish, DishListRecyclerViewAdapter.BpDishItemViewHolder>(
    BpDishDiffItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : BpDishItemViewHolder = BpDishItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: BpDishItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, dagiaoListener)
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
        fun bind(item: BP_Dish, dagiaoListener: (bpDish: BP_Dish) -> Unit) {
            tv_tenmon.text = item.ten
            tv_ghichu.text = "Ghi chú: " + if (item.ghichu != null) item.ghichu else ""
            tv_soluong.text = "SL: " + item.soluong.toString()
            tv_tenban.text = "Bàn số: " + item.ban.toString()
            btn_dagiao.setOnClickListener {dagiaoListener(item)}
        }
    }

}