package com.example.sample.ui.phucvu.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.databinding.PvFragmentBillInfoBinding
import com.example.sample.model.BillResponse
import com.example.sample.model.apiresponse.GetAllTableResponse

import com.example.sample.model.apiresponse.GetListBpDishResponse
import com.example.sample.network.api.BillService
import com.example.sample.network.RetrofitClient
import com.example.sample.network.SocketHandler
import com.example.sample.ui.phucvu.home.adapter.BillInfoRecyclerViewAdapter
import io.socket.client.Socket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class BillInfoFragment : Fragment() {

    private var _binding: PvFragmentBillInfoBinding? = null
    private val binding get() = _binding!!
    lateinit var mSocket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        mSocket = SocketHandler.getSocket()
        Log.d("BILL INFO", "ONCREATE $mSocket")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = PvFragmentBillInfoBinding.inflate(inflater, container, false)
        val billId = BillInfoFragmentArgs.fromBundle(requireArguments()).billId
        val tableId = BillInfoFragmentArgs.fromBundle(requireArguments()).tableId

        // Connect socket
        Timer().schedule(object: TimerTask() {
            override fun run() {
                mSocket.connect()
            }
        }, 1000)
        binding.themmonBtn.setOnClickListener {
            val action = BillInfoFragmentDirections.actionBillInfoFragmentToThemmonFragment(billId, tableId)
            binding.root.findNavController().navigate(action)
        }


        val adapter = BillInfoRecyclerViewAdapter()
        binding.billInfoRecyclerview.adapter = adapter

        // Call API to get bill info item
        var service = RetrofitClient.retrofit.create(BillService::class.java)
        val getBillRequest = service.getBill(billId)
        getBillRequest.enqueue(
            object : Callback<BillResponse> {
                override fun onResponse(
                    call: Call<BillResponse>,
                    response: Response<BillResponse>
                ) {
                    if (response.body()?.status.toString() == "success") {
                        val bill = response.body()?.bill
                        binding.tvTenban.text = "Hóa đơn bàn số " + tableId
                        binding.tvNgaytao.text = "Ngày tạo: " + bill?.createdAt
                        binding.tvTaoboi.text = "Tạo bởi: " + bill?.taoboi
                        binding.tvGia.text = "Tổng thanh toán: " + (bill?.gia ?:0) + "đ"
                    } else {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BillResponse>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            }
        )
        val getBillAllBpDishRequest = service.getAllBpDish(billId)
        getBillAllBpDishRequest.enqueue(object : Callback<GetListBpDishResponse> {
            override fun onResponse(
                call: Call<GetListBpDishResponse>,
                response: Response<GetListBpDishResponse>
            ) {
                if (response.body()?.status.toString() == "success") {
                    val bpdishList = response.body()?.bp_dishes
                    if (bpdishList != null) {
                        Log.d("RECEIVE BPDISH", bpdishList.toString())
                        adapter.submitList(bpdishList)
                    }
                } else {
                    Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetListBpDishResponse>, t: Throwable) {
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

        })

        mSocket.on("st_c_pv") { args ->
            if (args[0] != null) {
                Log.d("MESSII", args[0].toString())
                if (billId.toString() == args[0].toString()) {
                    Log.d("RESET BILL INFO", "RESET")
                    getBillAllBpDishRequest.clone().enqueue(object : Callback<GetListBpDishResponse> {
                        override fun onResponse(
                            call: Call<GetListBpDishResponse>,
                            response: Response<GetListBpDishResponse>
                        ) {
                            if (response.body()?.status.toString() == "success") {
                                val bpdishList = response.body()?.bp_dishes
                                if (bpdishList != null) {
                                    adapter.submitList(bpdishList)
                                    Log.d("RESET DISH LIST", bpdishList.toString())
                                }
                            } else {
                                Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<GetListBpDishResponse>, t: Throwable) {
                            Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                        }

                    })
                }
            }
        }
        mSocket.on("bill_done_pv") { args ->
            if (args[0] != null) {
                val args_tableId = args[0]
                if (tableId == args_tableId) {
                    Timer().schedule(object: TimerTask() {
                        override fun run() {
                            activity?.runOnUiThread{
                                val action = BillInfoFragmentDirections.actionBillInfoFragmentToTableListFragment()
                                binding.root.findNavController().navigate(action)
                            }
                        }
                    }, 500)
                }
            }
        }

        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSocket.disconnect()
        mSocket.off("st_c_pv")
        mSocket.off("bill_done_pv")
        _binding = null
    }
}