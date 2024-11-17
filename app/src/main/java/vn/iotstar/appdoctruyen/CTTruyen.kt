package vn.iotstar.appdoctruyen

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.FragmentAdapter
import vn.iotstar.appdoctruyen.model.truyen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner


class CTTruyen : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var pager2: ViewPager2? = null
    var adapter: FragmentAdapter? = null
    var img_truyen: ImageView? = null
    var id_truyen = 0
    var truyenList: List<truyen?>? = null
    var Truyen: truyen? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cttruyen)
        Anhxa()
        val intent = intent
        id_truyen = intent.getIntExtra("id_truyen", 1)
        truyenById
        val fragmentManager = supportFragmentManager
        adapter = FragmentAdapter(fragmentManager, lifecycle)
        pager2!!.setAdapter(adapter)
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Chi tiết"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Chapter"))
        tabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager2!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        pager2!!.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout!!.selectTab(tabLayout!!.getTabAt(position))
            }
        })
    }

    private fun Anhxa() {
        tabLayout = findViewById(R.id.tab_layout)
        pager2 = findViewById(R.id.view_pager2)
        img_truyen = findViewById(R.id.img_truyen)
    }

    private val truyenById: Unit
        private get() {
            APIService.apiService.getTruyenById(id_truyen)!!.enqueue(object : Callback<List<truyen?>?> {
                override fun onResponse(call: Call<List<truyen?>?>, response: Response<List<truyen?>?>) {
                    truyenList = response.body()
                    Truyen = truyenList!![0]
                    Glide.with(this@CTTruyen).load(Truyen!!.linkanh).into(img_truyen!!)
                }

                override fun onFailure(call: Call<List<truyen?>?>, t: Throwable) {
                    Log.e("API_CALL", "Failed to fetch data from API", t)
                }
            })
        }
}

private fun <T> Call<T>.enqueue(callback: Callback<List<truyen?>?>) {

}
