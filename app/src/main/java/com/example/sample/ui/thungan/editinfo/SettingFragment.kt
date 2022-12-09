package com.example.sample.ui.thungan.editinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sample.databinding.TnFragmentSettingBinding
import com.example.sample.model.apirequest.UpdateUser
import com.example.sample.model.apiresponse.UserListResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import simplifiedcoding.net.kotlinretrofittutorial.storage.SharedPrefManager

class SettingFragment : Fragment() {
    private var _binding: TnFragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TnFragmentSettingBinding.inflate(inflater, container, false)

        val userService = RetrofitClient.retrofit.create(UserService::class.java)

        val user = SharedPrefManager.getInstance(this.requireActivity()).user
        binding.tnInfEdtName.setText(user.ten)
        binding.tnInfEdtAge.setText(user.tuoi.toString())
        binding.tnInfEdtSdt.setText(user.sdt)
        binding.tnInfEdtEmail.setText(user.email)
        binding.tnInfEdtAddress.setText(user.noio)

        binding.tnInfBtnUpdate.setOnClickListener {
            val oldpass = binding.tnInfEdtOldPass.text
            val newpass = binding.tnInfEdtNewPass.text
            val newpassre = binding.tnInfEdtNewPasRe.text
            if (newpass.toString() != newpassre.toString()) {
                Toast.makeText(context, "Mật khẩu không khớp, vui lòng nhập lại", Toast.LENGTH_SHORT).show()
            } else {
                val updateUserRequest = userService.updateUser(
                    UpdateUser(
                    id = user.id,
                    ten = binding.tnInfEdtName.text.toString(),
                    tuoi = binding.tnInfEdtAge.text.toString().toInt(),
                    sdt = binding.tnInfEdtSdt.text.toString(),
                    noio = binding.tnInfEdtAddress.text.toString(),
                    email = binding.tnInfEdtEmail.text.toString(),
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
                                binding.tnInfEdtOldPass.setText("")
                                binding.tnInfEdtNewPass.setText("")
                                binding.tnInfEdtNewPasRe.setText("")
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