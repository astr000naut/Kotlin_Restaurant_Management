package com.example.sample.ui.quanly.dishmanage.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.sample.model.Area
import com.example.sample.model.Bill
import com.example.sample.model.Dish

class DM_DishDiffItemCallback: DiffUtil.ItemCallback<Dish>() {
    override fun areItemsTheSame(oldItem: Dish, newItem: Dish): Boolean
    = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Dish, newItem: Dish): Boolean
    = (oldItem == newItem)
}