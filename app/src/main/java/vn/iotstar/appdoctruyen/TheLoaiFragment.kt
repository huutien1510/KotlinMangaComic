package vn.iotstar.appdoctruyen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.FragmentAdapterTheLoai

class TheLoaiFragment : AppCompatActivity() {

    private var tabLayout: TabLayout? = null
    private var pager2: ViewPager2? = null
    private var adapter: FragmentAdapterTheLoai? = null

    private var autoCompleteTextView: AutoCompleteTextView? = null
    private var adapterItems: ArrayAdapter<String>? = null

    private var onTheLoaiSelectedListener: OnTheLoaiSelectedListener? = null

    interface OnTheLoaiSelectedListener {
        fun onTheLoaiSelected(theLoai: String) // Chỉ giữ lại một phương thức
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_theloai)

        initViews()
        setupViewPagerAndTabs()
        fetchTheLoaiData()
    }

    private fun initViews() {
        tabLayout = findViewById(R.id.tab_layout)
        pager2 = findViewById(R.id.view_pager2_tl)
        autoCompleteTextView = findViewById(R.id.auto_complete_txt)
    }

    private fun setupViewPagerAndTabs() {
        val fragmentManager = supportFragmentManager
        adapter = FragmentAdapterTheLoai(fragmentManager, lifecycle)
        pager2?.adapter = adapter

        tabLayout?.apply {
            addTab(newTab().setText("Mới nhất"))
            addTab(newTab().setText("BXH Votes"))
            addTab(newTab().setText("BXH Lượt Xem"))

            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    pager2?.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }

        pager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout?.getTabAt(position)?.select()
            }
        })
    }

    private fun fetchTheLoaiData() {
        APIService.apiService.theLoai?.enqueue(object : Callback<List<String>?> {
            override fun onResponse(call: Call<List<String>?>, response: Response<List<String>?>) {
                if (response.isSuccessful && response.body() != null) {
                    val theLoaiList = response.body() ?: emptyList()
                    setupAutoCompleteTextView(theLoaiList)
                } else {
                    Log.e("API_CALL", "Failed to fetch data from API")
                    showToast("Failed to fetch data from API")
                }
            }

            override fun onFailure(call: Call<List<String>?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
                showToast("Failure: ${t.message}")
            }
        })
    }

    private fun setupAutoCompleteTextView(theLoaiList: List<String>) {
        adapterItems = ArrayAdapter(this, R.layout.list_item, theLoaiList)
        autoCompleteTextView?.apply {
            setText(theLoaiList.firstOrNull() ?: "")
            setAdapter(adapterItems)
            setOnItemClickListener { parent, _, position, _ ->
                val selectedItem = parent.getItemAtPosition(position).toString()
                onTheLoaiSelectedListener?.onTheLoaiSelected(selectedItem)
                showToast("Thể loại: $selectedItem")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    fun setOnTheLoaiSelectedListener(listener: OnTheLoaiSelectedListener?) {
        onTheLoaiSelectedListener = listener
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
