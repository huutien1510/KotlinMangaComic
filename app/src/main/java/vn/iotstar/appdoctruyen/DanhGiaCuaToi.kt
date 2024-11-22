package vn.iotstar.appdoctruyen

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.DanhGiaCuaToiAdapter
import vn.iotstar.appdoctruyen.model.DanhGiaCuaToiDto

class DanhGiaCuaToi : AppCompatActivity() {
    var danhGiaList: List<DanhGiaCuaToiDto>? = null
    var email: String? = null
    var idtk: Int? = null
    var tv_tongdanhgia: TextView? = null
    var rcv_tongdanhgia: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danh_gia_cua_toi)

        tv_tongdanhgia = findViewById(R.id.tv_tongdanhgia)
        rcv_tongdanhgia = findViewById(R.id.rcv_tongdanhgia)

        // Lấy email của người dùng đã đăng nhập
        val user = FirebaseAuth.getInstance().currentUser
        email = user?.email

        if (email == null) {
            Toast.makeText(this, "Không tìm thấy tài khoản. Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show()
            return
        }

        // Lấy ID tài khoản
        val thongTinTaiKhoan = ThongTinTaiKhoan()
        thongTinTaiKhoan.email = email
        thongTinTaiKhoan.gettaikhoan(email)

        // Setup RecyclerView
        rcv_tongdanhgia?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val itemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        rcv_tongdanhgia?.addItemDecoration(itemDecoration)

        // Delay để đợi lấy ID tài khoản
        Handler().postDelayed({
            idtk = thongTinTaiKhoan.tk?.id
            if (idtk != null) {
                loadDanhGiaCuaToi()
            } else {
                Toast.makeText(this, "Không tìm thấy ID tài khoản!", Toast.LENGTH_SHORT).show()
            }
        }, 500)
    }

    private fun loadDanhGiaCuaToi() {
        APIService.apiService.findDanhGiaByIdn(idtk)?.enqueue(object : Callback<List<DanhGiaCuaToiDto>> {
            override fun onResponse(
                call: Call<List<DanhGiaCuaToiDto>>,
                response: Response<List<DanhGiaCuaToiDto>>
            ) {
                if (response.isSuccessful) {
                    danhGiaList = response.body()
                    tv_tongdanhgia?.text = "Tổng đánh giá: ${danhGiaList?.size ?: 0}"

                    val adapter = DanhGiaCuaToiAdapter(
                        this@DanhGiaCuaToi,
                        R.layout.item_rcv_danhgiacuatoi,
                        danhGiaList
                    )
                    rcv_tongdanhgia?.adapter = adapter
                } else {
                    Toast.makeText(
                        this@DanhGiaCuaToi,
                        "Không thể tải dữ liệu: ${response.message()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<DanhGiaCuaToiDto>>, t: Throwable) {
                Log.e("API_CALL", "Lỗi khi tải dữ liệu", t)
                Toast.makeText(this@DanhGiaCuaToi, "Lỗi: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
