package com.example.sample.ui.quanly.dishmanage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.R
import com.example.sample.databinding.QlDmDishlishFragBinding
import com.example.sample.model.apiresponse.GetAllAreaResponse
import com.example.sample.model.apiresponse.GetAllDishResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.AreaService
import com.example.sample.network.api.DishService
import com.example.sample.ui.quanly.dishmanage.adapter.DM_DishListRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DishListFragment : Fragment() {
    private var _binding: QlDmDishlishFragBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QlDmDishlishFragBinding.inflate(inflater, container, false)

        val adapter = DM_DishListRecyclerViewAdapter()
        binding.qlDmDishlistRecyclerview.adapter = adapter

        val service = RetrofitClient.retrofit.create(DishService::class.java)
        val getAllDishRequest = service.getAllDish()

        getAllDishRequest.enqueue(object : Callback<GetAllDishResponse> {
            override fun onResponse(
                call: Call<GetAllDishResponse>,
                response: Response<GetAllDishResponse>
            ) {
                if (response.body()?.status.toString() == "success") {
                    val dishList = response.body()?.dishes
                    if (dishList != null) {
                        adapter.submitList(dishList)
                    }
                } else {
                    Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<GetAllDishResponse>, t: Throwable) {
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnAddDish.setOnClickListener {
            val action = DishListFragmentDirections.actionDishListFragmentToDMAddDishFragment()
            binding.root.findNavController().navigate(action)
        }


        val root = binding.root
        return root
    }

}