package com.example.sample.ui.phucvu.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.databinding.PvFragmentCreateBillBinding
import com.example.sample.model.BillResponse
import com.example.sample.model.CreateBillRequest
import com.example.sample.model.GetAllTableResponse
import com.example.sample.network.BillService
import com.example.sample.network.RetrofitClient
import com.example.sample.network.TableService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import simplifiedcoding.net.kotlinretrofittutorial.storage.SharedPrefManager



class CreateBillFragment : Fragment() {
    private var _binding: PvFragmentCreateBillBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = PvFragmentCreateBillBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val tableId = CreateBillFragmentArgs.fromBundle(requireArguments()).tableId
        binding.createBillTableName.text = "Bàn số: ${tableId.toString()}"
        val user = SharedPrefManager.getInstance(this.requireContext()).user
        val service = RetrofitClient.retrofit.create(BillService::class.java)
        binding.createBillButton.setOnClickListener {
            val createBillRequest = CreateBillRequest(user.id, tableId)
            val createBill = service.createBill(createBillRequest)
            createBill.enqueue(object : Callback<BillResponse> {
                override fun onResponse(
                    call: Call<BillResponse>,
                    response: Response<BillResponse>
                ) {
                    if (response.body()?.status.toString() == "success") {
                        val billId = response.body()?.bill?.id
                        val action = CreateBillFragmentDirections.actionCreateBillFragment2ToBillInfoFragment(tableId, billId!!)
                        binding.root.findNavController().navigate(action)
                    } else {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<BillResponse>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}