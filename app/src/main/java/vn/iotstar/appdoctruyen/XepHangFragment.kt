package vn.iotstar.appdoctruyen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import vn.iotstar.appdoctruyen.Adapter.FragmentAdapterBXH

class XepHangFragment : AppCompatActivity() {
    var tabLayout: TabLayout? = null
    var pager2: ViewPager2? = null
    var adapter: FragmentAdapterBXH? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.xephang)
        tabLayout = findViewById(R.id.tab_layout_xh)
        pager2 = findViewById(R.id.view_pager2_xh)
        val fragmentManager = supportFragmentManager
        adapter = FragmentAdapterBXH(fragmentManager, lifecycle)
        pager2!!.setAdapter(adapter)
        tabLayout!!.addTab(tabLayout!!.newTab().setText("BXH Votes"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("BXH Lượt Xem"))
        tabLayout!!.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager2!!.setCurrentItem(tab.position)
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
}
