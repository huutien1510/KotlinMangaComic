package vn.iotstar.appdoctruyen.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.iotstar.appdoctruyen.TheLoaiLuotXemFragment
import vn.iotstar.appdoctruyen.TheLoaiNewFragment
import vn.iotstar.appdoctruyen.TheLoaiVoteFragment

class FragmentAdapterTheLoai(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return TheLoaiVoteFragment()
            2 -> return TheLoaiLuotXemFragment()
        }
        return TheLoaiNewFragment()
    }

    override fun getItemCount(): Int {
        return 3
    }
}
