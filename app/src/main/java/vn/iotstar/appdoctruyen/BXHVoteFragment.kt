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
import vn.iotstar.appdoctruyen.Adapter.VotesAdapter
import vn.iotstar.appdoctruyen.model.TruyenVotes

class BXHVoteFragment : Fragment() {
    private var rcv: RecyclerView? = null
    private var mListVotes: List<TruyenVotes>? = null
    private var email: String? = null

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
        val view = inflater.inflate(R.layout.fragment_b_x_h_vote, container, false)
        Anhxa(view)
        val intent = requireActivity().intent
        email = intent.getStringExtra("email")

        val linearLayoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rcv?.layoutManager = linearLayoutManager
        val itemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        rcv?.addItemDecoration(itemDecoration)

        mListVotes = ArrayList()
        callApiGetTruyenVotes()
        return view
    }

    private fun callApiGetTruyenVotes() {
        APIService.apiService.voteComics?.enqueue(object : Callback<List<TruyenVotes>?> {
            override fun onResponse(call: Call<List<TruyenVotes>?>, response: Response<List<TruyenVotes>?>) {
                if (response.isSuccessful) {
                    mListVotes = response.body()
                    val votesAdapter = VotesAdapter(activity!!, mListVotes, email!!)
                    rcv?.adapter = votesAdapter
                }
            }

            override fun onFailure(call: Call<List<TruyenVotes>?>, t: Throwable) {
                // Handle failure here
            }
        })
    }

    private fun Anhxa(view: View) {
        rcv = view.findViewById(R.id.rcv_xh_vote)
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: String?, param2: String?): BXHVoteFragment {
            val fragment = BXHVoteFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
