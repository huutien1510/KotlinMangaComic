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
import vn.iotstar.appdoctruyen.Adapter.BinhLuanCuaToiAdapter
import vn.iotstar.appdoctruyen.model.BinhLuanCuaToiDto

class BinhLuanCuaToi : AppCompatActivity() {
    var bl: List<BinhLuanCuaToiDto>? = null
    var email: String? = null
    var idtk: Int? = null
    var tv_tongbinhluan: TextView? = null
    var rcv_tongbinhluan: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_binh_luan_cua_toi)
        tv_tongbinhluan = findViewById(R.id.tv_tongbinhluan)
        rcv_tongbinhluan = findViewById(R.id.rcv_tongbinhluan)
        val user = FirebaseAuth.getInstance().currentUser
        email = user!!.email
        val thongTinTaiKhoan = ThongTinTaiKhoan()
        thongTinTaiKhoan.email = email
        thongTinTaiKhoan.gettaikhoan(email)
        val linearLayoutManager = LinearLayoutManager(this@BinhLuanCuaToi, RecyclerView.VERTICAL, false)
        rcv_tongbinhluan!!.setLayoutManager(linearLayoutManager)
        val item = DividerItemDecoration(this@BinhLuanCuaToi, DividerItemDecoration.VERTICAL)
        rcv_tongbinhluan!!.addItemDecoration(item)
        bl = ArrayList()


        // Sử dụng Handler để trì hoãn hành động trong luồng chính
        Handler().postDelayed(object : Runnable {
            override fun run() {
                idtk = thongTinTaiKhoan.tk!!.id
                binhLuanCuaToi
            }
        }, 500)
    }

    val binhLuanCuaToi: Unit
        get() {
            APIService.apiService.findByIdn(idtk)!!.enqueue(object : Callback<List<BinhLuanCuaToiDto>?> {
                override fun onResponse(
                    call: Call<List<BinhLuanCuaToiDto>?>,
                    response: Response<List<BinhLuanCuaToiDto>?>
                ) {
                    bl = response.body()
                    tv_tongbinhluan!!.text = "Tổng bình luận: " + bl!!.size
                    val binhLuanCuaToiAdapter =
                        BinhLuanCuaToiAdapter(this@BinhLuanCuaToi, R.layout.item_binhluancuatoi, bl)
                    rcv_tongbinhluan!!.setAdapter(binhLuanCuaToiAdapter)
                }

                override fun onFailure(call: Call<List<BinhLuanCuaToiDto>?>, t: Throwable) {
                    Log.e("API_CALL", "Failed to fetch data from API", t)
                    Toast.makeText(applicationContext, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
                }
            })
        }
}

private fun <T> Call<T>.enqueue(callback: Callback<List<BinhLuanCuaToiDto>?>) {

}
