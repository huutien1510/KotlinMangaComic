package vn.iotstar.appdoctruyen

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

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
                Toast.makeText(applicationContext, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@Login, MainActivity::class.java)
                startActivity(intent)
            } else Toast.makeText(applicationContext, "Đăng nhập thất bại!", Toast.LENGTH_SHORT).show()
        }
    }
}