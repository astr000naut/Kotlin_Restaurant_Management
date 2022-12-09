package com.example.sample.ui.quanly.personnelmanage.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.sample.model.Area
import com.example.sample.model.Bill
import com.example.sample.model.Dish
import com.example.sample.model.User

class PM_PersonnelDiffItemCallback: DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean
    = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean
    = (oldItem == newItem)
}