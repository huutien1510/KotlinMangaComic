package vn.iotstar.appdoctruyen

import android.app.ProgressDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class DoiMatKhau : AppCompatActivity(), View.OnClickListener {
    var mkht: TextView? = null
    var mkm1: TextView? = null
    var mkm2: TextView? = null
    var user: FirebaseUser? = null
    var prg: ProgressDialog? = null
    var email: String? = null
    var prgisshow = false
    var ht: Button? = null
    var huy: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doi_mat_khau)
        Anhxa()
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_xacnhan) {
            val mkht1: String
            val mk11: String
            val mk21: String
            mkht1 = mkht!!.getText().toString()
            mk11 = mkm1!!.getText().toString()
            mk21 = mkm2!!.getText().toString()
            if (TextUtils.isEmpty(mkht1)) {
                Toast.makeText(this, "Vui lòng nhập email!", Toast.LENGTH_SHORT).show()
                return
            }
            if (TextUtils.isEmpty(mk11)) {
                Toast.makeText(this, "Vui lòng nhập password!", Toast.LENGTH_SHORT).show()
                return
            }
            if (TextUtils.isEmpty(mk21)) {
                Toast.makeText(this, "Vui lòng nhập password!", Toast.LENGTH_SHORT).show()
                return
            }
            if (mk11 != mk21) {
                Toast.makeText(this, "Xác nhận mật khẩu không chính xác", Toast.LENGTH_SHORT).show()
                return
            }
            user = FirebaseAuth.getInstance().currentUser
            email = user!!.email
            if (user != null) {
                val credential = EmailAuthProvider.getCredential(email!!, mkht1)
                user!!.reauthenticate(credential).addOnCompleteListener { task: Task<Void?> ->
                    if (task.isSuccessful) {
                        // Người dùng đã được xác thực lại thành công, cập nhật mật khẩu
                        updatePassword(user!!, mk11)
                    } else {
                        Toast.makeText(this, "Mật khẩu hiện tại không chính xác", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        if (v.id == R.id.btn_huy) {
            finish()
        }
    }

    fun Anhxa() {
        mkht = findViewById(R.id.edt_matkhauhientai)
        mkm1 = findViewById(R.id.edt_matkhaumoi)
        mkm2 = findViewById(R.id.edt_nhaplaimatkhau)
        ht = findViewById(R.id.btn_xacnhan)
        huy = findViewById(R.id.btn_huy)
        ht!!.setOnClickListener(this)
        huy!!.setOnClickListener(this)
    }

    private fun updatePassword(user: FirebaseUser, newpass: String) {
        user.updatePassword(newpass).addOnCompleteListener { task: Task<Void?> ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Mật khẩu mới không hợp lệ", Toast.LENGTH_SHORT).show()
            }
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
                            Toast.makeText(applicationContext, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()
                            finish()
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
}