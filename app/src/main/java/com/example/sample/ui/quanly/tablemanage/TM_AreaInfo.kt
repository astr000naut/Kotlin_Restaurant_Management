package com.example.sample.ui.quanly.tablemanage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.R
import com.example.sample.databinding.QlTmAreainfoFragBinding
import com.example.sample.model.apirequest.DeleteAreaRequest
import com.example.sample.model.apiresponse.DefaultResponse
import com.example.sample.model.apiresponse.GetAllAreaResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.AreaService
import com.example.sample.ui.phucvu.home.CreateBillFragmentDirections
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TM_AreaInfo : Fragment() {

    private var _binding: QlTmAreainfoFragBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QlTmAreainfoFragBinding.inflate(inflater, container, false)
        val areaName = TM_AreaInfoArgs.fromBundle(requireArguments()).areaname
        val slb2 = TM_AreaInfoArgs.fromBundle(requireArguments()).slb2
        val slb4 = TM_AreaInfoArgs.fromBundle(requireArguments()).slb4
        val slb6 = TM_AreaInfoArgs.fromBundle(requireArguments()).slb6
        val slb8 = TM_AreaInfoArgs.fromBundle(requireArguments()).slb8
        binding.tvAreaname.text = "Tên khu vực: " + areaName
        binding.tvSlb2.text = "Số lượng bàn 2 chỗ: " + slb2.toString()
        binding.tvSlb4.text = "Số lượng bàn 4 chỗ: " + slb4.toString()
        binding.tvSlb6.text = "Số lượng bàn 6 chỗ: " + slb6.toString()
        binding.tvSlb8.text = "Số lượng bàn 8 chỗ: " + slb8.toString()

        val areaService = RetrofitClient.retrofit.create(AreaService::class.java)
        val deleteAreaRequest = areaService.deleteArea(DeleteAreaRequest(areaName))

        binding.btnDeleteArea.setOnLongClickListener {
            deleteAreaRequest.enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if (response.body()?.status.toString() == "success") {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                        Timer().schedule(object: TimerTask() {
                            override fun run() {
                                activity?.runOnUiThread{
                                    val action = TM_AreaInfoDirections.actionTMAreaInfoToTMAreaList()
                                    binding.root.findNavController().navigate(action)
                                }
                            }
                        }, 1000)
                    } else {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
            true
        }
        val root = binding.root
        return root
    }

}