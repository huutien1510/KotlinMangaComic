package vn.iotstar.appdoctruyen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.LuotXemApdapter
import vn.iotstar.appdoctruyen.model.TruyenVotes

class BXHLuotXemFragment : Fragment() {

    private var rcv: RecyclerView? = null
    private var mListVotes: List<TruyenVotes>? = null
    private var email: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Dùng cú pháp an toàn để truy xuất giá trị từ arguments
            email = it.getString(ARG_EMAIL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_b_x_h_luot_xem, container, false)
        setupRecyclerView(view)
        callApiGetTruyenView()
        return view
    }

    private fun setupRecyclerView(view: View) {
        rcv = view.findViewById(R.id.rcv_xh_view)
        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rcv?.layoutManager = linearLayoutManager
        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rcv?.addItemDecoration(itemDecoration)
    }

    private fun callApiGetTruyenView() {
        APIService.apiService.viewComics?.enqueue(object : Callback<List<TruyenVotes>?> {
            override fun onResponse(call: Call<List<TruyenVotes>?>, response: Response<List<TruyenVotes>?>) {
                if (response.isSuccessful) {
                    mListVotes = response.body()
                    val adapter = LuotXemApdapter(activity!!, mListVotes, email!!)
                    rcv?.adapter = adapter
                }
            }

            override fun onFailure(call: Call<List<TruyenVotes>?>, t: Throwable) {
                // Handle failure here
            }
        })
    }

    companion object {
        private const val ARG_EMAIL = "email"

        fun newInstance(email: String): BXHLuotXemFragment {
            val fragment = BXHLuotXemFragment()
            val args = Bundle()
            args.putString(ARG_EMAIL, email)
            fragment.arguments = args
            return fragment
        }
    }
}
