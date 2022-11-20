package com.example.sample.ui.phucvu.home

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.model.Table


class TableRecyclerViewAdapter : ListAdapter<Table, TableRecyclerViewAdapter.TableItemViewHolder>(TableDiffItemCallback()) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : TableItemViewHolder = TableItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: TableItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class TableItemViewHolder(val rootView: CardView): RecyclerView.ViewHolder(rootView) {
        val table_name = rootView.findViewById<TextView>(R.id.table_name)
        val table_size = rootView.findViewById<TextView>(R.id.table_size)
        val table_status = rootView.findViewById<TextView>(R.id.table_status)
        companion object {
            fun inflateFrom(parent: ViewGroup): TableItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.pv_item_recyclerview, parent, false) as CardView
                return TableItemViewHolder(view)
            }
        }
        fun bind(item: Table) {
            table_name.text = "Bàn số: " + item.id.toString()
            table_size.text = "Số chỗ ngồi: " + item.socho.toString()
            if (item.hoadonht == -1) {
                table_status.text = "Trạng thái: Trống"
                rootView.setCardBackgroundColor(Color.GREEN)
            } else {
                table_status.text = "Trạng thái: Đang phục vụ"
                rootView.setCardBackgroundColor(Color.YELLOW)
            }
            rootView.setOnClickListener{
                if (item.hoadonht == -1) {
                    val action1 = TableListFragmentDirections.actionTableListFragmentToCreateBillFragment2(item.id)
                    rootView.findNavController().navigate(action1)
                } else {
                    val action2 = TableListFragmentDirections.actionTableListFragmentToBillInfoFragment(item.hoadonht!!)
                    rootView.findNavController().navigate(action2)
                }

            }

        }
    }

}