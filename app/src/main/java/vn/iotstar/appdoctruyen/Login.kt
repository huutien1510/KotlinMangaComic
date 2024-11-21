package vn.iotstar.appdoctruyen

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.model.Taikhoan

class Login : AppCompatActivity() {
    private var email: EditText? = null
    private var pass: EditText? = null
    private var btnLogin: Button? = null
    private var btnRegister: Button? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        email = findViewById(R.id.txt_email)
        pass = findViewById(R.id.txt_password)
        btnLogin = findViewById(R.id.btn_login)
        btnRegister = findViewById(R.id.btn_logingg)
        btnLogin!!.setOnClickListener(View.OnClickListener { login() })
        btnRegister!!.setOnClickListener(View.OnClickListener { register() })
    }

    private fun register() {
        val intent = Intent(this@Login, SignUp::class.java)
        startActivity(intent)
    }

    private fun login() {
        val emailtype: String
        val passtype: String
        var taikhoan: List<Taikhoan>? = null
        var tv_trangthai: TextView? = null
        emailtype = email!!.getText().toString()
        passtype = pass!!.getText().toString()
        if (TextUtils.isEmpty(emailtype)) {
            Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(passtype)) {
            Toast.makeText(this, "Vui lòng nhập password!", Toast.LENGTH_SHORT).show()
            return
        }
        mAuth!!.signInWithEmailAndPassword(emailtype, passtype).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                APIService.apiService.getTaiKhoanByEmail(emailtype)?.enqueue(object :
                    Callback<List<Taikhoan>?> {
                    override fun onResponse(call: Call<List<Taikhoan>?>, response: Response<List<Taikhoan>?>) {
                        taikhoan = response.body()
                        if (taikhoan != null) {
                            val trangthai = taikhoan!![0].loaitk
                            if (trangthai != 2) {
                                Toast.makeText(applicationContext, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@Login, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(applicationContext, "Tài khoản bạn đã bị khóa!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Taikhoan>?>, throwable: Throwable) {}
                })

            } else Toast.makeText(applicationContext, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show()
        }
    }
}