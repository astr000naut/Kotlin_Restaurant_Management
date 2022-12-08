package com.example.sample.ui.quanly.dishmanage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.R
import com.example.sample.databinding.QlDmDishinfoFragBinding
import com.example.sample.model.apiresponse.DefaultResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.DishService
import com.example.sample.ui.quanly.tablemanage.TM_AreaInfoDirections
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class DM_DishInfoFragment : Fragment() {

    private var _binding: QlDmDishinfoFragBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QlDmDishinfoFragBinding.inflate(inflater, container, false)
        val id = DM_DishInfoFragmentArgs.fromBundle(requireArguments()).id
        val ten = DM_DishInfoFragmentArgs.fromBundle(requireArguments()).ten
        val gia = DM_DishInfoFragmentArgs.fromBundle(requireArguments()).gia

        val dishService = RetrofitClient.retrofit.create(DishService::class.java)
        val deleteDishRequest = dishService.deleteDish(id)

        binding.tvDmAddishTen.text = "Tên món: " + ten
        binding.tvDmAddishGia.text = "Giá: " + gia.toString()
        binding.btnDmDeletedish.setOnLongClickListener {
            deleteDishRequest.enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if (response.body()?.status.toString() == "success") {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                        Timer().schedule(object: TimerTask() {
                            override fun run() {
                                activity?.runOnUiThread{
                                    val action = DM_DishInfoFragmentDirections.actionDMDishInfoFragmentToDishListFragment()
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