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
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.DanhGiaCuaToiAdapter
import vn.iotstar.appdoctruyen.model.DanhGiaCuaToiDto

class DanhGiaCuaToi : AppCompatActivity() {
    var dg: List<DanhGiaCuaToiDto>? = null
    var email: String? = null
    var idtk: Int? = null
    var tv_tongdanhgia: TextView? = null
    var rcv_tongdanhgia: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danh_gia_cua_toi)
        tv_tongdanhgia = findViewById(R.id.tv_tongdanhgia)
        rcv_tongdanhgia = findViewById(R.id.rcv_tongdanhgia)
        val user = FirebaseAuth.getInstance().currentUser
        email = user!!.email
        val thongTinTaiKhoan = ThongTinTaiKhoan()
        thongTinTaiKhoan.email = email
        thongTinTaiKhoan.gettaikhoan(email)
        val linearLayoutManager = LinearLayoutManager(this@DanhGiaCuaToi, RecyclerView.VERTICAL, false)
        rcv_tongdanhgia!!.setLayoutManager(linearLayoutManager)
        val item = DividerItemDecoration(this@DanhGiaCuaToi, DividerItemDecoration.VERTICAL)
        rcv_tongdanhgia!!.addItemDecoration(item)
        dg = ArrayList()


        // Sử dụng Handler để trì hoãn hành động trong luồng chính
        Handler().postDelayed(object : Runnable {
            override fun run() {
                idtk = thongTinTaiKhoan.tk!!.id
                danhGiaCuaToi
            }
        }, 500)
    }

    val danhGiaCuaToi: Unit
        get() {
            APIService.apiService.findDanhGiaByIdn(idtk)!!.enqueue(object : Callback<List<DanhGiaCuaToiDto>?> {
                override fun onResponse(
                    call: Call<List<DanhGiaCuaToiDto>?>,
                    response: Response<List<DanhGiaCuaToiDto>?>
                ) {
                    dg = response.body()
                    tv_tongdanhgia!!.text = "Tổng đánh giá: " + dg!!.size
                    val danhGiaCuaToiAdapter =
                        DanhGiaCuaToiAdapter(this@DanhGiaCuaToi, R.layout.item_rcv_danhgiacuatoi, dg)
                    rcv_tongdanhgia!!.setAdapter(danhGiaCuaToiAdapter)
                }

                override fun onFailure(call: Call<List<DanhGiaCuaToiDto>?>, t: Throwable) {
                    Log.e("API_CALL", "Failed to fetch data from API", t)
                    Toast.makeText(applicationContext, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
}

private fun <T> Call<T>.enqueue(callback: Callback<List<DanhGiaCuaToiDto>?>) {

}
