package com.example.sample.ui.thungan.billhistrory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.sample.databinding.TnBhBillinfoDialogFragBinding
import com.example.sample.model.apiresponse.GetListBpDishResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.BillService
import com.example.sample.ui.thungan.billhistrory.adapter.BH_BpDishRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BH_BillInfoDialog(val billId: Int, val banId: String, val createdAt: String, val price: Int, val taoboi: String) : DialogFragment() {

    private var _binding: TnBhBillinfoDialogFragBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TnBhBillinfoDialogFragBinding.inflate(inflater, container, false)

        val adapter = BH_BpDishRecyclerViewAdapter()
        binding.tnBhBillinfoDialogRv.adapter = adapter

        binding.tvBhBiTenban.text = "Bàn: " + banId
        binding.tvBhBiTaoboi.text = "Tạo bởi: " + taoboi
        binding.tvBhBiGia.text = "Tổng thanh toán: " + price + "đ"
        binding.tvBhBiCreatedAt.text = "Ngày tạo: " + createdAt

        val service = RetrofitClient.retrofit.create(BillService::class.java)
        val getAllBillBpDishRequest = service.getAllBpDish(billId)
        getAllBillBpDishRequest.enqueue(object : Callback<GetListBpDishResponse> {
            override fun onResponse(
                call: Call<GetListBpDishResponse>,
                response: Response<GetListBpDishResponse>
            ) {
                if (response.body()?.status.toString() == "success") {
                    val bpDishList = response.body()?.bp_dishes
                    if (bpDishList != null) {
                        adapter.submitList(bpDishList)
                    }
                } else {
                    Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            override fun onFailure(call: Call<GetListBpDishResponse>, t: Throwable) {
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })


        val root = binding.root
        return root
    }
}