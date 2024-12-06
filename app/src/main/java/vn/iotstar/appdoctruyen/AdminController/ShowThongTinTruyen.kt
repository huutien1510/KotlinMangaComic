package vn.iotstar.appdoctruyen.AdminController

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.AdminAdapter.QuanLyChapterAdapter
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.Chapter
import vn.iotstar.appdoctruyen.model.ChapterAdmin
import vn.iotstar.appdoctruyen.model.truyen
import java.text.Normalizer
import java.time.LocalDate
import java.util.*
import java.util.regex.Pattern

class ShowThongTinTruyen : AppCompatActivity(), View.OnClickListener {
    var img_truyen: ImageView? = null
    var img_new: ImageView? = null
    var tv_id: TextView? = null
    var edt_tacgia: EditText? = null
    var edt_mota: EditText? = null
    var edt_theloai: EditText? = null
    var edt_linkanh: EditText? = null
    var edt_trangthai: EditText? = null
    var edt_tentruyen: EditText? = null
    var edt_tenchapter_newchapter: EditText? = null
    var bt_chinhsua: Button? = null
    var bt_them: Button? = null
    var bt_huy: Button? = null
    var bt_xacnhantruyen: Button? = null
    var bt_huychinhsuatruyen: Button? = null
    var truyen1: truyen? = null
    var id = 0
    var cv_themchapter: CardView? = null
    private var rcv: RecyclerView? = null
    private val adapter: QuanLyChapterAdapter? = null
    private var truyenList: List<truyen>? = null
    private var chapterList: List<ChapterAdmin>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_thong_tin_truyen)
        AnhXa()
        val intent = intent
        id = intent.getIntExtra("id_truyen", 1)

        val linearLayoutManager = LinearLayoutManager(this@ShowThongTinTruyen, RecyclerView.VERTICAL, false)
        rcv!!.setLayoutManager(linearLayoutManager)
        val itemDecoration = DividerItemDecoration(this@ShowThongTinTruyen, DividerItemDecoration.VERTICAL)
        rcv!!.addItemDecoration(itemDecoration)
        chapterList = ArrayList()
        setData()
        setEnable(0)

        showChapter()
        setOnClickListener()
    }
    private fun setData() {
        // Gọi API để lấy thông tin truyện
        APIService.apiService.getTruyenById(id)?.enqueue(object : Callback<List<truyen>?> {
            override fun onResponse(call: Call<List<truyen>?>, response: Response<List<truyen>?>) {
                // Kiểm tra nếu phản hồi thành công
                if (response.isSuccessful) {
                    val truyenList = response.body()
                    if (truyenList != null && truyenList.isNotEmpty()) {
                        // Log để debug dữ liệu nhận được
                        Log.d("DEBUG", "Truyện nhận được: ${truyenList[0]}")

                        val truyen = truyenList[0]

                        // Cập nhật giao diện
                        edt_tentruyen!!.setText(truyen.tentruyen)
                        Glide.with(this@ShowThongTinTruyen)
                            .load(truyen.linkanh)
                            .into(img_truyen!!)

                        edt_tacgia!!.setText(truyen.tacgia)
                        edt_mota!!.setText(truyen.mota)
                        edt_theloai!!.setText(truyen.theloai)
                        edt_trangthai!!.setText(truyen.trangthai.toString())
                        tv_id!!.text = truyen.id.toString()
                        edt_linkanh!!.setText(truyen.linkanh)
                    } else {
                        // Dữ liệu rỗng hoặc không hợp lệ
                        Toast.makeText(this@ShowThongTinTruyen, "Dữ liệu trả về trống", Toast.LENGTH_SHORT).show()
                        Log.e("DEBUG", "Dữ liệu trả về từ API rỗng hoặc không có.")
                    }
                } else {
                    // Phản hồi không thành công (404, 500, v.v.)
                    Toast.makeText(this@ShowThongTinTruyen, "Lỗi API: ${response.code()}", Toast.LENGTH_SHORT).show()
                    Log.e("DEBUG", "Lỗi API: ${response.code()}, ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<truyen>?>, throwable: Throwable) {
                // Xử lý lỗi kết nối
                Toast.makeText(this@ShowThongTinTruyen, "Lỗi kết nối: ${throwable.message}", Toast.LENGTH_SHORT).show()
                Log.e("DEBUG", "Lỗi kết nối API: ${throwable.message}", throwable)
            }
        })
    }



    private fun setOnClickListener() {
        img_new!!.setOnClickListener(this)
        bt_them!!.setOnClickListener(this)
        bt_huy!!.setOnClickListener(this)
        bt_chinhsua!!.setOnClickListener(this)
        bt_huychinhsuatruyen!!.setOnClickListener(this)
        bt_xacnhantruyen!!.setOnClickListener(this)
    }

    private fun showChapter() {
        APIService.apiService.getChapterByIdAdmin(id)?.enqueue(object : Callback<List<ChapterAdmin>?> {
            override fun onResponse(call: Call<List<ChapterAdmin>?>, response: Response<List<ChapterAdmin>?>) {
                chapterList = response.body()
                val adapter = QuanLyChapterAdapter(this@ShowThongTinTruyen, chapterList)
                rcv!!.setAdapter(adapter)
            }

            override fun onFailure(call: Call<List<ChapterAdmin>?>, throwable: Throwable) {
                //Toast.makeText(ShowThongTinTruyen.this, "Không láy dc chapter", Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Không láy dc chapter. Lỗi: " + throwable.message, throwable)
                Toast.makeText(
                    this@ShowThongTinTruyen,
                    "Không láy dc chapter. Lỗi: " + throwable.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }




    private fun setEnable(i: Int) {
        if (i == 1) {
            edt_linkanh!!.setEnabled(true)
            edt_trangthai!!.setEnabled(true)
            edt_tentruyen!!.setEnabled(true)
            edt_mota!!.setEnabled(true)
            edt_theloai!!.setEnabled(true)
            edt_tacgia!!.setEnabled(true)
            bt_chinhsua!!.visibility = View.GONE
            bt_huychinhsuatruyen!!.visibility = View.VISIBLE
            bt_xacnhantruyen!!.visibility = View.VISIBLE
        } else {
            edt_linkanh!!.setEnabled(false)
            edt_trangthai!!.setEnabled(false)
            edt_tentruyen!!.setEnabled(false)
            edt_mota!!.setEnabled(false)
            edt_theloai!!.setEnabled(false)
            edt_tacgia!!.setEnabled(false)
            bt_chinhsua!!.visibility = View.VISIBLE
            bt_huychinhsuatruyen!!.visibility = View.GONE
            bt_xacnhantruyen!!.visibility = View.GONE
        }
    }

    private fun AnhXa() {
        img_new = findViewById(R.id.img_newchapter)
        img_truyen = findViewById(R.id.img_qlt)
        bt_chinhsua = findViewById(R.id.bt_chinhsuatruyen)
        bt_huy = findViewById(R.id.bt_huy_newchapter)
        bt_them = findViewById(R.id.bt_them_newchapter)
        tv_id = findViewById(R.id.tv_qlt_id)
        edt_tacgia = findViewById(R.id.edt_qlt_tacgia)
        edt_mota = findViewById(R.id.edt_qlt_mota)
        edt_theloai = findViewById(R.id.edt_qlt_theloai)
        edt_linkanh = findViewById(R.id.edt_qlt_linkanh)
        edt_trangthai = findViewById(R.id.edt_qlt_trangthai)
        edt_tentruyen = findViewById(R.id.edt_qlt_tentruyen)
        cv_themchapter = findViewById(R.id.cv_themchapter)
        bt_xacnhantruyen = findViewById(R.id.bt_xacnhantruyen)
        bt_huychinhsuatruyen = findViewById(R.id.bt_huychinhsuatruyen)
        edt_tenchapter_newchapter = findViewById(R.id.edt_tenchapter_newchapter)
        rcv = findViewById(R.id.rcv_quanlychapter)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.img_newchapter -> {
                cv_themchapter!!.visibility = View.VISIBLE
            }
            R.id.bt_chinhsuatruyen -> {
                setEnable(1)
            }
            R.id.bt_huychinhsuatruyen -> {
                setEnable(0)
            }
            R.id.bt_xacnhantruyen -> {
                val id = tv_id!!.text.toString().toInt()
                val tentruyen = edt_tentruyen!!.text.toString()
                val tacgia = edt_tacgia!!.text.toString()
                val mota = edt_mota!!.text.toString()
                val theloai = edt_theloai!!.text.toString()
                val linkanh = edt_linkanh!!.text.toString()
                val trangthai = edt_trangthai!!.text.toString()
                val key_search = removeAccent(tentruyen).trim { it <= ' ' }

                if (tentruyen.isNotEmpty() && tacgia.isNotEmpty() && theloai.isNotEmpty() && mota.isNotEmpty() && linkanh.isNotEmpty()) {
                    truyen1 = truyen(tentruyen, tacgia, mota, theloai, linkanh, trangthai.toInt(), key_search)
                    APIService.apiService.updateTruyen(truyen1!!, id)?.enqueue(object : Callback<truyen?> {
                        override fun onResponse(call: Call<truyen?>, response: Response<truyen?>) {
                            if (response.isSuccessful) {
                                val result = response.body()
                                if (result != null) {
                                    Toast.makeText(this@ShowThongTinTruyen, "Cập nhật thành công", Toast.LENGTH_SHORT).show()
                                    reload()
                                } else {
                                    Toast.makeText(this@ShowThongTinTruyen, "Dữ liệu trả về không hợp lệ", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this@ShowThongTinTruyen, "Cập nhật thất bại: ${response.message()}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<truyen?>, throwable: Throwable) {
                            Toast.makeText(this@ShowThongTinTruyen, "Cập nhật thất bại: ${throwable.message}", Toast.LENGTH_SHORT).show()
                            Log.e("API Error", "Cập nhật thất bại. Lỗi: ${throwable.message}", throwable)
                        }
                    })
                    cv_themchapter!!.visibility = View.GONE
                } else {
                    Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
                }
            }

            R.id.bt_them_newchapter -> {
                val idtruyen = truyen()
                idtruyen.id = tv_id!!.text.toString().toInt()
                val tenchapter = edt_tenchapter_newchapter!!.text.toString()

                if (tenchapter.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập tên chapter", Toast.LENGTH_SHORT).show()
                } else {
                    val date = LocalDate.now()
                    val chapter = Chapter(tenchapter, date, 0, 0.0)
                    val idtruyenId = idtruyen.id ?: 0 // Provide a default value of 0 if idtruyen.id is null
                    APIService.apiService.addChapter(idtruyenId, chapter)?.enqueue(object : Callback<Chapter?> {
                        override fun onResponse(call: Call<Chapter?>, response: Response<Chapter?>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@ShowThongTinTruyen, "Thêm chapter thành công", Toast.LENGTH_SHORT).show()
                                showChapter()
                            } else {
                                Toast.makeText(this@ShowThongTinTruyen, "Thêm chapter thất bại", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Chapter?>, throwable: Throwable) {
                            Toast.makeText(this@ShowThongTinTruyen, "Thêm chapter thất bại: ${throwable.message}", Toast.LENGTH_SHORT).show()
                            Log.e("API Error", "Lỗi: ${throwable.message}", throwable)
                        }
                    })
                }
            }

            R.id.bt_huy_newchapter -> {
                cv_themchapter!!.visibility = View.GONE
            }
        }
    }


    private fun reload() {
        val intent = intent
        finish()  // Kết thúc Activity hiện tại
        startActivity(intent)  // Khởi động lại Activity với dữ liệu mới
        overridePendingTransition(0, 0)
    }

    companion object {
        fun removeAccent(s: String): String {
            var s = s
            s = s.lowercase(Locale.getDefault())
            s = s.replace("đ".toRegex(), "d")
            val temp = Normalizer.normalize(s, Normalizer.Form.NFD)
            val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
            return pattern.matcher(temp).replaceAll("")
        }
    }
}