package com.example.sample.ui.phucvu.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import com.example.sample.R
import com.example.sample.databinding.PvFragmentTableListBinding
import com.example.sample.model.apirequest.TableFilterRequest
import com.example.sample.model.apiresponse.GetAllAreaResponse
import com.example.sample.model.apiresponse.GetAllTableResponse
import com.example.sample.network.RetrofitClient
import com.example.sample.network.SocketHandler
import com.example.sample.network.api.AreaService
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

        // Ui listener
        val rg_trangthai = binding.rgTrangthai
        val spn_khuvuc: Spinner = binding.spnKhuvuc
        val spn_loaiban: Spinner = binding.spnLoaiban
        val list_loaiban = arrayOf("2", "4", "6", "8", "Tất cả")
        val spn_loaiban_adapter = activity?.let {
            ArrayAdapter(it, R.layout.z_layout_spinner_item, list_loaiban)
        }
        spn_loaiban.adapter = spn_loaiban_adapter

        binding.btnTimkiem.setOnClickListener {
            val khuvuc = spn_khuvuc.selectedItem.toString()
            val socho = spn_loaiban.selectedItem.toString()
            var trangthai = "tatca"
            when(rg_trangthai.checkedRadioButtonId) {
                binding.btnDangphucvu.id -> trangthai = "dangphucvu"
                binding.btnBantrong.id -> trangthai = "trong"
            }
            val filterTableRequest = service.filterTables(TableFilterRequest(
                khuvuc, socho, trangthai
            ))
            filterTableRequest.enqueue(object : Callback<GetAllTableResponse> {
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

        // Call API to get all area
        val areaService = RetrofitClient.retrofit.create(AreaService::class.java)
        val getAllAreaRequest = areaService.getAllAreas()
        getAllAreaRequest.enqueue(object : Callback<GetAllAreaResponse> {
            override fun onResponse(
                call: Call<GetAllAreaResponse>,
                response: Response<GetAllAreaResponse>
            ) {
                if (response.body()?.status.toString() == "success") {
                    val areaList = response.body()?.areas!!
                    val spn_khuvuc_adapter = activity?.let {
                        ArrayAdapter(it, R.layout.z_layout_spinner_item, areaList)
                    }
                    spn_khuvuc.adapter = spn_khuvuc_adapter
                } else {
                    Toast.makeText(context, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetAllAreaResponse>, t: Throwable) {
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
