package com.example.sample.ui.thungan.billhistrory.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.sample.model.BP_Dish

class BH_BPDishDiffItemCallback: DiffUtil.ItemCallback<BP_Dish>() {
    override fun areItemsTheSame(oldItem: BP_Dish, newItem: BP_Dish): Boolean
    = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: BP_Dish, newItem: BP_Dish): Boolean
    = (oldItem == newItem)
}