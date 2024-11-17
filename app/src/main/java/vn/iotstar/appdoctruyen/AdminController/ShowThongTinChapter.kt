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
import vn.iotstar.appdoctruyen.AdminAdapter.QuanLyDNChapterAdapter
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.Chapter
import vn.iotstar.appdoctruyen.model.ChapterAdmin
import vn.iotstar.appdoctruyen.model.Noidungchapter
import vn.iotstar.appdoctruyen.model.truyen

class ShowThongTinChapter : AppCompatActivity(), View.OnClickListener {
    var img_truyen: ImageView? = null
    var img_new: ImageView? = null
    var tv_id: TextView? = null
    var tv_danhgia: TextView? = null
    var tv_luotxem: TextView? = null
    var tv_ngaydang: TextView? = null
    var tv_tentruyen: TextView? = null
    var edt_tenchapter: EditText? = null
    var edt_linkanh: EditText? = null
    var bt_chinhsua: Button? = null
    var bt_them: Button? = null
    var bt_huy: Button? = null
    var bt_xacnhanchaper: Button? = null
    var bt_huychinhsuachapter: Button? = null
    var truyen1: truyen? = null
    var chapter: Chapter? = null
    var id = 0
    var cv_themndchapter: CardView? = null
    private var rcv: RecyclerView? = null
    private val adapter: QuanLyDNChapterAdapter? = null
    private var list: List<ChapterAdmin>? = null
    private var truyenList: List<truyen>? = null
    private var noidungchapterList: List<Noidungchapter>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_thong_tin_chapter)
        AnhXa()
        val intent = intent
        id = intent.getIntExtra("id_chapter", 1)

        //Toast.makeText(this, "id: " + id, Toast.LENGTH_SHORT).show();
