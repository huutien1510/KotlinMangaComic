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

class DocChapter : AppCompatActivity(), View.OnClickListener {
    private var rcv: RecyclerView? = null
    private var rcv_binhluan: RecyclerView? = null
    private var rcv_adapter: DocChapterAdapter? = null
    private var rcv_binhluanadapter: BinhLuanAdapter? = null
    var id_chapter = 0
    var id_truyen = 0
    var tv_tenchapter: TextView? = null
    var tv_sosaochapter: TextView? = null
    var img_backdoctruyen: ImageView? = null
    var img_pre: ImageView? = null
    var img_next: ImageView? = null
    var bt_danhgia: Button? = null
    var bt_binhluan: Button? = null
    var edt_binhluan: EditText? = null
    var chapterDto: ChapterDto? = null
    var truyenList: List<NoiDungChapterDto>? = null
    var binhLuanTruyen: MutableList<BinhLuanTruyenDto>? = null
    var listtaiKhoanTruyen: List<TaiKhoanDto>? = null
    var listten: List<ChapterDto?>? = null
    var listid: List<Int>? = null
    var idtaikhoan = 0
    var kt = 0
    var sosaochapter = 0.0
    var user = FirebaseAuth.getInstance().currentUser
    var rtb: RatingBar? = null
    var minIdChapter: Int? = null
    var maxIdChapter: Int? = null

    interface MinMaxCallback {
        fun onMinMaxReady(minId: Int, maxId: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.docchapter)
        Anhxa()
        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcv!!.setLayoutManager(linearLayoutManager)
        val intent = intent
        id_chapter = intent.getIntExtra("id_chapter", 0)
        id_truyen = intent.getIntExtra("id_truyen", 0)
        getMinMax(object : MinMaxCallback {
            override fun onMinMaxReady(minId: Int, maxId: Int) {
                minIdChapter = minId
                maxIdChapter = maxId
                // Thiết lập onClickListener sau khi minIdChapter và maxIdChapter đã được cập nhật
                setOnClickListener()
            }
        })
        if (id_chapter != 0) {
            GetTenChapter()
        }
        truyenList = ArrayList()
        rcv_adapter = DocChapterAdapter(truyenList, this@DocChapter)
        rcv!!.setAdapter(rcv_adapter)
        GetNoiDungChapter()
        recyclerViewBinhLuan()
    }

    private fun Anhxa() {
        rcv = findViewById(R.id.rcv_docchapter)
        rcv_binhluan = findViewById(R.id.rcv_binhluan)
        tv_tenchapter = findViewById(R.id.tv_tenchapter)
        img_backdoctruyen = findViewById(R.id.img_backdoctruyen)
        img_next = findViewById(R.id.img_next)
        img_pre = findViewById(R.id.img_pre)
        edt_binhluan = findViewById(R.id.edt_binhluan)
        bt_binhluan = findViewById(R.id.bt_binhluan)
        bt_danhgia = findViewById(R.id.bt_danhgia)
        rtb = findViewById(R.id.rtb)
        tv_sosaochapter = findViewById(R.id.tv_sosaochapter)
    }

    private fun GetTenChapter() {
        APIService.apiService.getTenById(id_chapter)!!.enqueue(object : Callback<List<ChapterDto?>?> {
            override fun onResponse(call: Call<List<ChapterDto?>?>, response: Response<List<ChapterDto?>?>) {
                listten = response.body()
                chapterDto = listten!![0]
                tv_tenchapter!!.text = chapterDto!!.tenchapter
                Log.e("MinIdChapter", minIdChapter.toString())
            }

            override fun onFailure(call: Call<List<ChapterDto?>?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
            }
        })
        APIService.apiService.updateLuotXemChapter(id_chapter)!!.enqueue(object : Callback<Void?> {
            override fun onResponse(call: Call<Void?>, response: Response<Void?>) {}
            override fun onFailure(call: Call<Void?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
            }
        })
    }

    private fun GetNoiDungChapter() {
        APIService.apiService.getNoiDungChapterById(id_chapter)!!.enqueue(object : Callback<List<NoiDungChapterDto>?> {
            override fun onResponse(
                call: Call<List<NoiDungChapterDto>?>,
                response: Response<List<NoiDungChapterDto>?>
            ) {
                truyenList = response.body()
                val categoryAdapter = DocChapterAdapter(truyenList, this@DocChapter)
                rcv!!.setAdapter(categoryAdapter)
            }

            override fun onFailure(call: Call<List<NoiDungChapterDto>?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
            }
        })
    }

