package com.example.sample.ui.quanly.personnelmanage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sample.R
import com.example.sample.model.User
import com.example.sample.ui.quanly.personnelmanage.PersonnelListFragmentDirections


class PM_PersonnelListRecyclerViewAdapter() : ListAdapter<User, PM_PersonnelListRecyclerViewAdapter.UserItemViewHolder>(
    PM_PersonnelDiffItemCallback()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : UserItemViewHolder = UserItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: UserItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class UserItemViewHolder(val rootView: CardView): RecyclerView.ViewHolder(rootView) {
        val tv_role = rootView.findViewById<TextView>(R.id.tv_role)
        val tv_name = rootView.findViewById<TextView>(R.id.tv_name)
        companion object {
            fun inflateFrom(parent: ViewGroup): UserItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.ql_pm_tem_personnel, parent, false) as CardView
                return UserItemViewHolder(view)
            }
        }
        fun bind(item: User) {

            when (item.role) {
                "bep" -> tv_role.text = "Nhân viên bếp"
                "phucvu" -> tv_role.text = "Nhân viên phục vụ"
                "thungan" -> tv_role.text = "Nhân viên thu ngân"
            }
            tv_name.text = item.ten
            rootView.setOnClickListener{
                val action = PersonnelListFragmentDirections.actionPersonnelListFragmentToPMPersonnelInfo(item.id,item.ten!!, item.tuoi!!, item.sdt!!, item.email!!, item.noio!!)
                rootView.findNavController().navigate(action)
            }
        }
    }

}