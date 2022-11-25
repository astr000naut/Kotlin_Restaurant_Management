package com.example.sample.ui.phucvu.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.sample.databinding.PvFragmentTableListBinding
import com.example.sample.model.apiresponse.GetAllTableResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.SocketHandler
import com.example.sample.network.api.TableService
import com.example.sample.ui.phucvu.home.adapter.TableRecyclerViewAdapter
import io.socket.client.Socket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TableListFragment : Fragment() {

    private var _binding: PvFragmentTableListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var mSocket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSocket = SocketHandler.getSocket()
    }

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
        Timer().schedule(object: TimerTask() {
            override fun run() {
                mSocket.connect()
                mSocket.on(Socket.EVENT_CONNECT) {
                    Log.d("SOCKET", "CONNECTED TABLE LIST")
                }
                mSocket.on(Socket.EVENT_CONNECT_ERROR) {
                    Log.d("SOCKET", "CONNECT ERROR TABLE LIST")
                }
                mSocket.on(Socket.EVENT_DISCONNECT) {
                    Log.d("SOCKET", "DISCONNECT TABLE LIST")
                }
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
            }
        }, 1000)


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mSocket.disconnect()
        mSocket.off("counter")
        mSocket.off(Socket.EVENT_CONNECT)
        mSocket.off(Socket.EVENT_DISCONNECT)
        mSocket.off(Socket.EVENT_CONNECT_ERROR)
        Log.d("TABLELIST", "DESTROYED")
        _binding = null
    }
}
