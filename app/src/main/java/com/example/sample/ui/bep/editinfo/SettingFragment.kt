package com.example.sample.ui.bep.editinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sample.R
import com.example.sample.databinding.BpFragmentSettingBinding
import com.example.sample.model.apirequest.UpdateUser
import com.example.sample.model.apiresponse.UserListResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import simplifiedcoding.net.kotlinretrofittutorial.storage.SharedPrefManager


class  SettingFragment : Fragment() {
    private var _binding: BpFragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = BpFragmentSettingBinding.inflate(inflater, container, false)
        val userService = RetrofitClient.retrofit.create(UserService::class.java)

        val user = SharedPrefManager.getInstance(this.requireActivity()).user
        binding.bpInfEdtName.setText(user.ten)
        binding.bpInfEdtAge.setText(user.tuoi.toString())
        binding.bpInfEdtSdt.setText(user.sdt)
        binding.bpInfEdtEmail.setText(user.email)
        binding.bpInfEdtAddress.setText(user.noio)

        binding.bpInfBtnUpdate.setOnClickListener {
            val oldpass = binding.bpInfEdtOldPass.text
            val newpass = binding.bpInfEdtNewPass.text
            val newpassre = binding.bpInfEdtNewPasRe.text
            if (newpass.toString() != newpassre.toString()) {
                Toast.makeText(context, "Mật khẩu không khớp, vui lòng nhập lại", Toast.LENGTH_SHORT).show()
            } else {
                val updateUserRequest = userService.updateUser(
                    UpdateUser(
                    id = user.id,
                    ten = binding.bpInfEdtName.text.toString(),
                    tuoi = binding.bpInfEdtAge.text.toString().toInt(),
                    sdt = binding.bpInfEdtSdt.text.toString(),
                    noio = binding.bpInfEdtAddress.text.toString(),
                    email = binding.bpInfEdtEmail.text.toString(),
                    oldpass = oldpass.toString(),
                    newpass = newpass.toString())
                )
                updateUserRequest.enqueue(object : Callback<UserListResponse> {
                    override fun onResponse(
                        call: Call<UserListResponse>,
                        response: Response<UserListResponse>
                    ) {
                        if (response.body()?.status.toString() == "success") {
                            Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                            if (response.body()?.users?.size!! > 0) {
                                SharedPrefManager.getInstance(activity!!).saveUser(response.body()?.users?.get(0)!!)
                                binding.bpInfEdtOldPass.setText("")
                                binding.bpInfEdtNewPass.setText("")
                                binding.bpInfEdtNewPasRe.setText("")
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