package com.example.sample.ui.phucvu.home

import android.graphics.Paint.Cap
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.databinding.PvFragmentCapnhatmonBinding
import com.example.sample.model.apirequest.UpdateBillBpDish
import com.example.sample.model.apiresponse.DefaultResponse
import com.example.sample.model.apiresponse.GetAllBpDishResponse
import com.example.sample.network.RetrofitClient


import com.example.sample.network.SocketHandler
import com.example.sample.network.api.BillService
import io.socket.client.Socket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class CapnhatmonFragment : Fragment() {

    private var _binding: PvFragmentCapnhatmonBinding? = null
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
        // Inflate the layout for this fragment
        _binding = PvFragmentCapnhatmonBinding.inflate(inflater, container, false)
        val root = binding.root
        // Connect socket
        Timer().schedule(object: TimerTask() {
            override fun run() {
                mSocket.connect()
            }
        }, 1000)

        val ten_mon = CapnhatmonFragmentArgs.fromBundle(requireArguments()).ten
        val so_luong = CapnhatmonFragmentArgs.fromBundle(requireArguments()).soluong
        val ghi_chu = CapnhatmonFragmentArgs.fromBundle(requireArguments()).ghichu

        binding.tvTenmon.text = ten_mon
        binding.edtSoluong.setText(so_luong.toString())
        binding.edtGhichu.setText(ghi_chu)

        val bill_id = CapnhatmonFragmentArgs.fromBundle(requireArguments()).billId
        val table_id = CapnhatmonFragmentArgs.fromBundle(requireArguments()).tableId
        val bp_dish_id = CapnhatmonFragmentArgs.fromBundle(requireArguments()).monId
        binding.btnCapnhat.setOnClickListener {
            var service = RetrofitClient.retrofit.create(BillService::class.java)
            val updateData = UpdateBillBpDish(
                type = "capnhat",
                bp_dish_id = bp_dish_id,
                soluong = binding.edtSoluong.text.toString().toInt(),
                ghichu = binding.edtGhichu.text.toString()
            )
            val updateBillBpDishRequest = service.updateBillBpDish(updateData)
            updateBillBpDishRequest.enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    if (response.body()?.status.toString() == "success") {
                        mSocket.emit("dish_update_pv",
                            updateData.type,
                            updateData.bp_dish_id,
                            updateData.soluong,
                            updateData.ghichu)
                        val action = CapnhatmonFragmentDirections.actionCapnhatmonFragmentToBillInfoFragment(bill_id, table_id)
                        Timer().schedule(object: TimerTask() {
                            override fun run() {
                                activity?.runOnUiThread{
                                    root.findNavController().navigate(action)
                                }
                            }
                        }, 1000)
                    }
                }
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
        }

        binding.btnHuymon.setOnClickListener {
            var service = RetrofitClient.retrofit.create(BillService::class.java)
            val deleteData = UpdateBillBpDish(
                type = "xoa",
                bp_dish_id = bp_dish_id,
                soluong = binding.edtSoluong.text.toString().toInt(),
                ghichu = binding.edtGhichu.text.toString()
            )
            val deleteBillBpDishRequest = service.updateBillBpDish(deleteData)
            deleteBillBpDishRequest.enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    if (response.body()?.status.toString() == "success") {
                        mSocket.emit("dish_update_pv",
                            deleteData.type,
                            deleteData.bp_dish_id,
                            deleteData.soluong,
                            deleteData.ghichu)
                        val action = CapnhatmonFragmentDirections.actionCapnhatmonFragmentToBillInfoFragment(bill_id, table_id)
                        Timer().schedule(object: TimerTask() {
                            override fun run() {
                                activity?.runOnUiThread{
                                    root.findNavController().navigate(action)
                                }
                            }
                        }, 1000)
                    }
                }
                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSocket.disconnect()
        _binding = null
    }

}