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
    private var idTaikhoan = 0
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
        loadUserRating()
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

        // Gắn sự kiện click
        imgBack.setOnClickListener(this)
        imgPre.setOnClickListener(this)
        imgNext.setOnClickListener(this)
        btBinhLuan.setOnClickListener(this)

        // Gắn sự kiện cho nút "Đánh giá"

        btDanhGia.setOnClickListener {
            val soSao = rtb.rating.toDouble()
            postDanhGia(soSao)
        }


        rtb.setOnRatingBarChangeListener { _, rating, _ ->
            if (rating == 0f) {
                tvSoSaoChapter.text = "Chọn số sao để đánh giá"
            } else {
                tvSoSaoChapter.text = String.format("%.1f/5", rating)
            }
        }

    }

    private fun postDanhGia(soSao: Double) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null || currentUser.email == null) {
            showToast("Không tìm thấy tài khoản! Vui lòng đăng nhập.")
            return
        }

        val email = currentUser.email
        Log.d("DEBUG", "Email của người dùng: $email")

        // Gọi API để lấy idTaiKhoan
        APIService.apiService.findIdTaiKhoan(email!!)?.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val idTaiKhoan = response.body()
                    if (idTaiKhoan != null) {
                        Log.d("DEBUG", "idTaiKhoan: $idTaiKhoan")

                        // Tạo đối tượng DanhGiaRequest
                        val danhGiaRequest = DanhGiaRequest(
                            idchapter = idChapter, idtaikhoan = idTaiKhoan, sosao = soSao
                        )

                        // Gọi API addOrUpdateDanhGia
                        APIService.apiService.addOrUpdateDanhGia(danhGiaRequest)
                            ?.enqueue(object : Callback<Map<String, Any>> {
                                override fun onResponse(
                                    call: Call<Map<String, Any>>,
                                    response: Response<Map<String, Any>>
                                ) {
                                    if (response.isSuccessful) {
                                        val responseBody = response.body()
                                        if (responseBody != null) {
                                            Log.d("DEBUG", "Response: $responseBody")
                                            showToast("Đánh giá thành công!")
                                            // Cập nhật số sao ngay lập tức
                                            rtb.rating = soSao.toFloat()
                                            tvSoSaoChapter.text = String.format("%.1f/5", soSao)
                                        } else {
                                            Log.e("DEBUG", "Response body is null")
                                            showToast("Lỗi: Phản hồi rỗng từ server!")
                                        }
                                    } else {
                                        Log.e(
                                            "DEBUG",
                                            "Response error: ${response.code()} - ${response.message()}"
                                        )
                                        showToast("Lỗi: ${response.message()}")
                                    }
                                }

                                override fun onFailure(call: Call<Map<String, Any>>, t: Throwable) {
                                    Log.e("DEBUG", "Kết nối thất bại: ${t.message}")
                                    showToast("Không thể kết nối đến server!")
                                }
                            })

                    } else {
                        showToast("Không tìm thấy ID tài khoản!")
                        Log.e("DEBUG", "ID tài khoản null")
                    }
                } else {
                    showToast("Lỗi: Không thể lấy ID tài khoản!")
                    Log.e("DEBUG", "API lỗi: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                showToast("Lỗi kết nối đến server!")
                Log.e("DEBUG", "Lỗi kết nối: ${t.message}")
            }
        })
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

    private fun loadUserRating() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null || currentUser.email == null) {
            Log.e("DEBUG", "Không tìm thấy tài khoản!")
            return
        }

        val email = currentUser.email
        APIService.apiService.findIdTaiKhoan(email!!)?.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                val idTaiKhoan = response.body()
                if (idTaiKhoan != null) {
                    // Gọi API lấy trạng thái đánh giá
                    APIService.apiService.getDanhGiaByUserAndChapter(idChapter, idTaiKhoan)
                        ?.enqueue(object : Callback<DanhGiaRequest> {
                            override fun onResponse(
                                call: Call<DanhGiaRequest>, response: Response<DanhGiaRequest>
                            ) {
                                if (response.isSuccessful) {
                                    val danhGia = response.body()
                                    if (danhGia != null) {
                                        Log.d("DEBUG", "Số sao đã đánh giá: ${danhGia.sosao}")
                                        rtb.rating = danhGia.sosao.toFloat() // Cập nhật RatingBar
                                    }
                                } else {
                                    Log.d("DEBUG", "Chưa có đánh giá cho chapter này.")
                                }
                            }

                            override fun onFailure(call: Call<DanhGiaRequest>, t: Throwable) {
                                Log.e("DEBUG", "Lỗi kết nối đến server: ${t.message}")
                            }
                        })
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("DEBUG", "Lỗi kết nối để lấy ID tài khoản: ${t.message}")
            }
        })
    }


    private fun loadChapterDetails() {
        if (idChapter == 0) return

        // Gọi API để lấy tiêu đề chapter
        APIService.apiService.getTenById(idChapter)?.enqueue(object : Callback<List<ChapterDto>> {
            override fun onResponse(
                call: Call<List<ChapterDto>>, response: Response<List<ChapterDto>>
            ) {
                response.body()?.let {
                    if (it.isNotEmpty()) {
                        tvTenChapter.text = it[0].tenchapter
                    } else {
                        showToast("Không tìm thấy tiêu đề chương!")
                    }
                } ?: run {
                    showToast("Failed to load chapter details: No response body")
                }
            }

            override fun onFailure(call: Call<List<ChapterDto>>, t: Throwable) {
                showToast("Failed to load chapter details")
                Log.e("API_CALL", "Failed to load chapter details", t)
            }
        })

        // Gọi API để cập nhật lượt xem chapter
        APIService.apiService.updateLuotXemChapter(idChapter)?.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("API_CALL", "Successfully updated view count")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("API_CALL", "Failed to update view count", t)
            }
        })

        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser == null || currentUser.email == null) {
            showToast("Không tìm thấy tài khoản! Vui lòng đăng nhập.")
            return
        }

        val email = currentUser.email
        APIService.apiService.findIdTaiKhoan(email!!)?.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                val idTaiKhoan = response.body()
                if (idTaiKhoan != null) {
                    // Tạo đối tượng lichSuDocTruyenModel để gửi đi
                    val lichSuDocTruyenModel = LichSuDocTruyenModel(idTaiKhoan, idChapter)
                    APIService.apiService.addLichSuDocTruyen(lichSuDocTruyenModel)?.enqueue(object :
                        Callback<Void> { // Sử dụng Callback<Void> vì không có dữ liệu trả về
                        override fun onResponse(
                            call: Call<Void>, response: Response<Void>
                        ) {
                            if (response.isSuccessful) {
                                // Nếu thành công với mã 200
                                Log.d("API", "Lịch sử đọc truyện đã được lưu thành công.")
                            } else {
                                // Nếu có lỗi từ server (ví dụ mã lỗi không phải 200)
                                Log.e(
                                    "API", "Không thể lưu lịch sử đọc truyện: ${response.message()}"
                                )
                            }
                        }


                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            // Xử lý lỗi kết nối hoặc các lỗi khác
                            Log.e("API", "Lỗi kết nối: ${t.message}")
                        }
                    })
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("DEBUG", "Lỗi kết nối để lấy ID tài khoản: ${t.message}")
            }
        })


        // Gọi API để tải nội dung chapter
        APIService.apiService.getNoiDungChapterById(idChapter)
            ?.enqueue(object : Callback<List<NoiDungChapterDto>> {
                override fun onResponse(
                    call: Call<List<NoiDungChapterDto>>, response: Response<List<NoiDungChapterDto>>
                ) {
                    response.body()?.let { noidungList ->
                        if (noidungList.isNotEmpty()) {
                            truyenList.clear()
                            truyenList.addAll(noidungList)
                            rcvAdapter?.notifyDataSetChanged()
                        } else {
                            showToast("Nội dung chương không có!")
                        }
                    } ?: run {
                        showToast("Failed to load content: No response body")
                    }
                }

                override fun onFailure(
                    call: Call<List<NoiDungChapterDto>>, t: Throwable
                ) {
                    showToast("Failed to load content")
                    Log.e("API_CALL", "Failed to load chapter content", t)
                }
            })

        // Gọi API để lấy số sao trung bình của chapter
        APIService.apiService.getAverageRatingByIdChapter(idChapter)
            ?.enqueue(object : Callback<Double> {
                override fun onResponse(
                    call: Call<Double>, response: Response<Double>
                ) {
                    if (response.isSuccessful) {
                        // Xử lý phản hồi từ API
                        val averageRating =
                            response.body() ?: 0.0 // Nếu body null, gán giá trị mặc định 0.0
                        tvSoSaoChapter.text = String.format("%.1f/5", averageRating)
                        Log.d("DEBUG", "Average Rating Loaded: $averageRating")
                    } else {
                        // API trả về lỗi HTTP
                        tvSoSaoChapter.text = "xx"
                        Log.e(
                            "API_CALL", "Response error: ${response.code()} - ${response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<Double>, t: Throwable) {
                    // Lỗi kết nối hoặc Gson không parse được
                    tvSoSaoChapter.text = "yy"
                    Log.e("API_CALL", "Failed to load average rating", t)
                    showToast("Failed to load average rating")
                }
            })


    }


    private fun loadComments() {
        APIService.apiService.getBinhLuanTheoIdChapter(idChapter)
            ?.enqueue(object : Callback<List<BinhLuanTruyenDto>> {
                override fun onResponse(
                    call: Call<List<BinhLuanTruyenDto>>, response: Response<List<BinhLuanTruyenDto>>
                ) {
                    response.body()?.let {
                        binhLuanTruyen.clear()
                        binhLuanTruyen.addAll(it)
                        rcvBinhLuanAdapter?.notifyDataSetChanged()
                    }
                }

                override fun onFailure(
                    call: Call<List<BinhLuanTruyenDto>>, t: Throwable
                ) {
                    showToast("Failed to load comments")
                }
            })
    }

    private fun fetchMinMaxChapterIds() {
        APIService.apiService.getMinIdChapter(idTruyen)?.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                response.body()?.let { minId ->
                    minIdChapter = minId
                    APIService.apiService.getMaxIdChapter(idTruyen)
                        ?.enqueue(object : Callback<Int> {
                            override fun onResponse(
                                call: Call<Int>, response: Response<Int>
                            ) {
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
        if (user == null) {
            showToast("Bạn cần đăng nhập để bình luận!")
            return
        }
        user?.email?.let { email ->
            APIService.apiService.findByEmail1(email)
                ?.enqueue(object : Callback<List<TaiKhoanDto>> {
                    override fun onResponse(
                        call: Call<List<TaiKhoanDto>>, response: Response<List<TaiKhoanDto>>
                    ) {
                        val account = response.body()?.firstOrNull()
                        account?.id?.let { accountId ->
                            val binhLuanDto = BinhLuanDto(idChapter, accountId, comment)
                            APIService.apiService.themBinhLuan(binhLuanDto)
                                ?.enqueue(object : Callback<BinhLuanDto> {
                                    override fun onResponse(
                                        call: Call<BinhLuanDto>, response: Response<BinhLuanDto>
                                    ) {
                                        showToast("Bình luận thành công!")
                                        edtBinhLuan.setText("")
                                        loadComments()
                                    }

                                    override fun onFailure(
                                        call: Call<BinhLuanDto>, t: Throwable
                                    ) {
                                        showToast("Thêm bình luận thất bại!")
                                    }
                                })
                        }
                    }

                    override fun onFailure(
                        call: Call<List<TaiKhoanDto>>, t: Throwable
                    ) {
                        showToast("Lỗi tìm tài khoản!")
                    }
                })
        }
    }

    private fun navigateToActivity(
        activityClass: Class<*>, chapterId: Int? = null
    ) {
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
