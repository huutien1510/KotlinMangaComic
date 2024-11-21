package vn.iotstar.appdoctruyen.AdminController

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.AdminAdapter.QuanLyTaiKhoanAdapter
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.Taikhoan

class QuanLyTaiKhoan : AppCompatActivity(), View.OnClickListener {
    var taiKhoan: Taikhoan? = null
    var email: String? = null
    private var rcv: RecyclerView? = null
    private var adapter: QuanLyTaiKhoanAdapter? = null

    var bt_them: Button? = null
    var bt_huy: Button? = null
    var edt_email: EditText? = null
    var edt_matkhau: EditText? = null
    var edt_ten: EditText? = null
    var edt_dienthoai: EditText? = null
    var cv_themtaikhoan: CardView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quan_ly_tai_khoan)
        AnhXa()
        val intent = intent
        email = intent.getStringExtra("email")
        val linearLayoutManager = LinearLayoutManager(this@QuanLyTaiKhoan, RecyclerView.VERTICAL, false)
        rcv!!.setLayoutManager(linearLayoutManager)
        val itemDecoration = DividerItemDecoration(this@QuanLyTaiKhoan, DividerItemDecoration.VERTICAL)
        rcv!!.addItemDecoration(itemDecoration)
        setOnClickListener()
        showTaiKhoan()
    }

    private fun showTaiKhoan() {
        APIService.apiService.taiKhoan?.enqueue(object : Callback<List<Taikhoan>> {
            override fun onResponse(call: Call<List<Taikhoan>>, response: Response<List<Taikhoan>>) {
                if (response.isSuccessful) {
                    val list = response.body()
                    Log.d("API_RESPONSE", list.toString())
                    adapter = QuanLyTaiKhoanAdapter(this@QuanLyTaiKhoan, list)
                    rcv!!.adapter = adapter
                } else {
                    Log.e("API_ERROR", "Response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Taikhoan>>, t: Throwable) {
                Log.e("API_ERROR", "Failure: ${t.message}")
                Toast.makeText(this@QuanLyTaiKhoan, "API call failed: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun AnhXa() {
        rcv = findViewById(R.id.rcv_quanlytaikhoan)
        bt_huy = findViewById(R.id.bt_huy_newtk)
        bt_them = findViewById(R.id.bt_them_newtk)
        edt_email = findViewById(R.id.edt_email_newtk)
        edt_matkhau = findViewById(R.id.edt_mk_newtk)
        edt_ten = findViewById(R.id.edt_ten_newtk)
        edt_dienthoai = findViewById(R.id.edt_dienthoai_newtk)
        cv_themtaikhoan = findViewById(R.id.cv_themtk)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.bt_huy_newtk) {
            cv_themtaikhoan!!.visibility = View.GONE
        }
        if (v.id == R.id.bt_them_newtk) {
            val email = edt_email!!.getText().toString()
            val matkhau = edt_matkhau!!.getText().toString()
            val ten = edt_ten!!.getText().toString()
            val dienthoai = edt_dienthoai!!.getText().toString()
            val taikhoan = Taikhoan(email, matkhau, ten, dienthoai, 0, 0)
            APIService.apiService.addTaiKhoan(taikhoan)?.enqueue(object : Callback<Taikhoan?> {
                override fun onResponse(call: Call<Taikhoan?>, response: Response<Taikhoan?>) {
                    Toast.makeText(this@QuanLyTaiKhoan, "Thêm thành công", Toast.LENGTH_SHORT)
                    showTaiKhoan()
                    cv_themtaikhoan!!.visibility = View.GONE
                }

                override fun onFailure(call: Call<Taikhoan?>, t: Throwable) {}
            })
        }
    }

    private fun setOnClickListener() {
        bt_them!!.setOnClickListener(this)
        bt_huy!!.setOnClickListener(this)
    }

    private fun reload() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }
}

private fun <T> Call<T>?.enqueue(callback: Callback<List<Taikhoan?>?>) {

}
