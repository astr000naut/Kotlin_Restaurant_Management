package com.example.sample.ui.bep.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sample.databinding.BpFragmentDishListBinding

import com.example.sample.model.BP_Dish
import com.example.sample.model.apiresponse.GetListBpDishResponse
import com.example.sample.model.database.BpDishDatabase
import com.example.sample.network.RetrofitClient
import com.example.sample.network.SocketHandler
import com.example.sample.network.api.DishService
import com.example.sample.ui.bep.home.adapter.DishListRecyclerViewAdapter
import com.example.sample.ui.bep.home.viewmodel.DishListViewModel
import com.example.sample.ui.bep.home.viewmodel.DishListViewModelFactory
import com.google.gson.Gson
import io.socket.client.Socket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class DIshListFragment : Fragment() {

    private var _binding: BpFragmentDishListBinding? = null
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
        _binding = BpFragmentDishListBinding.inflate(inflater, container, false)
        val root = binding.root
        Timer().schedule(object: TimerTask() {
            override fun run() {
                mSocket.connect()
            }
        }, 1000)
        // Create view model
        val application = requireNotNull(this.activity).application
        val dao = BpDishDatabase.getInstance(application).bpDishDao
        val viewModelFactory = DishListViewModelFactory(dao)
        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(DishListViewModel::class.java)

        // Create adapter
        val dagiaoListener: (BP_Dish) -> Unit = {bpDish ->
            viewModel.removeBpDish(bpDish)
            mSocket.emit("dish_state_change", bpDish.id, 2)
        }
        val batdaulamListener: (BP_Dish) -> Unit = {bpDish ->
            viewModel.changeStateBpDish(bpDish)
            mSocket.emit("dish_state_change", bpDish.id, 1)
        }
        val adapter = DishListRecyclerViewAdapter(
            dagiaoListener,
            batdaulamListener
        )
        binding.bpDishlistRecyclerview.adapter = adapter


        val gson = Gson()

        viewModel.bpDishList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        var service = RetrofitClient.retrofit.create(DishService::class.java)
        val getUnfinishedBpDishRequest = service.getUnfinishedDish()
        getUnfinishedBpDishRequest.enqueue(
            object : Callback<GetListBpDishResponse> {
                override fun onResponse(
                    call: Call<GetListBpDishResponse>,
                    response: Response<GetListBpDishResponse>
                ) {
                    if (response.body()?.status.toString() == "success") {
                        val bpdish_list = response.body()?.bp_dishes
                        Log.d("UNFINISHED DISH", bpdish_list.toString())
                        bpdish_list?.forEach{bpDish: BP_Dish ->
                            viewModel.addBpDish(bpDish)
                        }
                    } else {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetListBpDishResponse>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            }
        )

        mSocket.on("dish_list_bep") { args ->
            if (args[0] != null) {
                val bpdish_list = args[0].toString()
                val objectBpDishList = gson.fromJson(bpdish_list, Array<BP_Dish>::class.java).asList()
                objectBpDishList.forEach{bpDish: BP_Dish ->
                    viewModel.addBpDish(bpDish)
                }
            }
        }
        mSocket.on("dish_update_bep") { args ->
            if (args[0] != null) {
                Log.d("DISH UPDATE BEP",
                    args[0].toString() + args[1].toString() + args[2].toString() + args[3].toString())
                if (args[0].toString() == "capnhat") {
                    viewModel.updateBpDishById(args[1].toString().toInt(), args[2].toString().toInt(), args[3].toString())
                }
                if (args[0].toString() == "xoa") {
                    viewModel.removeBpDishById(args[1].toString().toInt())
                }
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSocket.disconnect()
        mSocket.off("dish_list_bep")
        mSocket.off("dish_update_bep")
        Log.d("DISHLIST", "DESTROYED")
        _binding = null
    }
}