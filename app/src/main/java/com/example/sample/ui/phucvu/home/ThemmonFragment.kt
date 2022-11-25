package com.example.sample.ui.phucvu.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.databinding.PvFragmentThemmonBinding
import com.example.sample.model.*


import com.example.sample.network.BillService
import com.example.sample.network.DishService
import com.example.sample.network.RetrofitClient
import com.example.sample.network.SocketHandler
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.socket.client.Socket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class ThemmonFragment : Fragment() {
    private var _binding: PvFragmentThemmonBinding? = null
    private val binding get() = _binding!!
    lateinit var mSocket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        mSocket = SocketHandler.getSocket()
        Log.d("SET SOCKET", mSocket.toString())
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PvFragmentThemmonBinding.inflate(inflater, container, false)
        val root = binding.root
        val adapter = ThemmonRecyclerViewAdapter()
        _binding!!.themmonRecyclerview.adapter = adapter

        //Connect Socket
        Timer().schedule(object: TimerTask() {
            override fun run() {
                mSocket.connect()
                mSocket.on(Socket.EVENT_CONNECT) {
                    Log.d("SOCKET", "CONNECTED THEM MON")
                }
                mSocket.on(Socket.EVENT_CONNECT_ERROR) {
                    Log.d("SOCKET", "CONNECT ERROR THEM MON")
                }
                mSocket.on(Socket.EVENT_DISCONNECT) {
                    Log.d("SOCKET", "DISCONNECT THEM MON")
                }
            }
        }, 1000)


        // Call API to get all dishes
        val service = RetrofitClient.retrofit.create(DishService::class.java)
        val getAllDishesRequest = service.getAllDish()
        getAllDishesRequest.enqueue(object : Callback<GetAllDishResponse> {
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

        val billService = RetrofitClient.retrofit.create(BillService::class.java)
        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting().create()



        binding.btnThemmon.setOnClickListener {
            val bill_id = ThemmonFragmentArgs.fromBundle(requireArguments()).billId
            val table_id = ThemmonFragmentArgs.fromBundle(requireArguments()).tableId
            val dish_add_list = mutableListOf<Dish>()
            adapter.currentList.forEach{dish ->
                if(dish.soluong > 0) {
                    dish.ban = table_id
                    dish.trangthai = "Chưa làm"
                    dish.billId = bill_id
                    dish_add_list.add(dish)
                }
            }
            Log.d("DISHHHH", dish_add_list.toString())
            val addDishRequest = billService.addDish(AddDishRequest(bill_id, dish_add_list))
            if (dish_add_list.size > 0) {
                addDishRequest.enqueue(object : Callback<BillAddDishResponse> {
                    override fun onResponse(
                        call: Call<BillAddDishResponse>,
                        response: Response<BillAddDishResponse>
                    ) {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                        val hoadonmonId = response.body()?.hoadonmonId
                        val bpDishListCreated = mutableListOf<BP_Dish>()
                        for (i in dish_add_list.indices)
                            bpDishListCreated.add(BP_Dish(
                                id = hoadonmonId!![i],
                                ban = dish_add_list[i].ban,
                                ten = dish_add_list[i].ten,
                                gia = dish_add_list[i].gia,
                                soluong = dish_add_list[i].soluong,
                                ghichu = dish_add_list[i].ghichu,
                                trangthai = dish_add_list[i].trangthai,
                                billId = bill_id
                            ))
                        Log.d("DISH LIST RETURN", bpDishListCreated.toString())
                        mSocket.emit("dish_list_pv", gson.toJson(bpDishListCreated))

                        val action = ThemmonFragmentDirections.actionThemmonFragmentToBillInfoFragment(bill_id, table_id)
                        Timer().schedule(object: TimerTask() {
                            override fun run() {
                                activity?.runOnUiThread{
                                    root.findNavController().navigate(action)
                                }
                            }

                        }, 1000)
                    }

                    override fun onFailure(call: Call<BillAddDishResponse>, t: Throwable) {
                        Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSocket.disconnect()
        mSocket.off(Socket.EVENT_CONNECT)
        mSocket.off(Socket.EVENT_DISCONNECT)
        mSocket.off(Socket.EVENT_CONNECT_ERROR)
        Log.d("THEMMON", "DESTROYED")
        _binding = null
    }

}