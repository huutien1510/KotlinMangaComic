package vn.iotstar.appdoctruyen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.BinhLuanAdapter
import vn.iotstar.appdoctruyen.Adapter.DocChapterAdapter
import vn.iotstar.appdoctruyen.model.*
import kotlin.math.log

class DocChapter : AppCompatActivity(), View.OnClickListener {
    private lateinit var rcv: RecyclerView
    private lateinit var rcvBinhLuan: RecyclerView
    private lateinit var tvTenChapter: TextView
    private lateinit var imgBack: ImageView
    private lateinit var imgPre: ImageView
    private lateinit var imgNext: ImageView
    private lateinit var btDanhGia: Button
    private lateinit var btBinhLuan: Button
    private lateinit var edtBinhLuan: EditText
    private lateinit var rtb: RatingBar
    private lateinit var tvSoSaoChapter: TextView

    private var rcvAdapter: DocChapterAdapter? = null
    private var rcvBinhLuanAdapter: BinhLuanAdapter? = null

    private var idChapter = 0
    private var idTruyen = 0
    private var minIdChapter = 0
    private var maxIdChapter = 0
    private var user = FirebaseAuth.getInstance().currentUser
    private val binhLuanTruyen = mutableListOf<BinhLuanTruyenDto>()
    private val truyenList = mutableListOf<NoiDungChapterDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.docchapter)
        initViews()
        setupRecyclerViews()
        fetchIntentData()
        loadChapterDetails()
        loadComments()
        fetchMinMaxChapterIds()
    }

    private fun initViews() {
        rcv = findViewById(R.id.rcv_docchapter)
        rcvBinhLuan = findViewById(R.id.rcv_binhluan)
        tvTenChapter = findViewById(R.id.tv_tenchapter)
        imgBack = findViewById(R.id.img_backdoctruyen)
        imgPre = findViewById(R.id.img_pre)
        imgNext = findViewById(R.id.img_next)
        edtBinhLuan = findViewById(R.id.edt_binhluan)
        btBinhLuan = findViewById(R.id.bt_binhluan)
        btDanhGia = findViewById(R.id.bt_danhgia)
        rtb = findViewById(R.id.rtb)
        tvSoSaoChapter = findViewById(R.id.tv_sosaochapter)

        imgBack.setOnClickListener(this)
        imgPre.setOnClickListener(this)
        imgNext.setOnClickListener(this)
        btBinhLuan.setOnClickListener(this)
    }

    private fun setupRecyclerViews() {
        rcv.layoutManager = LinearLayoutManager(this)
        rcvAdapter = DocChapterAdapter(truyenList, this)
        rcv.adapter = rcvAdapter

        rcvBinhLuan.layoutManager = LinearLayoutManager(this)
        rcvBinhLuanAdapter = BinhLuanAdapter(this, binhLuanTruyen)
        rcvBinhLuan.adapter = rcvBinhLuanAdapter
    }

    private fun fetchIntentData() {
        idChapter = intent.getIntExtra("id_chapter", 0)
        idTruyen = intent.getIntExtra("id_truyen", 0)
    }

    private fun loadChapterDetails() {
        if (idChapter == 0) return
        APIService.apiService.getTenById(idChapter)?.enqueue(object : Callback<List<ChapterDto>> {
            override fun onResponse(call: Call<List<ChapterDto>>, response: Response<List<ChapterDto>>) {
                response.body()?.let {
                    if (it.isNotEmpty()) {
                        tvTenChapter.text = it[0].tenchapter
                    }
                }
            }

            override fun onFailure(call: Call<List<ChapterDto>>, t: Throwable) {
                showToast("Failed to load chapter details")
            }
        })


        APIService.apiService.updateLuotXemChapter(idChapter)?.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                // Successfully updated views
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("API_CALL", "Failed to update view count", t)
            }
        })

        APIService.apiService.getNoiDungChapterById(idChapter)?.enqueue(object : Callback<List<NoiDungChapterDto>> {
            override fun onResponse(call: Call<List<NoiDungChapterDto>>, response: Response<List<NoiDungChapterDto>>) {
                response.body()?.let {
                    truyenList.clear()
                    truyenList.addAll(it)
                    rcvAdapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<NoiDungChapterDto>>, t: Throwable) {
                showToast("Failed to load content")
            }
        })
    }

    private fun loadComments() {
        APIService.apiService.getBinhLuanTheoIdChapter(idChapter)?.enqueue(object : Callback<List<BinhLuanTruyenDto>> {
            override fun onResponse(call: Call<List<BinhLuanTruyenDto>>, response: Response<List<BinhLuanTruyenDto>>) {
                response.body()?.let {
                    binhLuanTruyen.clear()
                    binhLuanTruyen.addAll(it)
                    rcvBinhLuanAdapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<BinhLuanTruyenDto>>, t: Throwable) {
                showToast("Failed to load comments")
            }
        })
    }

    private fun fetchMinMaxChapterIds() {
        APIService.apiService.getMinIdChapter(idTruyen)?.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                response.body()?.let { minId ->
                    minIdChapter = minId
                    APIService.apiService.getMaxIdChapter(idTruyen)?.enqueue(object : Callback<Int> {
                        override fun onResponse(call: Call<Int>, response: Response<Int>) {
                            response.body()?.let {
                                maxIdChapter = it
                            }
                        }
                        override fun onFailure(call: Call<Int>, t: Throwable) {
                            showToast("Failed to fetch max chapter ID")
                        }
                    })
                }
            }
            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch min chapter ID", t)
            }
        })
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.img_backdoctruyen -> navigateToActivity(CTTruyen::class.java)
            R.id.img_pre -> handleChapterNavigation(-1)
            R.id.img_next -> handleChapterNavigation(1)
            R.id.bt_binhluan -> postComment()
        }
    }

    private fun handleChapterNavigation(direction: Int) {
        val newIdChapter = idChapter + direction
        println("New ID Chapter: $newIdChapter")
        when {
            newIdChapter < minIdChapter -> showToast("Bạn đang ở Chapter đầu tiên!")
            newIdChapter > maxIdChapter -> showToast("Bạn đang ở Chapter cuối!")
            else -> navigateToActivity(DocChapter::class.java, newIdChapter)
        }
    }

    private fun postComment() {
        val comment = edtBinhLuan.text.toString().trim()
        if (comment.isEmpty()) {
            showToast("Bình luận không được để trống!")
            return
        }
        user?.email?.let { email ->
            APIService.apiService.findByEmail1(email)?.enqueue(object : Callback<List<TaiKhoanDto>> {
                override fun onResponse(call: Call<List<TaiKhoanDto>>, response: Response<List<TaiKhoanDto>>) {
                    val account = response.body()?.firstOrNull()
                    account?.id?.let { accountId ->
                        val binhLuanDto = BinhLuanDto(idChapter, accountId, comment)
                        APIService.apiService.themBinhLuan(binhLuanDto)?.enqueue(object : Callback<BinhLuanDto> {
                            override fun onResponse(call: Call<BinhLuanDto>, response: Response<BinhLuanDto>) {
                                showToast("Bình luận thành công!")
                                edtBinhLuan.setText("")
                                loadComments()
                            }

                            override fun onFailure(call: Call<BinhLuanDto>, t: Throwable) {
                                showToast("Thêm bình luận thất bại!")
                            }
                        })
                    }
                }

                override fun onFailure(call: Call<List<TaiKhoanDto>>, t: Throwable) {
                    showToast("Lỗi tìm tài khoản!")
                }
            })
        }
    }

    private fun navigateToActivity(activityClass: Class<*>, chapterId: Int? = null) {
        val intent = Intent(this, activityClass)
        chapterId?.let { intent.putExtra("id_chapter", it) }
        intent.putExtra("id_truyen", idTruyen)
        startActivity(intent)
        finish()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
