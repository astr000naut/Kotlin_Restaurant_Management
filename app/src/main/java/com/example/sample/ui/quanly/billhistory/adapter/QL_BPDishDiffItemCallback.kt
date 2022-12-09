package com.example.sample.ui.quanly.billhistory.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.sample.model.BP_Dish
import com.example.sample.model.Bill
import com.example.sample.model.Dish

class QL_BPDishDiffItemCallback: DiffUtil.ItemCallback<BP_Dish>() {
    override fun areItemsTheSame(oldItem: BP_Dish, newItem: BP_Dish): Boolean
    = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: BP_Dish, newItem: BP_Dish): Boolean
    = (oldItem == newItem)
}