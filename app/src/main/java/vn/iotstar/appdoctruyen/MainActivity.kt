package vn.iotstar.appdoctruyen

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {
    var fragment: Fragment? = null
    var chipNavigationBar: ChipNavigationBar? = null
    var email: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        email = if (FirebaseAuth.getInstance().currentUser == null) null else FirebaseAuth.getInstance().currentUser!!
            .email
        chipNavigationBar = findViewById(R.id.NavigationBar)
        chipNavigationBar!!.setItemSelected(R.id.home, true)
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        chipNavigationBar!!.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(i: Int) {
                if (i == R.id.home) {
                    fragment = HomeFragment()
                } else if (i == R.id.store) {
                    if (email == null) {
                        Toast.makeText(
                            applicationContext,
                            "Vui lòng đăng nhập để sử dụng chức năng này",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        fragment = TuSachFragment()
                    }
                } else {
                    if (email == null) {
                        Toast.makeText(
                            applicationContext,
                            "Vui lòng đăng nhập để sử dụng chức năng này",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        fragment = TaiKhoanFragment()
                    }
                }

                if (fragment != null) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, fragment!!)
                        .commit()
                }
            }
        })

    }
}
