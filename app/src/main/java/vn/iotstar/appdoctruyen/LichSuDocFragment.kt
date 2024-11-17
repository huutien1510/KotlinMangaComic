package vn.iotstar.appdoctruyen

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.TruyenDaDocAdapter
import vn.iotstar.appdoctruyen.model.LichSuDocTruyenModel
import vn.iotstar.appdoctruyen.model.TaiKhoanDto
import vn.iotstar.appdoctruyen.model.Truyen1

class LichSuDocFragment : Fragment() {
    var dlog: ProgressDialog? = null
    var email: String? = null
    var taiKhoan: TaiKhoanDto? = null
    private var lichSuDocTruyenList: List<LichSuDocTruyenModel>? = null
    private var rcv: RecyclerView? = null
    private var rcv_adapter: TruyenDaDocAdapter? = null

    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mParam1 = it.getString(ARG_PARAM1)
            mParam2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lich_su_doc_truyen, container, false)
        Anhxa(view)
        val user = FirebaseAuth.getInstance().currentUser
        email = user?.email

        val thongTinTaiKhoan = ThongTinTaiKhoan()
        thongTinTaiKhoan.email = email
        thongTinTaiKhoan.gettaikhoan(email)

        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        rcv?.layoutManager = linearLayoutManager
        val item = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        rcv?.addItemDecoration(item)

        dlog = ProgressDialog(context).apply {
            setMessage("Đang xử lý!")
            setCancelable(false)
            show()
        }

        Handler().postDelayed({
            taiKhoan = thongTinTaiKhoan.tk
            recyclerViewTruyenDaDoc()
            dlog?.dismiss()
        }, 5000)

        return view
    }

    private fun recyclerViewTruyenDaDoc() {
        if (taiKhoan != null) {
            GetTruyenDaDoc()
        }
    }

    private fun GetTruyenDaDoc() {
        taiKhoan?.id?.let { id ->
            APIService.apiService.getListTruyenDaDoc(id)!!.enqueue(object : Callback<List<LichSuDocTruyenModel>?> {
                override fun onResponse(
                    call: Call<List<LichSuDocTruyenModel>?>,
                    response: Response<List<LichSuDocTruyenModel>?>
                ) {
                    lichSuDocTruyenList = response.body()
                    rcv_adapter = TruyenDaDocAdapter(requireActivity(), lichSuDocTruyenList, taiKhoan!!)
                    rcv?.adapter = rcv_adapter
                }

                override fun onFailure(call: Call<List<LichSuDocTruyenModel>?>, t: Throwable) {
                    Log.e("API_CALL", "Failed to fetch data from API", t)
                    Toast.makeText(context, "loihi: " + t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun Anhxa(view: View) {
        rcv = view.findViewById(R.id.rcv_truyendadoc)
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: String?, param2: String?): LichSuDocFragment {
            val fragment = LichSuDocFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
