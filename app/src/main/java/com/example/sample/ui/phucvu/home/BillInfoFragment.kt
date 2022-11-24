package com.example.sample.ui.phucvu.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.R
import com.example.sample.databinding.PvFragmentBillInfoBinding
import com.example.sample.model.GetAllDishResponse
import com.example.sample.network.BillService
import com.example.sample.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BillInfoFragment : Fragment() {

    private var _binding: PvFragmentBillInfoBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = PvFragmentBillInfoBinding.inflate(inflater, container, false)
        val billId = BillInfoFragmentArgs.fromBundle(requireArguments()).billId
        val tableId = BillInfoFragmentArgs.fromBundle(requireArguments()).tableId
        binding.themmonBtn.setOnClickListener {
            val action = BillInfoFragmentDirections.actionBillInfoFragmentToThemmonFragment(billId, tableId)
            binding.root.findNavController().navigate(action)
        }

        val adapter = BillInfoRecyclerViewAdapter()
        _binding!!.billInfoRecyclerview.adapter = adapter

        // Call API to get bill info item
        var service = RetrofitClient.retrofit.create(BillService::class.java)
        val getBillAllDishRequest = service.getAllDish(billId)
        getBillAllDishRequest.enqueue(object : Callback<GetAllDishResponse> {
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





        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}