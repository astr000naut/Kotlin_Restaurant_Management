package com.example.sample.ui.thungan.home.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.model.Table
import com.example.sample.ui.thungan.home.TN_TableListFragmentDirections


class TN_TableRecyclerViewAdapter : ListAdapter<Table, TN_TableRecyclerViewAdapter.TableItemViewHolder>(
    TN_TableDiffItemCallback()
) {



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
                val view = layoutInflater.inflate(R.layout.tn_item_table, parent, false) as CardView
                return TableItemViewHolder(view)
            }
        }
        fun bind(item: Table) {
            table_name.text = "Bàn số: " + item.id.toString()
            table_size.text = "Số chỗ ngồi: " + item.socho.toString()
            if (item.hoadonht == -1) {
                table_status.text = "Trống"
                rootView.setCardBackgroundColor(Color.GREEN)
            } else {
                table_status.text = "Đang phục vụ"
                rootView.setCardBackgroundColor(Color.YELLOW)
            }
            rootView.setOnClickListener{
                if (item.hoadonht != -1) {
                    val action = TN_TableListFragmentDirections.actionTnTableListFragToTnBillInfoFrag(item.hoadonht!!, item.id)
                    rootView.findNavController().navigate(action)
                }

            }

        }
    }

}