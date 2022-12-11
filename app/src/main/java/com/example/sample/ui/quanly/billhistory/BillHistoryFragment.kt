package com.example.sample.ui.quanly.billhistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sample.databinding.QlFragmentBillHistoryBinding
import com.example.sample.model.BillFilterResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.BillService
import com.example.sample.ui.quanly.billhistory.adapter.QL_BillHistoryRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class BillHistoryFragment : Fragment() {

    private var _binding: QlFragmentBillHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QlFragmentBillHistoryBinding.inflate(inflater, container, false)

        val btn_chitietListener: (Int, String, String, Int, String) -> Unit = {billId, tableId, createdAt, price, taoboi ->
            val dialog = QL_BillInfoDialog(billId, tableId, createdAt, price, taoboi)
            activity?.let {
                dialog.show(it.supportFragmentManager, "billinfodialog")
            }
        }

        val adapter = QL_BillHistoryRecyclerViewAdapter(btn_chitietListener)
        binding.billInfoRecyclerview.adapter = adapter

        val btn_search = binding.btnSearch
        val dp_from = binding.dpFrom
        val dp_to = binding.dpTo
        val calendar = Calendar.getInstance()
        dp_from.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
            Calendar.DAY_OF_MONTH))
        dp_to.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(
            Calendar.DAY_OF_MONTH))

        val service = RetrofitClient.retrofit.create(BillService::class.java)

        btn_search.setOnClickListener {
//            calendar.set(dp_from.year, dp_from.month, dp_from.dayOfMonth)
            val filterBillRequest = service.filterBill(
                dp_from.dayOfMonth, dp_from.month.toInt() + 1, dp_from.year,
                dp_to.dayOfMonth, dp_to.month.toInt() + 1, dp_to.year)
            filterBillRequest.enqueue(object : Callback<BillFilterResponse> {
                override fun onResponse(
                    call: Call<BillFilterResponse>,
                    response: Response<BillFilterResponse>
                ) {
                    if (response.body()?.status.toString() == "success") {
                        val billList = response.body()?.bills
                        var tong_thu = 0
                        if (billList?.size!! > 0) {
                            billList?.forEach{bill ->
                                tong_thu += bill.gia!!
                            }
                            binding.tongThu.text = "Tổng thu: " + tong_thu.toString() + "đ"
                        } else {
                            binding.tongThu.text = "Không có hóa đơn"
                        }
                        if (billList != null) {
                            adapter.submitList(billList)
                        }
                    } else {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }


                override fun onFailure(call: Call<BillFilterResponse>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }

        val root = binding.root
        return root
    }

}