package com.example.sample.ui.quanly.tablemanage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.R
import com.example.sample.databinding.QlTmAddareaFragBinding
import com.example.sample.databinding.QlTmAreainfoFragBinding
import com.example.sample.model.BillResponse
import com.example.sample.model.apirequest.AddAreaRequest
import com.example.sample.model.apiresponse.DefaultResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.AreaService
import com.example.sample.ui.phucvu.home.CreateBillFragmentDirections
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class TM_AddArea : Fragment() {

    private var _binding: QlTmAddareaFragBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QlTmAddareaFragBinding.inflate(inflater, container, false)

        val areaService = RetrofitClient.retrofit.create(AreaService::class.java)
        val areaName = binding.edtAreaname.text
        val b2 = binding.edtSlb2.text
        val b4 = binding.edtSlb4.text
        val b6 = binding.edtSlb6.text
        val b8 = binding.edtSlb8.text

        binding.btnAddArea.setOnClickListener {
            Log.d("ADD AREA CLICKED", "$areaName-$b2-$b4-$b6-$b8")
            if (areaName.isNotEmpty() && b2.isNotEmpty() && b4.isNotEmpty() && b6.isNotEmpty() && b8.isNotEmpty()) {
                val addAreaRequest = areaService.addArea(AddAreaRequest(areaName.toString(), b2.toString().toInt(), b4.toString().toInt(), b6.toString().toInt(), b8.toString().toInt()))
                addAreaRequest.enqueue(object : Callback<DefaultResponse> {
                    override fun onResponse(
                        call: Call<DefaultResponse>,
                        response: Response<DefaultResponse>
                    ) {
                        if (response.body()?.status.toString() == "success") {
                            Timer().schedule(object: TimerTask() {
                                override fun run() {
                                    activity?.runOnUiThread{
                                        val action =
                                            TM_AddAreaDirections.actionTMAddAreaToTMAreaList()
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

            } else {
                Toast.makeText(context, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            }
        }

        val root = binding.root
        return root
    }

}