package com.example.sample.ui.quanly.personnelmanage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.sample.R
import com.example.sample.databinding.QlPmAddpersonnelFragBinding
import com.example.sample.model.apirequest.CreateUser
import com.example.sample.model.apiresponse.DefaultResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.api.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class PM_AddPersonnel : Fragment() {

    private var _binding: QlPmAddpersonnelFragBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = QlPmAddpersonnelFragBinding.inflate(inflater, container, false)
        val list_role = arrayOf("NV phục vụ", "NV bếp", "NV thu ngân")
        val spn_role_adapter = activity?.let {
            ArrayAdapter(it, R.layout.z_layout_spinner_item, list_role)
        }
        binding.spnUserRole.adapter = spn_role_adapter


        val userService = RetrofitClient.retrofit.create(UserService::class.java)
        binding.pmAddBtnAdd.setOnClickListener {
            val role = when (binding.spnUserRole.selectedItem.toString()) {
                "NV phục vụ" -> "phucvu"
                "NV thu ngân" -> "thungan"
                "NV bếp" -> "bep"
                else -> ""
            }
            val createUserRequest = userService.createUser(CreateUser(
                ten = binding.pmAddEdtName.text.toString(),
                tuoi = binding.pmAddEdtAge.text.toString().toInt(),
                sdt = binding.pmAddEdtSdt.text.toString(),
                noio = binding.pmAddEdtAddress.text.toString(),
                email = binding.pmAddEdtEmail.text.toString(),
                role = role,
                username = binding.pmAddEdtUsername.text.toString(),
                password = binding.pmAddEdtPass.text.toString()
            ))
            createUserRequest.enqueue(object : Callback<DefaultResponse> {
                override fun onResponse(
                    call: Call<DefaultResponse>,
                    response: Response<DefaultResponse>
                ) {
                    if (response.body()?.status.toString() == "success") {
                        Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                        Timer().schedule(object: TimerTask() {
                            override fun run() {
                                activity?.runOnUiThread{
                                    val action = PM_AddPersonnelDirections.actionPMAddPersonnelToPersonnelListFragment()
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
        }
        val root = binding.root
        return root
    }
}