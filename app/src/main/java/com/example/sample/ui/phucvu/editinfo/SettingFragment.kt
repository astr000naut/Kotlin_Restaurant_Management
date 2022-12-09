package com.example.sample.ui.phucvu.editinfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.R
import com.example.sample.databinding.PvFragmentSettingBinding
import com.example.sample.databinding.TnFragmentBillHistoryBinding
import com.example.sample.model.User
import com.example.sample.model.apirequest.UpdateUser
import com.example.sample.model.apiresponse.DefaultResponse
import com.example.sample.model.apiresponse.UserListResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.UserService
import com.example.sample.ui.quanly.personnelmanage.PM_PersonnelInfoDirections
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import simplifiedcoding.net.kotlinretrofittutorial.storage.SharedPrefManager
import java.util.*

class SettingFragment : Fragment() {

    private var _binding: PvFragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PvFragmentSettingBinding.inflate(inflater, container, false)

        val userService = RetrofitClient.retrofit.create(UserService::class.java)

        val user = SharedPrefManager.getInstance(this.requireActivity()).user
        binding.pvInfEdtName.setText(user.ten)
        binding.pvInfEdtAge.setText(user.tuoi.toString())
        binding.pvInfEdtSdt.setText(user.sdt)
        binding.pvInfEdtEmail.setText(user.email)
        binding.pvInfEdtAddress.setText(user.noio)

        binding.pvInfBtnUpdate.setOnClickListener {
            val oldpass = binding.pvInfEdtOldPass.text
            val newpass = binding.pvInfEdtNewPass.text
            val newpassre = binding.pvInfEdtNewPasRe.text
            if (newpass.toString() != newpassre.toString()) {
                Toast.makeText(context, "Mật khẩu không khớp, vui lòng nhập lại", Toast.LENGTH_SHORT).show()
            } else {
                val updateUserRequest = userService.updateUser(UpdateUser(
                        id = user.id,
                        ten = binding.pvInfEdtName.text.toString(),
                        tuoi = binding.pvInfEdtAge.text.toString().toInt(),
                        sdt = binding.pvInfEdtSdt.text.toString(),
                        noio = binding.pvInfEdtAddress.text.toString(),
                        email = binding.pvInfEdtEmail.text.toString(),
                        oldpass = oldpass.toString(),
                        newpass = newpass.toString()))
                updateUserRequest.enqueue(object : Callback<UserListResponse> {
                    override fun onResponse(
                        call: Call<UserListResponse>,
                        response: Response<UserListResponse>
                    ) {
                        if (response.body()?.status.toString() == "success") {
                            Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                            if (response.body()?.users?.size!! > 0) {
                                SharedPrefManager.getInstance(activity!!).saveUser(response.body()?.users?.get(0)!!)
                                binding.pvInfEdtOldPass.setText("")
                                binding.pvInfEdtNewPass.setText("")
                                binding.pvInfEdtNewPasRe.setText("")
                            }

                        } else {
                            Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                        Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                })


            }


        }

        val root = binding.root
        return root
    }
}