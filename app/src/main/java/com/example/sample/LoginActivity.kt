package com.example.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.sample.databinding.LiActivityLoginBinding
import com.example.sample.model.LoginRequest
import com.example.sample.model.LoginResponse
import com.example.sample.network.LoginService
import com.example.sample.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import simplifiedcoding.net.kotlinretrofittutorial.storage.SharedPrefManager

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LiActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LiActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var btn_submit = binding.btnSubmit


        btn_submit.setOnClickListener {
            loginUser()
        }
    }

    private fun loginUser() {
        val email = binding.etUsername.text.toString()
        val password = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            val service = RetrofitClient.retrofit.create(LoginService::class.java)
            val loginInfo = LoginRequest(email, password)
            val loginRequest = service.login(loginInfo)
            loginRequest.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.body()?.message.toString() == "Login success") {
                        val user = response.body()?.user!!
                        SharedPrefManager.getInstance(applicationContext).saveUser(user)
                        var intent = Intent(applicationContext, PhucvuActivity::class.java)
                        when (user.role) {
                            "phucvu" -> intent = Intent(applicationContext, PhucvuActivity::class.java)
                            "bep" -> intent = Intent(applicationContext, BepActivity::class.java)
                            "quanly" -> intent = Intent(applicationContext, QuanlyActivity::class.java)
                            "thungan" -> intent = Intent(applicationContext, ThunganActivity::class.java)
                        }

                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

            })

        } else {
            Toast.makeText(this,
                "Email and password cannot be empty",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStart() {
        super.onStart()
//        if(SharedPrefManager.getInstance(this).isLoggedIn) {
//            var intent = Intent(applicationContext, PhucvuActivity::class.java)
//            val user = SharedPrefManager.getInstance(this).user
//            when (user.role) {
//                "phucvu" -> intent = Intent(applicationContext, PhucvuActivity::class.java)
//                "bep" -> intent = Intent(applicationContext, BepActivity::class.java)
//                "quanly" -> intent = Intent(applicationContext, QuanlyActivity::class.java)
//                "thungan" -> intent = Intent(applicationContext, ThunganActivity::class.java)
//            }
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
//        }
    }
}