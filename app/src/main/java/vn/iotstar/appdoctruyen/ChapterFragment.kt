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
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.ChapterAdapter
import vn.iotstar.appdoctruyen.model.ChapterDto

class ChapterFragment : Fragment() {
    private var user = FirebaseAuth.getInstance().currentUser
    private var chapter: ChapterDto? = null
    private var email: String? = null
    private var tvChapter: TextView? = null
    private var tvNgayDang: TextView? = null
    private var tvLuotXem: TextView? = null
    private var rcv: RecyclerView? = null
    private var rcvAdapter: ChapterAdapter? = null
    private var idTruyen = 0
    private var chapterList: List<ChapterDto>? = null

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
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_chapter, container, false)
        initViews(rootView)
        setupRecyclerView()
        fetchChapterData()
        return rootView
    }

    private fun initViews(view: View) {
        tvChapter = view.findViewById(R.id.tv_chapter)
        tvNgayDang = view.findViewById(R.id.tv_ngaydang)
        tvLuotXem = view.findViewById(R.id.tv_luotxem)
        rcv = view.findViewById(R.id.rcv_chapter)

        val intent = requireActivity().intent
        idTruyen = intent.getIntExtra("id_truyen", 0)
    }

    private fun setupRecyclerView() {
        rcv?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun fetchChapterData() {
        APIService.apiService.getChapterById(idTruyen)?.enqueue(object : Callback<List<ChapterDto>?> {
            override fun onResponse(call: Call<List<ChapterDto>?>, response: Response<List<ChapterDto>?>) {
                if (response.isSuccessful) {
                    chapterList = response.body()
                    rcvAdapter = ChapterAdapter(requireContext(), chapterList ?: emptyList(), user?.email ?: "")
                    rcv?.adapter = rcvAdapter
                }
            }

            override fun onFailure(call: Call<List<ChapterDto>?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
                Toast.makeText(requireContext(), "Failure: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: String?, param2: String?): ChapterFragment {
            return ChapterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        }
    }
}
