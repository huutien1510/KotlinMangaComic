package vn.iotstar.appdoctruyen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import vn.iotstar.appdoctruyen.Adapter.FragmentAdapterTuSach

/**
 * A simple [Fragment] subclass.
 * Use the [TuSachFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TuSachFragment : Fragment() {

    // Đổi tên biến `view` thành `rootView`
    private var rootView: View? = null
    private var tabLayout: TabLayout? = null
    private var pager2: ViewPager2? = null
    private var adapterTuSach: FragmentAdapterTuSach? = null

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tu_sach, container, false)
        Anhxa()
        val fragmentManager = requireActivity().supportFragmentManager
        adapterTuSach = FragmentAdapterTuSach(fragmentManager, lifecycle)
        pager2!!.adapter = adapterTuSach
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Lịch sử đọc"))
        /*tabLayout.addTab(tabLayout.newTab().setText("Gợi ý truyện"));*/tabLayout!!.addOnTabSelectedListener(object :
            OnTabSelectedListener {
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
        return rootView
    }

    private fun Anhxa() {
        tabLayout = rootView!!.findViewById(R.id.tab_layout_tusach)
        pager2 = rootView!!.findViewById(R.id.view_pager2_tusach)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TuSachFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): TuSachFragment {
            val fragment = TuSachFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