//        chapter=db.getOneChapter(id);
//        truyen=db.getTruyenById(chapter.getIdtruyen());
        val linearLayoutManager = LinearLayoutManager(this@ShowThongTinChapter, RecyclerView.VERTICAL, false)
        rcv!!.setLayoutManager(linearLayoutManager)
        val itemDecoration = DividerItemDecoration(this@ShowThongTinChapter, DividerItemDecoration.VERTICAL)
        rcv!!.addItemDecoration(itemDecoration)
        list = ArrayList()
        noidungchapterList = ArrayList()
        setEnable(0)
        setData()
        showThongTinChapter()
        setOnClickListener()
    }

    private fun showThongTinChapter() {
        APIService.apiService.getLinkChapterById(id)?.enqueue(object : Callback<List<Noidungchapter>?> {
            override fun onResponse(call: Call<List<Noidungchapter>?>, response: Response<List<Noidungchapter>?>) {
                noidungchapterList = response.body()
                val adapter = QuanLyDNChapterAdapter(this@ShowThongTinChapter, noidungchapterList)
                rcv!!.setAdapter(adapter)
            }

            override fun onFailure(call: Call<List<Noidungchapter>?>, throwable: Throwable) {
                //Toast.makeText(ShowThongTinChapter.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                Log.e("API Error", "Lỗi kết nối. Lỗi: " + throwable.message, throwable)
                Toast.makeText(this@ShowThongTinChapter, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setOnClickListener() {
        img_new!!.setOnClickListener(this)
        bt_them!!.setOnClickListener(this)
        bt_huy!!.setOnClickListener(this)
        bt_chinhsua!!.setOnClickListener(this)
        bt_huychinhsuachapter!!.setOnClickListener(this)
        bt_xacnhanchaper!!.setOnClickListener(this)
    }

    private fun setData() {
        APIService.apiService.getChapterContentById(id)?.enqueue(object : Callback<List<ChapterAdmin>?> {
            override fun onResponse(call: Call<List<ChapterAdmin>?>, response: Response<List<ChapterAdmin>?>) {
                list = response.body()
                if (list != null) {
                    //Glide.with(ShowThongTinChapter.this).load(truyen.getLinkhanh()).into(img_truyen);
                    tv_id!!.text = "" + list!![0].id
                    //tv_tentruyen.setText(truyen.getTentruyen());
                    edt_tenchapter!!.setText(list!![0].tenchapter)
                    tv_danhgia!!.text = "" + list!![0].danhgia
                    tv_luotxem!!.text = "" + list!![0].soluotxem
                    tv_ngaydang!!.text = list!![0].ngaydang
                }
            }

            override fun onFailure(call: Call<List<ChapterAdmin>?>, throwable: Throwable) {}
        })
        APIService.apiService.getTruyenById(id).enqueue(object : Callback<List<truyen>?> {
            override fun onResponse(call: Call<List<truyen>?>, response: Response<List<truyen>?>) {
                truyenList = response.body()
                if (truyenList != null) {
                    Glide.with(this@ShowThongTinChapter).load(truyenList!![0].linkanh).into(img_truyen!!)
                    tv_tentruyen!!.text = truyenList!![0].tentruyen
                }
            }

            override fun onFailure(call: Call<List<truyen>?>, throwable: Throwable) {
                Toast.makeText(this@ShowThongTinChapter, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setEnable(i: Int) {
        if (i == 1) {
            edt_tenchapter!!.setEnabled(true)
            bt_xacnhanchaper!!.visibility = View.VISIBLE
            bt_huychinhsuachapter!!.visibility = View.VISIBLE
            bt_chinhsua!!.visibility = View.GONE
        } else {
            edt_tenchapter!!.setEnabled(false)
            bt_xacnhanchaper!!.visibility = View.GONE
            bt_huychinhsuachapter!!.visibility = View.GONE
            bt_chinhsua!!.visibility = View.VISIBLE
        }
    }

    private fun reload() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }

    private fun AnhXa() {
        img_new = findViewById(R.id.img_newnoidungchapter)
        img_truyen = findViewById(R.id.img_qlc)
        bt_chinhsua = findViewById(R.id.bt_chinhsuachapter)
        bt_huy = findViewById(R.id.bt_huy_newndchapter)
        bt_them = findViewById(R.id.bt_them_newndchapter)
        tv_id = findViewById(R.id.tv_qlc_id)
        edt_tenchapter = findViewById(R.id.edt_qlc_tenchapter)
        edt_linkanh = findViewById(R.id.edt_linkanh_newndchapter)
        cv_themndchapter = findViewById(R.id.cv_themndchapter)
        bt_xacnhanchaper = findViewById(R.id.bt_xacnhanchapter)
        bt_huychinhsuachapter = findViewById(R.id.bt_huychinhsuachapter)
        rcv = findViewById(R.id.rcv_quanlynoidungchapter)
        tv_danhgia = findViewById(R.id.tv_qlc_danhgia)
        tv_luotxem = findViewById(R.id.tv_qlc_luotxem)
        tv_ngaydang = findViewById(R.id.tv_qlc_ngaydang)
        tv_tentruyen = findViewById(R.id.tv_qlc_tentruyen)
        rcv = findViewById(R.id.rcv_quanlynoidungchapter)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.img_newnoidungchapter) {
            cv_themndchapter!!.visibility = View.VISIBLE
        }
        if (v.id == R.id.bt_huy_newndchapter) {
            cv_themndchapter!!.visibility = View.GONE
        }
        if (v.id == R.id.bt_them_newndchapter) {
            val linkanh = edt_linkanh!!.getText().toString().trim { it <= ' ' }
            if (linkanh.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập link ảnh", Toast.LENGTH_SHORT).show()
            } else {
                val noidungchapter = Noidungchapter(linkanh)
                APIService.apiService.addLinkChapter(id, noidungchapter)?.enqueue(object : Callback<Noidungchapter?> {
                    override fun onResponse(call: Call<Noidungchapter?>, response: Response<Noidungchapter?>) {
                        val result = response.body()
                        if (result != null) {
                            Toast.makeText(
                                this@ShowThongTinChapter,
                                "Thêm nội dung chapter thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                            showThongTinChapter()
                            cv_themndchapter!!.visibility = View.GONE
                        } else {
                            Toast.makeText(
                                this@ShowThongTinChapter,
                                "Thêm nội dung chapter thất bại",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<Noidungchapter?>, throwable: Throwable) {
                        //Toast.makeText(ShowThongTinChapter.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                        Log.e("API Error", "Lỗi kết nối. Lỗi: " + throwable.message, throwable)
                        Toast.makeText(this@ShowThongTinChapter, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
                    }
                })
                reload()
            }
        }
    }
}

fun <T> Call<T>?.enqueue(callback: Callback<List<ChapterAdmin>?>) {

}


