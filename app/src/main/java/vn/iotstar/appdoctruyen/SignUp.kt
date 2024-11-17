package vn.iotstar.appdoctruyen

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.model.Taikhoan

class SignUp : AppCompatActivity(), View.OnClickListener {
    private var email: EditText? = null
    private var pass: EditText? = null
    private var confpass: EditText? = null
    private var btnRegister: Button? = null
    private var mAuth: FirebaseAuth? = null
    var user: FirebaseUser? = null
    var prg: ProgressDialog? = null
    var prgisshow = false
    override fun onStop() {
        super.onStop()
        if (prg!!.isShowing || !prgisshow) user!!.delete()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        mAuth = FirebaseAuth.getInstance()
        email = findViewById(R.id.txt_email)
        pass = findViewById(R.id.txt_password)
        confpass = findViewById(R.id.txt_confirmpassword)
        btnRegister = findViewById(R.id.btn_logingg)
        prg = ProgressDialog(this)
        prg!!.setMessage("Đang đợi xác nhận email!")
        prg!!.setCancelable(false)

        //setOnClickListener();
        btnRegister!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                register()
                data
            }
        })
    }

    private fun register() {
        val emailtype: String
        val passtype: String
        val confpasstype: String
        emailtype = email!!.getText().toString()
        passtype = pass!!.getText().toString()
        confpasstype = confpass!!.getText().toString()
        if (TextUtils.isEmpty(emailtype)) {
            Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(passtype)) {
            Toast.makeText(this, "Vui lòng nhập password!", Toast.LENGTH_SHORT).show()
            return
        }
        if (passtype != confpasstype) {
            Toast.makeText(this, "Xác nhận mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
            return
        }
        mAuth!!.createUserWithEmailAndPassword(emailtype, passtype).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                user = mAuth!!.currentUser
                if (user != null) {
                    sendEmailVerification(user!!)
                }
            } else Toast.makeText(applicationContext, "Đăng kí thất bại!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification()
            .addOnCompleteListener(this) { task: Task<Void?> ->
                if (task.isSuccessful) {
                    // Email xác minh đã được gửi
                    prg!!.show()
                    prgisshow = true
                    Toast.makeText(this, "Email xác nhận đã được gửi tới " + user.email, Toast.LENGTH_SHORT).show()
                    checkEmailVerification(user)
                } else {
                    // Gửi email xác minh thất bại
                    user.delete()
                    Toast.makeText(this, "Gửi email xác nhận lỗi! Hủy đăng ký!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkEmailVerification(user: FirebaseUser) {
        val handler = Handler(Looper.getMainLooper())
        val checkEmailVerified: Runnable = object : Runnable {
            override fun run() {
                user.reload().addOnCompleteListener { task: Task<Void?> ->
                    if (task.isSuccessful) {
                        if (user.isEmailVerified) {
                            // Email đã được xác minh
                            prg!!.dismiss()
                            Toast.makeText(applicationContext, "Đăng kí thành công", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SignUp, MainActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.putExtra("email", email!!.getText().toString())
                            startActivity(intent)
                        } else {
                            // Tiếp tục kiểm tra sau một khoảng thời gian định kỳ
                            handler.postDelayed(this, 3000) // 3 giây
                        }
                    } else {
                        // Xử lý lỗi
                        user.delete()
                        Toast.makeText(applicationContext, "Xảy ra lỗi! Hủy đăng ký!", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        handler.post(checkEmailVerified)
    }

    private fun setOnClickListener() {
        btnRegister!!.setOnClickListener(this)
        btnRegister!!.setOnClickListener { register() }
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_logingg) {
            val emailt = email!!.getText().toString()
            val matkhaut = pass!!.getText().toString()
            val taikhoan = Taikhoan(emailt, matkhaut, "Chuaxacdinh", "Chuaxacdinh", 0, 0)
            APIService.apiService.addTaiKhoan(taikhoan)!!.enqueue(object : Callback<Taikhoan?> {
                override fun onResponse(call: Call<Taikhoan?>, response: Response<Taikhoan?>) {
                    Toast.makeText(this@SignUp, "Thêm thành công", Toast.LENGTH_SHORT)
                    //                    showTaiKhoan();
//                    cv_themtaikhoan.setVisibility(View.GONE);
                }

                override fun onFailure(call: Call<Taikhoan?>, t: Throwable) {
                    Toast.makeText(this@SignUp, "Thêm thất bại", Toast.LENGTH_SHORT)
                }
            })
        }
    }

    val data: Unit
        get() {
            val emailt = email!!.getText().toString()
            val matkhaut = pass!!.getText().toString()
            val taikhoan = Taikhoan(emailt, matkhaut, "Chuaxacdinh", "Chuaxacdinh", 0, 0)
            APIService.apiService.addTaiKhoan(taikhoan)!!.enqueue(object : Callback<Taikhoan?> {
                override fun onResponse(call: Call<Taikhoan?>, response: Response<Taikhoan?>) {
                    Toast.makeText(this@SignUp, "Thêm thành công", Toast.LENGTH_SHORT)
                    //                    showTaiKhoan();
//                    cv_themtaikhoan.setVisibility(View.GONE);
                }

                override fun onFailure(call: Call<Taikhoan?>, t: Throwable) {
                    Toast.makeText(this@SignUp, "Thêm thất bại", Toast.LENGTH_SHORT)
                }
            })
        }
}
