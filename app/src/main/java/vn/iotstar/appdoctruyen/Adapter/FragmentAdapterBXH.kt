package vn.iotstar.appdoctruyen.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import vn.iotstar.appdoctruyen.BXHLuotXemFragment
import vn.iotstar.appdoctruyen.BXHVoteFragment

class FragmentAdapterBXH(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return BXHLuotXemFragment()
        }
        return BXHVoteFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}
