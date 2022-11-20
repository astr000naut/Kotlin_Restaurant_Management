package com.example.sample.ui.phucvu.home

import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.model.Dish
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class ThemmonRecyclerViewAdapter : ListAdapter<Dish, ThemmonRecyclerViewAdapter.DishItemViewHolder>(DishDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : DishItemViewHolder = DishItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: DishItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class DishItemViewHolder(val rootView: CardView): RecyclerView.ViewHolder(rootView) {
        val tv_dish_name = rootView.findViewById<TextView>(R.id.tv_dish_name)
        val ghichu = rootView.findViewById<EditText>(R.id.ghi_chu)
        val btn_cong = rootView.findViewById<Button>(R.id.btn_cong)
        val btn_tru = rootView.findViewById<Button>(R.id.btn_tru)
        val tv_soluong = rootView.findViewById<TextView>(R.id.tv_soluong)
        companion object {
            fun inflateFrom(parent: ViewGroup): DishItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.pv_item_themmon, parent, false) as CardView
                return DishItemViewHolder(view)
            }
        }
        fun bind(item: Dish) {
            tv_dish_name.text = item.ten
            tv_soluong.text = item.soluong.toString()
            btn_cong.setOnClickListener {
                ++ item.soluong
                tv_soluong.text = item.soluong.toString()
            }
            btn_tru.setOnClickListener {
                if (item.soluong > 0) {
                    -- item.soluong
                    tv_soluong.text = item.soluong.toString()
                }
            }
            ghichu.addTextChangedListener(
                object :TextWatcher {
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }

                    override fun afterTextChanged(p0: Editable?) {
                        item.ghichu = p0.toString()
                    }

                }
            )
        }
    }

}