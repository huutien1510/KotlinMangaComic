package vn.iotstar.appdoctruyen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.FragmentAdapter
import vn.iotstar.appdoctruyen.model.truyen

class CTTruyen : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var pager2: ViewPager2
    private lateinit var adapter: FragmentAdapter
    private lateinit var imgTruyen: ImageView
    private var idTruyen: Int = 0
    private var truyenList: List<truyen>? = null
    private var truyen: truyen? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cttruyen)
        initViews()

        // Lấy id từ Intent
        val intent = intent
        idTruyen = intent.getIntExtra("id_truyen", 1)

        // Lấy dữ liệu từ API
        getTruyenById()

        // Thiết lập ViewPager2 và TabLayout
        val fragmentManager: FragmentManager = supportFragmentManager
        adapter = FragmentAdapter(fragmentManager, lifecycle)
        pager2.adapter = adapter

        tabLayout.addTab(tabLayout.newTab().setText("Chi tiết"))
        tabLayout.addTab(tabLayout.newTab().setText("Chapter"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        pager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    private fun initViews() {
        tabLayout = findViewById(R.id.tab_layout)
        pager2 = findViewById(R.id.view_pager2)
        imgTruyen = findViewById(R.id.img_truyen)
    }

    private fun getTruyenById() {
        APIService.apiService.getTruyenById(idTruyen)?.enqueue(object : Callback<List<truyen>> {
            override fun onResponse(call: Call<List<truyen>>, response: Response<List<truyen>>) {
                if (response.isSuccessful && response.body() != null) {
                    truyenList = response.body()
                    truyen = truyenList?.get(0)
                    Glide.with(this@CTTruyen).load(truyen?.linkanh).into(imgTruyen)
                } else {
                    Log.e("API_RESPONSE", "Response failed or body is null: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<List<truyen>>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
            }
        })
    }
}