    private fun recyclerViewBinhLuan() {
        val linearLayoutManager2 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rcv_binhluan!!.setLayoutManager(linearLayoutManager2)
        binhLuanTruyen = ArrayList()
        rcv_binhluanadapter = BinhLuanAdapter(this@DocChapter, binhLuanTruyen)
        rcv_binhluan!!.setAdapter(rcv_binhluanadapter)
        GetBinhLuanTheoIdChapter()
    }

    /*private void GetBinhLuanTheoIdChapter(){
        APIService.apiService.getBinhLuanTheoIdChapter(id_truyen).enqueue(new Callback<List<BinhLuanTruyenDto>>() {
            @Override
            public void onResponse(Call<List<BinhLuanTruyenDto>> call, Response<List<BinhLuanTruyenDto>> response) {
                binhLuanTruyen = response.body();
                BinhLuanTruyenAdapter binhLuanTruyenAdapter = new BinhLuanTruyenAdapter(DocChapter.this, binhLuanTruyen);
                rcv_binhluan.setAdapter(binhLuanTruyenAdapter);
            }

            @Override
            public void onFailure(Call<List<BinhLuanTruyenDto>> call, Throwable t) {
                Log.e("API_CALL", "Failed to fetch data from API", t);
                Toast.makeText(DocChapter.this, "Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/
    private fun GetBinhLuanTheoIdChapter() {
        APIService.apiService.getBinhLuanTheoIdChapter(id_chapter)!!
            .enqueue(object : Callback<List<BinhLuanTruyenDto>?> {
                override fun onResponse(
                    call: Call<List<BinhLuanTruyenDto>?>,
                    response: Response<List<BinhLuanTruyenDto>?>
                ) {
                    if (response.isSuccessful) {
                        val responseData = response.body()
                        if (responseData != null && !responseData.isEmpty()) {
                            binhLuanTruyen!!.clear()
                            binhLuanTruyen!!.addAll(responseData)
                            rcv_binhluanadapter!!.notifyDataSetChanged()
                        } else {
                            Log.e("API_CALL", "Response body is empty")
                            Toast.makeText(this@DocChapter, "No comments found", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Log.e("API_CALL", "Response not successful: " + response.errorBody())
                        Toast.makeText(this@DocChapter, "Failed to load comments", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<BinhLuanTruyenDto>?>, t: Throwable) {
                    Log.e("API_CALL", "Failed to fetch data from API", t)
                    Toast.makeText(this@DocChapter, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun getMinMax(callback: MinMaxCallback) {
        APIService.apiService.getMinIdChapter(id_truyen)!!.enqueue(object : Callback<Int?> {
            override fun onResponse(call: Call<Int?>, response: Response<Int?>) {
                val minId = response.body()!!
                APIService.apiService.getMaxIdChapter(id_truyen)!!.enqueue(object : Callback<Int?> {
                    override fun onResponse(call: Call<Int?>, response: Response<Int?>) {
                        val maxId = response.body()!!
                        // Gọi callback khi cả minId và maxId đã được cập nhật
                        callback.onMinMaxReady(minId, maxId)
                    }

                    override fun onFailure(call: Call<Int?>, t: Throwable) {
                        Log.e("API_CALL", "Failed to fetch maxId from API", t)
                    }
                })
            }

            override fun onFailure(call: Call<Int?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch minId from API", t)
            }
        })
    }

    override fun onClick(view: View) {
        if (view.id == R.id.img_pre) {
            if (id_chapter == minIdChapter) {
                Toast.makeText(this, "Bạn đang ở Chapter đầu tiên!", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, DocChapter::class.java)
                intent.putExtra("email", user!!.email)
                intent.putExtra("id_chapter", id_chapter - 1)
                intent.putExtra("id_truyen", id_truyen)
                startActivity(intent)
            }
        } else if (view.id == R.id.img_next) {
            if (id_chapter == maxIdChapter) {
                Toast.makeText(this, "Bạn đang ở Chapter cuối", Toast.LENGTH_SHORT).show()
            } else {
                val intent1 = Intent(this, DocChapter::class.java)
                intent1.putExtra("email", user!!.email)
                intent1.putExtra("id_chapter", id_chapter + 1)
                intent1.putExtra("id_truyen", id_truyen)
                startActivity(intent1)
            }
        } else if (view.id == R.id.img_backdoctruyen) {
            val intent2 = Intent(this, CTTruyen::class.java)
            intent2.putExtra("email", user!!.email)
            intent2.putExtra("id_truyen", id_truyen)
            startActivity(intent2)
            finish()
        } else if (view.id == R.id.bt_binhluan) {
            if (edt_binhluan!!.getText().length != 0) {
                if (edt_binhluan!!.getText().length != 0) {
                    //Nhớ thay email
                    APIService.apiService.findByEmail1(user!!.email)!!.enqueue(object : Callback<List<TaiKhoanDto>?> {
                        override fun onResponse(
                            call: Call<List<TaiKhoanDto>?>,
                            response: Response<List<TaiKhoanDto>?>
                        ) {
                            val listtaiKhoanTruyen = response.body()
                            if (listtaiKhoanTruyen != null && !listtaiKhoanTruyen.isEmpty()) {
                                idtaikhoan = listtaiKhoanTruyen[0].id!!
                                val binhLuanDto =
                                    BinhLuanDto(id_chapter, idtaikhoan, edt_binhluan!!.getText().toString() + "")
                                APIService.apiService.themBinhLuan(binhLuanDto)!!
                                    .enqueue(object : Callback<BinhLuanDto?> {
                                        override fun onResponse(
                                            call: Call<BinhLuanDto?>,
                                            response: Response<BinhLuanDto?>
                                        ) {
                                            edt_binhluan!!.setText("")
                                            recyclerViewBinhLuan()
                                        }

                                        override fun onFailure(call: Call<BinhLuanDto?>, t: Throwable) {
                                            Log.e("API_CALL", "Failed to fetch data from API", t)
                                            Toast.makeText(this@DocChapter, "Failure: " + t.message, Toast.LENGTH_SHORT)
                                                .show()
                                        }
                                    })
                            } else {
                                Toast.makeText(this@DocChapter, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<List<TaiKhoanDto>?>, t: Throwable) {
                            Log.e("API_CALL", "Failed to fetch data from API", t)
                            Toast.makeText(this@DocChapter, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
                } else {
                    Toast.makeText(this, "Vui lòng nhập bình luận!", Toast.LENGTH_SHORT).show()
                }
            }
        } else if (view.id == R.id.bt_danhgia) {
            APIService.apiService.findByEmail1(user!!.email)!!.enqueue(object : Callback<List<TaiKhoanDto>?> {
                override fun onResponse(call: Call<List<TaiKhoanDto>?>, response: Response<List<TaiKhoanDto>?>) {
                    val listtaiKhoanTruyen = response.body()
                    if (listtaiKhoanTruyen != null && !listtaiKhoanTruyen.isEmpty()) {
                        idtaikhoan = listtaiKhoanTruyen[0].id!!
                        APIService.apiService.getIDByChapterAndTK(id_chapter, idtaikhoan)!!
                            .enqueue(object : Callback<List<Int>?> {
                                override fun onResponse(call: Call<List<Int>?>, response: Response<List<Int>?>) {
                                    listid = response.body()
                                    if (listid != null && !listid!!.isEmpty()) {
                                        kt = listid!![0]
                                    } else {
                                        Toast.makeText(this@DocChapter, "Sai", Toast.LENGTH_SHORT).show()
                                    }
                                }

                                override fun onFailure(call: Call<List<Int>?>, t: Throwable) {
                                    Log.e("API_CALL", "Failed to fetch data from API", t)
                                    Toast.makeText(this@DocChapter, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
                                }
                            })
                    } else {
                        Toast.makeText(this@DocChapter, "Không tìm thấy tài khoản", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<TaiKhoanDto>?>, t: Throwable) {
                    Log.e("API_CALL", "Failed to fetch data from API", t)
                    Toast.makeText(this@DocChapter, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
                }
            })
            val sosao = rtb!!.rating
            if (kt != 0) {
                APIService.apiService.updateDanhGia(id_chapter, idtaikhoan, sosao.toDouble())!!
                    .enqueue(object : Callback<Void?> {
                        override fun onResponse(call: Call<Void?>, response: Response<Void?>) {}
                        override fun onFailure(call: Call<Void?>, t: Throwable) {
                            Log.e("API_CALL", "Failed to fetch data from API", t)
                            Toast.makeText(this@DocChapter, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
                        }
                    })
                APIService.apiService.getAverageRatingByIdChapter(id_chapter)!!.enqueue(object : Callback<Double?> {
                    override fun onResponse(call: Call<Double?>, response: Response<Double?>) {
                        val sosao = response.body()!!
                        tv_sosaochapter!!.text = "" + sosao
                    }

                    override fun onFailure(call: Call<Double?>, t: Throwable) {
                        Log.e("API_CALL", "Failed to fetch data from API", t)
                        Toast.makeText(this@DocChapter, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            } /*else {
                db.insertDanhGia(id_chapter, taiKhoan.getId(), sosao);
                setData();
            }*/
        }
    }

    private fun setData() {}
    private fun setOnClickListener() {
        img_backdoctruyen!!.setOnClickListener(this)
        img_pre!!.setOnClickListener(this)
        img_next!!.setOnClickListener(this)
        bt_binhluan!!.setOnClickListener(this)
        bt_danhgia!!.setOnClickListener(this)
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<List<ChapterDto?>?>) {
    TODO("Not yet implemented")
}
