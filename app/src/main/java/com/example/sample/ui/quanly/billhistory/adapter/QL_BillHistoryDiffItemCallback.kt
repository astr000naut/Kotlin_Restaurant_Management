package com.example.sample.ui.quanly.billhistory.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.sample.model.Bill

class QL_BillHistoryDiffItemCallback: DiffUtil.ItemCallback<Bill>() {
    override fun areItemsTheSame(oldItem: Bill, newItem: Bill): Boolean
    = (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Bill, newItem: Bill): Boolean
    = (oldItem == newItem)
}