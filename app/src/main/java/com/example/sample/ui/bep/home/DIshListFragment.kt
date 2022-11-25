package com.example.sample.ui.bep.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.sample.databinding.BpFragmentDishListBinding
import com.example.sample.model.BP_Dish
import com.example.sample.model.database.BpDishDatabase
import com.example.sample.network.SocketHandler
import com.example.sample.ui.bep.home.adapter.DishListRecyclerViewAdapter
import com.example.sample.ui.bep.home.viewmodel.DishListViewModel
import com.example.sample.ui.bep.home.viewmodel.DishListViewModelFactory
import com.google.gson.Gson
import io.socket.client.Socket
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

        mSocket.on("dish_list_bep") { args ->
            if (args[0] != null) {
                val bpdish_list = args[0].toString()
                val objectBpDishList = gson.fromJson(bpdish_list, Array<BP_Dish>::class.java).asList()
                objectBpDishList.forEach{bpDish: BP_Dish ->
                    viewModel.addBpDish(bpDish)
                }
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSocket.disconnect()
        mSocket.off("dish_list_bep")
        Log.d("DISHLIST", "DESTROYED")
        _binding = null
    }
}