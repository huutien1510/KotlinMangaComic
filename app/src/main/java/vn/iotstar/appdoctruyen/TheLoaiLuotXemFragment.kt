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
import vn.iotstar.appdoctruyen.Adapter.TheLoaiVotesAdapter
import vn.iotstar.appdoctruyen.model.TruyenVotes

class TheLoaiLuotXemFragment : Fragment() {
    private var rcv: RecyclerView? = null
    private var email: String? = null
    private var _theloai: String? = null
    private var mListVotes: List<TruyenVotes>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_theloai_vote, container, false)
        Anhxa(view)
        email = requireActivity().intent.getStringExtra("email")

        rcv?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rcv?.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        callApiGetTruyenView()

        val theLoaiFragment = activity as? TheLoaiFragment
        theLoaiFragment?.setOnTheLoaiSelectedListener(object : TheLoaiFragment.OnTheLoaiSelectedListener {
            override fun onTheLoaiSelected(theLoai: String) {
                _theloai = theLoai
                if (!_theloai.isNullOrEmpty()) {
                    allApiGetTruyenViewTheoTheLoai()
                }
            }



        })

        return view
    }

    private fun callApiGetTruyenView() {
        APIService.apiService.viewComics?.enqueue(object : Callback<List<TruyenVotes>?> {
            override fun onResponse(call: Call<List<TruyenVotes>?>, response: Response<List<TruyenVotes>?>) {
                if (response.isSuccessful) {
                    mListVotes = response.body()
                    rcv?.adapter = TheLoaiVotesAdapter(requireActivity(), mListVotes ?: emptyList(), email ?: "")
                }
            }

            override fun onFailure(call: Call<List<TruyenVotes>?>, t: Throwable) {
                Toast.makeText(requireContext(), "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun allApiGetTruyenViewTheoTheLoai() {
        _theloai?.let { theLoai ->
            APIService.apiService.getViewComicsByTheLoai(theLoai)?.enqueue(object : Callback<List<TruyenVotes>?> {
                override fun onResponse(call: Call<List<TruyenVotes>?>, response: Response<List<TruyenVotes>?>) {
                    mListVotes = response.body()
                    rcv?.adapter = TheLoaiVotesAdapter(requireActivity(), mListVotes ?: emptyList(), email ?: "")
                }

                override fun onFailure(call: Call<List<TruyenVotes>?>, t: Throwable) {
                    Toast.makeText(requireContext(), "Lỗi khi tải dữ liệu theo thể loại", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun Anhxa(view: View) {
        rcv = view.findViewById(R.id.rcv_theloai_vote)
    };
}