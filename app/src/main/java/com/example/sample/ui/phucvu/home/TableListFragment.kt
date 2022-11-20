package com.example.sample.ui.phucvu.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sample.R
import com.example.sample.databinding.PvFragmentHomeBinding
import com.example.sample.databinding.PvFragmentTableListBinding
import com.example.sample.model.GetAllTableResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.SocketHandler
import com.example.sample.network.TableService
import io.socket.client.Socket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TableListFragment : Fragment() {

    private var _binding: PvFragmentTableListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var mSocket: Socket

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = PvFragmentTableListBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val adapter = TableRecyclerViewAdapter()
        _binding!!.pvRecyclerview.adapter = adapter

        // Call API to get all table
        val service = RetrofitClient.retrofit.create(TableService::class.java)
        val getAllTablesRequest = service.getAllTables()
        getAllTablesRequest.enqueue(object : Callback<GetAllTableResponse> {
            override fun onResponse(
                call: Call<GetAllTableResponse>,
                response: Response<GetAllTableResponse>
            ) {
                if (response.body()?.status.toString() == "success") {
                    val tablelist = response.body()?.tables
                    if (tablelist != null) {
                        adapter.submitList(tablelist)
                    }
                } else {
                    Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetAllTableResponse>, t: Throwable) {
                Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })

        // Socket

        SocketHandler.setSocket()
        mSocket = SocketHandler.getSocket()
        mSocket.connect()

        mSocket.on("counter") { args ->
            if (args[0] != null) {
                getAllTablesRequest.clone().enqueue(object : Callback<GetAllTableResponse> {
                    override fun onResponse(
                        call: Call<GetAllTableResponse>,
                        response: Response<GetAllTableResponse>
                    ) {
                        if (response.body()?.status.toString() == "success") {
                            val tablelist = response.body()?.tables
                            if (tablelist != null) {
                                adapter.submitList(tablelist)
                            }
                        } else {
                            Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<GetAllTableResponse>, t: Throwable) {
                        Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSocket.disconnect()
        _binding = null
    }
}
