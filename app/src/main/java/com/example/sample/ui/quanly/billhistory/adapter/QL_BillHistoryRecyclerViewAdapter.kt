package com.example.sample.ui.quanly.billhistory.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.model.Bill


class QL_BillHistoryRecyclerViewAdapter(
    val btn_chitietListener: (Int, String, String, Int, String) -> Unit
) : ListAdapter<Bill, QL_BillHistoryRecyclerViewAdapter.BillItemViewHolder>(
    QL_BillHistoryDiffItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : BillItemViewHolder = BillItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: BillItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, btn_chitietListener)
    }

    class BillItemViewHolder(val rootView: CardView): RecyclerView.ViewHolder(rootView) {
        val tv_tenban = rootView.findViewById<TextView>(R.id.tv_bh_tenban)
        val tv_gia = rootView.findViewById<TextView>(R.id.tv_bh_gia)
        val tv_ngaytao = rootView.findViewById<TextView>(R.id.tv_bh_tg_tao)
        val btn_chitiet = rootView.findViewById<Button>(R.id.btn_bh_chitiet)
        companion object {
            fun inflateFrom(parent: ViewGroup): BillItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.ql_bh_bill_item, parent, false) as CardView
                return BillItemViewHolder(view)
            }
        }
        fun bind(item: Bill,
                 btn_chitietListener: (Int, String, String, Int, String) -> Unit) {
            tv_tenban.text = "Bàn số: " + item.ban
            tv_ngaytao.text = "Ngày tạo: " + item.createdAt
            tv_gia.text = "Giá: " + item.gia
            btn_chitiet.setOnClickListener {
                btn_chitietListener(item.id, item.ban, item.createdAt, item.gia!!, item.taoboi)
            }
        }
    }

}