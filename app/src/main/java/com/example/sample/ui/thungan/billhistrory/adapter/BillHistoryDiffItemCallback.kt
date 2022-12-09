package com.example.sample.ui.thungan.billhistrory.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.sample.model.Bill

class BillHistoryDiffItemCallback: DiffUtil.ItemCallback<Bill>() {
    override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean
    = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean
    = (oldItem == newItem)
}