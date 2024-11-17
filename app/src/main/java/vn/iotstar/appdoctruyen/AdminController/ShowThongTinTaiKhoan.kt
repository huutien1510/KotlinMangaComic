package vn.iotstar.appdoctruyen.AdminController

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.Taikhoan

class ShowThongTinTaiKhoan : AppCompatActivity() {
    var img: ImageView? = null
    var tv_id: TextView? = null
    var tv_email: TextView? = null
    var tv_matkhau: TextView? = null
    var tv_ten: TextView? = null
    var tv_dienthoai: TextView? = null
    var tv_trangthai: TextView? = null
    var tv_diem: TextView? = null
    private var taikhoan: List<Taikhoan>? = null
    var email: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_thong_tin_tai_khoan)
        AnhXa()
        val intent = intent
        email = intent.getStringExtra("email")
        taikhoan = ArrayList()
        setData()
    }

    private fun setData() {
        APIService.apiService.getTaiKhoanByEmail(email)?.enqueue(object : Callback<List<Taikhoan>?> {
            override fun onResponse(call: Call<List<Taikhoan>?>, response: Response<List<Taikhoan>?>) {
                taikhoan = response.body()
                if (taikhoan != null) {
                    val linkanh = taikhoan!![0].linkanh
                    if (linkanh != null) {
                        Glide.with(this@ShowThongTinTaiKhoan).load(linkanh).into(img!!)
                    } else {
                        img!!.setImageResource(R.drawable.avatar)
                    }
                    tv_id!!.text = "" + taikhoan!![0].id
                    tv_email!!.text = taikhoan!![0].email
                    tv_matkhau!!.text = taikhoan!![0].matkhau
                    tv_ten!!.text = taikhoan!![0].hoten
                    tv_dienthoai!!.text = taikhoan!![0].dienthoai
                    tv_diem!!.text = "" + taikhoan!![0].diemthuong
                    val trangthai = taikhoan!![0].loaitk
                    if (trangthai != 2) {
                        tv_trangthai!!.text = "Hoạt động"
                    } else {
                        tv_trangthai!!.text = "Bị khóa"
                    }
                }
            }

            override fun onFailure(call: Call<List<Taikhoan>?>, throwable: Throwable) {}
        })
    }

    private fun AnhXa() {
        img = findViewById(R.id.img_qltk)
        tv_dienthoai = findViewById(R.id.tv_qltk_dienthoai)
        tv_email = findViewById(R.id.tv_qltk_email)
        tv_id = findViewById(R.id.tv_qltk_id)
        tv_matkhau = findViewById(R.id.tv_qltk_matkhau)
        tv_trangthai = findViewById(R.id.tv_qltk_trangthai)
        tv_ten = findViewById(R.id.tv_qltk_ten)
        tv_diem = findViewById(R.id.tv_qltk_diem)
    }
}

private fun <T> Call<T>?.enqueue(callback: Callback<List<Taikhoan>?>) {

}
