package vn.iotstar.appdoctruyen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.BinhLuanTruyenAdapter
import vn.iotstar.appdoctruyen.model.BinhLuanTruyenDto
import vn.iotstar.appdoctruyen.model.Taikhoan
import vn.iotstar.appdoctruyen.model.Thongke
import vn.iotstar.appdoctruyen.model.Truyen1
import vn.iotstar.appdoctruyen.model.truyen

class ChiTietTruyenFragment : Fragment() {
    private var truyen: Truyen1? = null
    private var rootView: View? = null // Đổi tên từ `view` thành `rootView`
    private var tv_danhgia: TextView? = null
    private var tv_tongluotxem: TextView? = null
    private var tv_tongbinhluan: TextView? = null
    private var tv_mota: TextView? = null
    private var thongKe: Thongke? = null
    private var taiKhoan: Taikhoan? = null
    private var email: String? = null
    private var rcv_binhluan: RecyclerView? = null
    private var rcv_adapter: BinhLuanTruyenAdapter? = null
    private var id_truyen = 0
    private var tbDanhGia = 0.0
    private var tongLuotXem: Long? = null
    private var tongBinhLuan: Long? = null
    private var listBinhLuan: List<BinhLuanTruyenDto>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            truyen = it.getParcelable(ARG_PARAM1)
            email = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootView = inflater.inflate(R.layout.fragment_chi_tiet_truyen, container, false)
        Anhxa()
        val intent = requireActivity().intent
        id_truyen = intent.getIntExtra("id_truyen", 0)
        email = intent.getStringExtra("email")
        setupRecyclerView()
        loadChiTietTruyen()
        return rootView
    }

    private fun Anhxa() {
        tv_danhgia = rootView!!.findViewById(R.id.tv_danhgia)
        tv_tongluotxem = rootView!!.findViewById(R.id.tv_tongluotxem)
        tv_tongbinhluan = rootView!!.findViewById(R.id.tv_tongbinhluan)
        tv_mota = rootView!!.findViewById(R.id.tv_motatruyen)
        rcv_binhluan = rootView!!.findViewById(R.id.rcv_binhluan)
    }

    private fun setupRecyclerView() {
        rcv_binhluan?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        listBinhLuan = ArrayList()
        rcv_adapter = BinhLuanTruyenAdapter(requireActivity(), listBinhLuan)
        rcv_binhluan?.adapter = rcv_adapter
    }

    private fun loadChiTietTruyen() {
        // Gọi các API và cập nhật giao diện
        APIService.apiService.getAverageRatingByTruyenId(id_truyen)?.enqueue(object : Callback<Double?> {
            override fun onResponse(call: Call<Double?>, response: Response<Double?>) {
                tbDanhGia = response.body() ?: 0.0
                tv_danhgia?.text = tbDanhGia.toString()
            }

            override fun onFailure(call: Call<Double?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
            }
        })
        APIService.apiService.getTruyenById(id_truyen)?.enqueue(object : Callback<List<truyen>?> {
            override fun onResponse(call: Call<List<truyen>?>, response: Response<List<truyen>?>) {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        if (data.isNotEmpty()) {
                            tv_mota?.text = data[0].mota

                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<truyen>?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
                Toast.makeText(context, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })

        APIService.apiService.countBinhLuanByTruyenId(id_truyen)?.enqueue(object : Callback<Long?> {
            override fun onResponse(call: Call<Long?>, response: Response<Long?>) {
                tongBinhLuan = response.body()
                tv_tongbinhluan?.text = tongBinhLuan.toString()
            }

            override fun onFailure(call: Call<Long?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
            }
        })

        APIService.apiService.sumSoluotxemByTruyenId(id_truyen)?.enqueue(object : Callback<Long?> {
            override fun onResponse(call: Call<Long?>, response: Response<Long?>) {
                tongLuotXem = response.body()
                tv_tongluotxem?.text = tongLuotXem.toString()
            }

            override fun onFailure(call: Call<Long?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
            }
        })

        APIService.apiService.getBinhLuan(id_truyen)?.enqueue(object : Callback<List<BinhLuanTruyenDto>?> {
            override fun onResponse(call: Call<List<BinhLuanTruyenDto>?>, response: Response<List<BinhLuanTruyenDto>?>) {
                listBinhLuan = response.body()
                rcv_adapter = BinhLuanTruyenAdapter(requireContext(), listBinhLuan)
                rcv_binhluan?.adapter = rcv_adapter
            }

            override fun onFailure(call: Call<List<BinhLuanTruyenDto>?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
                Toast.makeText(context, "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(truyen: Truyen1, email: String): ChiTietTruyenFragment {
            val fragment = ChiTietTruyenFragment()
            val args = Bundle()
            args.putParcelable(ARG_PARAM1, truyen)
            args.putString(ARG_PARAM2, email)
            fragment.arguments = args
            return fragment
        }
    }
}
