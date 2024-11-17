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
import vn.iotstar.appdoctruyen.AdminAdapter.QuanLyTruyenAdapter
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.truyen
import java.text.Normalizer
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.regex.Pattern

class QuanLyTruyen : AppCompatActivity(), View.OnClickListener {
    private var rcv: RecyclerView? = null
    private val adapter: QuanLyTruyenAdapter? = null
    var img_newtruyen: ImageView? = null
    var bt_them: Button? = null
    var bt_huy: Button? = null
    var edt_tentruyen: EditText? = null
    var edt_theloai: EditText? = null
    var edt_tacgia: EditText? = null
    var edt_mota: EditText? = null
    var edt_linkanh: EditText? = null
    var cv_themtruyen: CardView? = null
    private var truyenList: List<truyen>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quan_ly_truyen)
        AnhXa()
        val linearLayoutManager = LinearLayoutManager(this@QuanLyTruyen, RecyclerView.VERTICAL, false)
        rcv!!.setLayoutManager(linearLayoutManager)
        val itemDecoration = DividerItemDecoration(this@QuanLyTruyen, DividerItemDecoration.VERTICAL)
        rcv!!.addItemDecoration(itemDecoration)
        truyenList = ArrayList()
        showAllTruyen()
        setOnClickListener()
    }

    private fun showAllTruyen() {
        APIService.apiService.truyenAll?.enqueue(object : Callback<List<truyen>?> {
            override fun onResponse(call: Call<List<truyen>?>, response: Response<List<truyen>?>) {
                truyenList = response.body()
                val adapter = QuanLyTruyenAdapter(this@QuanLyTruyen, truyenList)
                rcv!!.setAdapter(adapter)
            }

            override fun onFailure(call: Call<List<truyen>?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
                Toast.makeText(this@QuanLyTruyen, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(view: View) {
        if (view.id == R.id.img_newtruyen) {
            cv_themtruyen!!.visibility = View.VISIBLE
        }
        if (view.id == R.id.bt_huy_newtruyen) {
            cv_themtruyen!!.visibility = View.GONE
        }
        if (view.id == R.id.bt_them_newtruyen) {
            val tentruyen = edt_tentruyen!!.getText().toString()
            val theloai = edt_theloai!!.getText().toString()
            val tacgia = edt_tacgia!!.getText().toString()
            val mota = edt_mota!!.getText().toString()
            val linkanh = edt_linkanh!!.getText().toString()
            val key_search = removeAccent(tentruyen).trim { it <= ' ' }
            if (tentruyen.isEmpty() || theloai.isEmpty() || tacgia.isEmpty() || mota.isEmpty() || linkanh.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                if (checkTenTruyen(tentruyen)) {
                    Toast.makeText(this, "Truyện đã tồn tại", Toast.LENGTH_SHORT).show()
                } else {
                    //db.insertTruyen(tentruyen,theloai,tacgia,mota,linkanh);
                    val truyen1 = truyen(tentruyen, tacgia, mota, theloai, linkanh, 0, key_search)
                    APIService.apiService.addTruyen(truyen1)?.enqueue(object : Callback<truyen?> {
                        override fun onResponse(call: Call<truyen?>, response: Response<truyen?>) {
                            //Toast.makeText(QuanLyTruyen.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                            val result = response.body()
                            if (result != null) {
                                Toast.makeText(this@QuanLyTruyen, "Thêm thành công", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<truyen?>, throwable: Throwable) {
                            Toast.makeText(this@QuanLyTruyen, "Thêm thất bại", Toast.LENGTH_SHORT).show()
                        }
                    })
                }


                //Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                cv_themtruyen!!.visibility = View.GONE
                reload()
            }
        }
    }

    private fun setOnClickListener() {
        bt_them!!.setOnClickListener(this)
        bt_huy!!.setOnClickListener(this)
        img_newtruyen!!.setOnClickListener(this)
    }

    fun checkTenTruyen(tentruyen: String): Boolean {
        val isExist = BooleanArray(1)
        val latch = CountDownLatch(1)
        APIService.apiService.tenTruyen?.enqueue(object : Callback<List<String>> {
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                val tenTruyenList = response.body()!!
                for (ten in tenTruyenList) {
                    if (tentruyen == ten) {
                        isExist[0] = true
                        break
                    }
                }
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
            }
        })
        return isExist[0]
    }

    //    public void checkTenTruyen(String tentruyen) {
    //        APIService.apiService.getTenTruyen().enqueue(new Callback<List<String>>() {
    //            @Override
    //            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
    //                List<String> tenTruyenList = response.body();
    //                for (String ten : tenTruyenList) {
    //                    if (tentruyen.equals(removeAccent(ten).trim())) {
    //                        Log.d("checkTenTruyen", "Tên truyện đã tồn tại");
    //                        return;
    //                    }
    //                }
    //                Log.d("checkTenTruyen", "Tên truyện không tồn tại");
    //            }
    //
    //            @Override
    //            public void onFailure(Call<List<String>> call, Throwable t) {
    //                Log.e("API_CALL", "Failed to fetch data from API", t);
    //            }
    //        });
    //    }
    //    public int checkTenTruyen(String tentruyen){
    //        // @GET("truyen/tentruyen")
    //        //    Call<List<String>> getTenTruyen();
    //
    //        for (int i = 0; i < listtruyen.size(); i++) {
    //            if (tentruyen.equals(removeAccent(listtruyen.get(i).getTentruyen()).trim())) {
    //                return 1;
    //            }
    //        }
    //        return 0;
    //    }
    private fun reload() {
        val intent = intent
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()
        overridePendingTransition(0, 0)
        startActivity(intent)
    }

    private fun AnhXa() {
        rcv = findViewById(R.id.rcv_quanlytruyen)
        img_newtruyen = findViewById(R.id.img_newtruyen)
        bt_huy = findViewById(R.id.bt_huy_newtruyen)
        bt_them = findViewById(R.id.bt_them_newtruyen)
        edt_tentruyen = findViewById(R.id.edt_tentruyen_newtruyen)
        edt_theloai = findViewById(R.id.edt_theloai_newtruyen)
        edt_tacgia = findViewById(R.id.edt_tg_newtruyen)
        edt_mota = findViewById(R.id.edt_mota_newtruyen)
        edt_linkanh = findViewById(R.id.edt_linkanh_newtruyen)
        cv_themtruyen = findViewById(R.id.cv_themtruyen)
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


fun <T> Call<T>?.enqueue(callback: Callback<List<truyen>?>) {

}
