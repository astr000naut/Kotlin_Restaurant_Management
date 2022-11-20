package com.example.sample.ui.phucvu.home

import androidx.recyclerview.widget.DiffUtil
import com.example.sample.model.Dish

class DishDiffItemCallback: DiffUtil.ItemCallback<Dish>() {
    override fun areItemsTheSame(oldItem: Dish, newItem: Dish): Boolean
    = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Dish, newItem: Dish): Boolean
    = (oldItem == newItem)
}