package com.example.sample.ui.thungan.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.R
import com.example.sample.databinding.TnTlFragmentBillInfoBinding
import com.example.sample.model.BP_Dish
import com.example.sample.model.BillResponse
import com.example.sample.model.apiresponse.GetListBpDishResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.SocketHandler
import com.example.sample.network.api.BillService
import com.example.sample.ui.phucvu.home.CreateBillFragmentDirections
import com.example.sample.ui.thungan.home.adapter.TN_BillInfoRecyclerViewAdapter
import io.socket.client.Socket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class TN_BillInfoFragment : Fragment() {

    private var _binding: TnTlFragmentBillInfoBinding? = null
    private val binding get() = _binding!!
    lateinit var mSocket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        mSocket = SocketHandler.getSocket()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = TnTlFragmentBillInfoBinding.inflate(inflater, container, false)
        val billId = TN_BillInfoFragmentArgs.fromBundle(requireArguments()).billId
        val tableId = TN_BillInfoFragmentArgs.fromBundle(requireArguments()).tableId

        // Connect socket
        Timer().schedule(object: TimerTask() {
            override fun run() {
                mSocket.connect()
            }
        }, 1000)

        val adapter = TN_BillInfoRecyclerViewAdapter()
        binding.tnBillInfoRecyclerview.adapter = adapter

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
                        binding.tvTenban.text = "Bàn số: " + bill?.id
                        binding.tvNgaytao.text = "Ngày tạo: " + bill?.createdAt
                        binding.tvTaoboi.text = "Tạo bởi: " + bill?.taoboi
                        binding.tvGia.text = "Giá: " + (bill?.gia ?:0)
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
                if (billId.toString() == args[0].toString()) {
                    getBillAllBpDishRequest.clone().enqueue(object : Callback<GetListBpDishResponse> {
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
                }
            }
        }
        mSocket.on("dish_update_tn") { args ->
            if (args[0] != null) {
                if (args[0].toString() == tableId) {
                    getBillAllBpDishRequest.clone().enqueue(object : Callback<GetListBpDishResponse> {
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
                }
            }
        }

        binding.btnXacnhantt.setOnClickListener {
            mSocket.emit("bill_done_tn", tableId)

            Timer().schedule(object: TimerTask() {
                override fun run() {
                    activity?.runOnUiThread{
                        val action =
                            TN_BillInfoFragmentDirections.actionTnBillInfoFragToTnTableListFrag()
                        binding.root.findNavController().navigate(action)
                    }
                }
            }, 1000)
        }




        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSocket.disconnect()
        _binding = null
    }
}