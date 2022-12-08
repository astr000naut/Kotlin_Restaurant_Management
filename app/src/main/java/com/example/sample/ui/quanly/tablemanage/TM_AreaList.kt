package com.example.sample.ui.quanly.tablemanage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.databinding.QlTmArealistFragmentBinding
import com.example.sample.model.apiresponse.GetAllAreaResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.AreaService
import com.example.sample.ui.quanly.tablemanage.adapter.TM_AreaListRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TM_AreaList : Fragment() {
    private var _binding: QlTmArealistFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QlTmArealistFragmentBinding.inflate(inflater, container, false)
        val service = RetrofitClient.retrofit.create(AreaService::class.java)
        val getAllAreaRequest = service.getAllAreas()


        val adapter = TM_AreaListRecyclerViewAdapter()
        binding.qlTmRecyclerview.adapter = adapter



        getAllAreaRequest.enqueue(object : Callback<GetAllAreaResponse> {
            override fun onResponse(
                call: Call<GetAllAreaResponse>,
                response: Response<GetAllAreaResponse>
            ) {
                if (response.body()?.status.toString() == "success") {
                    val areaList = response.body()?.areas
                    if (areaList != null) {
                        adapter.submitList(areaList)
//                        Log.d("AREALIST", areaList.toString())
                    }
                } else {
                    Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<GetAllAreaResponse>, t: Throwable) {
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        binding.btnAddArea.setOnClickListener {
            val action = TM_AreaListDirections.actionTMAreaListToTMAddArea()
            binding.root.findNavController().navigate(action)
        }



        val root = binding.root
        return root
    }

}


