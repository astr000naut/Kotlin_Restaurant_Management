package com.example.sample.ui.phucvu.home.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.sample.model.Table

class TableDiffItemCallback: DiffUtil.ItemCallback<Table>() {
    override fun areItemsTheSame(oldItem: Table, newItem: Table): Boolean
    = (oldItem.hoadonht == newItem.hoadonht && oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Table, newItem: Table): Boolean
    = (oldItem == newItem)
}