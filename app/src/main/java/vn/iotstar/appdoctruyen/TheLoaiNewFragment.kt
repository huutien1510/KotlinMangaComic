package vn.iotstar.appdoctruyen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.TheLoaiAdapter
import vn.iotstar.appdoctruyen.model.PhanLoaiTruyen

class TheLoaiNewFragment : Fragment() {

    private var rcv: RecyclerView? = null
    private var email: String? = null
    private var _theloai: String? = null
    private var mListPL: List<PhanLoaiTruyen>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_theloai_new, container, false)
        Anhxa(view)

        rcv?.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        }

        email = activity?.intent?.getStringExtra("email")
        callApiGetTruyenMoiNhat()

        val theLoaiFragment = activity as? TheLoaiFragment
        theLoaiFragment?.setOnTheLoaiSelectedListener(object : TheLoaiFragment.OnTheLoaiSelectedListener {

            override fun onTheLoaiSelected(theLoai: String) {
                _theloai = theLoai
                if (!_theloai.isNullOrEmpty()) {
                    callApiGetTruyenMoiNhatTheoTheLoai()
                }
            }
        })

        return view
    }

    private fun callApiGetTruyenMoiNhatTheoTheLoai() {
        APIService.apiService.getNewestComicsByTheLoai(_theloai)?.enqueue(object : Callback<List<PhanLoaiTruyen>?> {
            override fun onResponse(call: Call<List<PhanLoaiTruyen>?>, response: Response<List<PhanLoaiTruyen>?>) {
                mListPL = response.body()
                rcv?.adapter = TheLoaiAdapter(requireActivity(), mListPL.orEmpty(), email.orEmpty())
            }

            override fun onFailure(call: Call<List<PhanLoaiTruyen>?>, t: Throwable) {
                Toast.makeText(requireContext(), "Lỗi khi tải dữ liệu theo thể loại", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun callApiGetTruyenMoiNhat() {
        APIService.apiService.newestComics?.enqueue(object : Callback<List<PhanLoaiTruyen>?> {
            override fun onResponse(call: Call<List<PhanLoaiTruyen>?>, response: Response<List<PhanLoaiTruyen>?>) {
                mListPL = response.body()
                rcv?.adapter = TheLoaiAdapter(requireActivity(), mListPL.orEmpty(), email.orEmpty())
            }

            override fun onFailure(call: Call<List<PhanLoaiTruyen>?>, t: Throwable) {
                Toast.makeText(requireContext(), "Lỗi khi tải dữ liệu mới nhất", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun Anhxa(view: View) {
        rcv = view.findViewById(R.id.rcv_theloai_new)
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        fun newInstance(param1: String?, param2: String?): TheLoaiNewFragment {
            val fragment = TheLoaiNewFragment()
            val args = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
            fragment.arguments = args
            return fragment
        }
    }
}

private fun <T> Call<T>?.enqueue(callback: Callback<List<PhanLoaiTruyen>?>) {

}
