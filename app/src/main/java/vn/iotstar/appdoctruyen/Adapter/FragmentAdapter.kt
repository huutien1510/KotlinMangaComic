package vn.iotstar.appdoctruyen.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.iotstar.appdoctruyen.ChapterFragment
import vn.iotstar.appdoctruyen.ChiTietTruyenFragment

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        return if (position == 1) {
            ChapterFragment()
        } else ChiTietTruyenFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}
