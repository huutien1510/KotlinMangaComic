package vn.iotstar.appdoctruyen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.model.TaiKhoanDto
import vn.iotstar.appdoctruyen.model.Taikhoan

class ThongTinTaiKhoan : AppCompatActivity(), View.OnClickListener {
    var id: TextView? = null
    var memail: TextView? = null
    var hoten: TextView? = null
    var dienthoai: TextView? = null
    var diemthuong: TextView? = null
    var trangthai: TextView? = null
    var chinhsua: Button? = null
    var tk: TaiKhoanDto? = null
    var ax = false
    var email: String? = null
    var cs = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thong_tin_tai_khoan)
        val user = FirebaseAuth.getInstance().currentUser
        email = user!!.email
        Anhxa()
        chinhsua!!.setOnClickListener(this)
        ax = true
        gettaikhoan(email)
    }

    fun gettaikhoan(email: String?) {
        APIService.apiService.findByEmail(this.email)!!.enqueue(object : Callback<TaiKhoanDto?> {
            override fun onResponse(call: Call<TaiKhoanDto?>, response: Response<TaiKhoanDto?>) {
                tk = response.body()
                if (ax == true) {
                    id!!.text = tk!!.id.toString()
                    memail!!.text = tk!!.email.toString()
                    hoten!!.text = tk!!.hoten.toString()
                    dienthoai!!.text = tk!!.dienthoai.toString()
                    diemthuong!!.text = tk!!.diemthuong.toString()
                    trangthai!!.text = "Đang hoạt động"
                }
            }

            override fun onFailure(call: Call<TaiKhoanDto?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
                Toast.makeText(applicationContext, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_chinhsua) {
            if (!cs) {
                cs = true
                hoten!!.setEnabled(true)
                dienthoai!!.setEnabled(true)
                chinhsua!!.text = "Hoàn thành"
            } else {
                hoten!!.setEnabled(false)
                dienthoai!!.setEnabled(false)
                cs = false
                chinhsua!!.text = "Chỉnh sửa"
                //gọi api
                val hotent = hoten!!.getText().toString()
                val dienthoait = dienthoai!!.getText().toString()

                // updateTaiKhoan
                val idt = id!!.getText().toString().toInt()
                val taikhoan = Taikhoan(hotent, dienthoait)
                APIService.apiService.updateTaiKhoan(taikhoan, idt)!!.enqueue(object : Callback<Taikhoan?> {
                    override fun onResponse(call: Call<Taikhoan?>, response: Response<Taikhoan?>) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                        } else {
//                            Toast.makeText(getApplicationContext(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
//                        }
                    }

                    override fun onFailure(call: Call<Taikhoan?>, t: Throwable) {
                        Log.e("API_CALL", "Failed to fetch data from API", t)
                        Toast.makeText(applicationContext, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }

    private fun Anhxa() {
        id = findViewById(R.id.tv_id)
        memail = findViewById(R.id.tv_email)
        hoten = findViewById(R.id.tv_hoten)
        dienthoai = findViewById(R.id.tv_dienthoai)
        diemthuong = findViewById(R.id.tv_diemthuong)
        trangthai = findViewById(R.id.tv_trangthai)
        chinhsua = findViewById(R.id.btn_chinhsua)
    }
}