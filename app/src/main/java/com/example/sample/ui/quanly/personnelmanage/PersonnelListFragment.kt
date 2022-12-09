package com.example.sample.ui.quanly.personnelmanage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.databinding.QlFragmentPersonnelListBinding
import com.example.sample.model.apiresponse.UserListResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.UserService
import com.example.sample.ui.quanly.personnelmanage.adapter.PM_PersonnelListRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PersonnelListFragment : Fragment() {
    private var _binding: QlFragmentPersonnelListBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QlFragmentPersonnelListBinding.inflate(inflater, container, false)

        val adapter = PM_PersonnelListRecyclerViewAdapter()
        binding.qlPmRecyclerview.adapter = adapter

        val service = RetrofitClient.retrofit.create(UserService::class.java)
        val getAllPersonnelRequest = service.getALlPersonnel()

        getAllPersonnelRequest.enqueue(object : Callback<UserListResponse> {
            override fun onResponse(
                call: Call<UserListResponse>,
                response: Response<UserListResponse>
            ) {
                if (response.body()?.status.toString() == "success") {
                    val userList = response.body()?.users
                    if (userList != null) {
                        adapter.submitList(userList)
//                        Log.d("AREALIST", areaList.toString())
                    }
                } else {
                    Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnAddPersonnel.setOnClickListener {
            val action = PersonnelListFragmentDirections.actionPersonnelListFragmentToPMAddPersonnel()
            binding.root.findNavController().navigate(action)
        }

        val root = binding.root
        return root
    }

}