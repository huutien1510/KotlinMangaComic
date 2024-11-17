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
import vn.iotstar.appdoctruyen.Adapter.TheLoaiVotesAdapter
import vn.iotstar.appdoctruyen.model.TruyenVotes

class TheLoaiVoteFragment : Fragment() {

    private var rcv: RecyclerView? = null
    private var email: String? = null
    private var _theloai: String? = null
    private var mListVotes: List<TruyenVotes>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // Arguments initialization if needed
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_theloai_vote, container, false)
        Anhxa(view)

        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rcv?.layoutManager = linearLayoutManager
        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rcv?.addItemDecoration(itemDecoration)

        val intent = activity?.intent
        email = intent?.getStringExtra("email")

        callApiGetTruyenVotes()

        // Set OnTheLoaiSelectedListener using Object approach
        val theLoaiFragment = activity as? TheLoaiFragment
        theLoaiFragment?.setOnTheLoaiSelectedListener(object : TheLoaiFragment.OnTheLoaiSelectedListener {

            override fun onTheLoaiSelected(theLoai: String) {
                _theloai = theLoai
                if (!theLoai.isNullOrEmpty()) {
                    allApiGetTruyenVotesTheoTheLoai()
                }
            }
        })

        return view
    }

    private fun allApiGetTruyenVotesTheoTheLoai() {
        APIService.apiService.getVoteComicsByTheLoai(_theloai)?.enqueue(object : Callback<List<TruyenVotes>?> {
            override fun onResponse(call: Call<List<TruyenVotes>?>, response: Response<List<TruyenVotes>?>) {
                mListVotes = response.body()
                val theLoaiAdapter = TheLoaiVotesAdapter(requireActivity(), mListVotes.orEmpty(), email.orEmpty())
                rcv?.adapter = theLoaiAdapter
            }

            override fun onFailure(call: Call<List<TruyenVotes>?>, t: Throwable) {
                // Handle error
            }
        })
    }

    private fun callApiGetTruyenVotes() {
        APIService.apiService.voteComics?.enqueue(object : Callback<List<TruyenVotes>?> {
            override fun onResponse(call: Call<List<TruyenVotes>?>, response: Response<List<TruyenVotes>?>) {
                if (response.isSuccessful) {
                    mListVotes = response.body()
                    val theLoaiAdapter = TheLoaiVotesAdapter(requireActivity(), mListVotes.orEmpty(), email.orEmpty())
                    rcv?.adapter = theLoaiAdapter
                }
            }

            override fun onFailure(call: Call<List<TruyenVotes>?>, t: Throwable) {
                // Handle error
            }
        })
    }

    private fun Anhxa(view: View) {
        rcv = view.findViewById(R.id.rcv_theloai_vote)
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: String?, param2: String?): TheLoaiVoteFragment {
            return TheLoaiVoteFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        }
    }
}
