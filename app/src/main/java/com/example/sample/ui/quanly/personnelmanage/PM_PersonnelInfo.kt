package com.example.sample.ui.quanly.personnelmanage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.R
import com.example.sample.databinding.QlPmPersonnelinfoFragBinding
import com.example.sample.model.apirequest.DeleteAreaRequest
import com.example.sample.model.apirequest.UpdateUser
import com.example.sample.model.apiresponse.DefaultResponse
import com.example.sample.model.apiresponse.UserListResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.AreaService
import com.example.sample.network.api.UserService
import com.example.sample.ui.quanly.tablemanage.TM_AreaInfoDirections
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class PM_PersonnelInfo : Fragment() {

    private var _binding: QlPmPersonnelinfoFragBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QlPmPersonnelinfoFragBinding.inflate(inflater, container, false)

        val id = PM_PersonnelInfoArgs.fromBundle(requireArguments()).id
        val name = PM_PersonnelInfoArgs.fromBundle(requireArguments()).name
        val age = PM_PersonnelInfoArgs.fromBundle(requireArguments()).age
        val sdt = PM_PersonnelInfoArgs.fromBundle(requireArguments()).sdt
        val email = PM_PersonnelInfoArgs.fromBundle(requireArguments()).email
        val address = PM_PersonnelInfoArgs.fromBundle(requireArguments()).address

        binding.pmInfEdtName.setText(name)
        binding.pmInfEdtAge.setText(age.toString())
        binding.pmInfEdtSdt.setText(sdt)
        binding.pmInfEdtEmail.setText(email)
        binding.pmInfEdtAddress.setText(address)

        val userService = RetrofitClient.retrofit.create(UserService::class.java)

        binding.pmInfBtnUpdate.setOnClickListener {
            val updateUserRequest = userService.updateUser(UpdateUser(
                id,
                binding.pmInfEdtName.text.toString(),
                binding.pmInfEdtAge.text.toString().toInt(),
                binding.pmInfEdtSdt.text.toString(),
                binding.pmInfEdtAddress.text.toString(),
                binding.pmInfEdtEmail.text.toString(),
            ))
            updateUserRequest.enqueue(object : Callback<UserListResponse> {
                override fun onResponse(
                    call: Call<UserListResponse>,
                    response: Response<UserListResponse>
                ) {
                    if (response.body()?.status.toString() == "success") {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                        Timer().schedule(object: TimerTask() {
                            override fun run() {
                                activity?.runOnUiThread{
                                    val action = PM_PersonnelInfoDirections.actionPMPersonnelInfoToPersonnelListFragment()
                                    binding.root.findNavController().navigate(action)
                                }
                            }
                        }, 1000)
                    } else {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.pmInfBtnDelete.setOnLongClickListener {
            val deleteUserRequest = userService.deleteUser(id)
            deleteUserRequest.enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if (response.body()?.status.toString() == "success") {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                        Timer().schedule(object: TimerTask() {
                            override fun run() {
                                activity?.runOnUiThread{
                                    val action = PM_PersonnelInfoDirections.actionPMPersonnelInfoToPersonnelListFragment()
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