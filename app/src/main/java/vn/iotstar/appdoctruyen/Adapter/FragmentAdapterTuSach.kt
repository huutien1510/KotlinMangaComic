package vn.iotstar.appdoctruyen.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.iotstar.appdoctruyen.GoiYTruyenFragment
import vn.iotstar.appdoctruyen.LichSuDocFragment

class FragmentAdapterTuSach(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return GoiYTruyenFragment()
        }
        return LichSuDocFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}
