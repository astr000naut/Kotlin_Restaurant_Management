package com.example.sample.ui.quanly.dishmanage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.model.Area
import com.example.sample.model.Dish
import com.example.sample.ui.quanly.dishmanage.DM_DishInfoFragmentDirections
import com.example.sample.ui.quanly.dishmanage.DishListFragmentDirections
import com.example.sample.ui.quanly.tablemanage.TM_AreaListDirections


class DM_DishListRecyclerViewAdapter() : ListAdapter<Dish, DM_DishListRecyclerViewAdapter.DishItemViewHolder>(
    DM_DishDiffItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : DishItemViewHolder = DishItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: DishItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DishItemViewHolder(val rootView: CardView): RecyclerView.ViewHolder(rootView) {
        val tv_dm_tenmon = rootView.findViewById<TextView>(R.id.tv_dm_tenmon)
        val tv_dm_giamon = rootView.findViewById<TextView>(R.id.tv_dm_giamon)
        companion object {
            fun inflateFrom(parent: ViewGroup): DishItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.ql_dm_item_dish, parent, false) as CardView
                return DishItemViewHolder(view)
            }
        }
        fun bind(item: Dish) {
            tv_dm_tenmon.text = item.ten
            tv_dm_giamon.text = item.gia.toString()
            rootView.setOnClickListener{
                val action = DishListFragmentDirections.actionDishListFragmentToDMDishInfoFragment(item.id, item.ten, item.gia)
                rootView.findNavController().navigate(action)
            }
        }
    }

}