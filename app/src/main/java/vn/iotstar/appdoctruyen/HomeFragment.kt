package vn.iotstar.appdoctruyen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.truyenAdapter
import vn.iotstar.appdoctruyen.AdminController.QuanLyTaiKhoan
import vn.iotstar.appdoctruyen.AdminController.QuanLyTruyen
import vn.iotstar.appdoctruyen.TimKiem
import vn.iotstar.appdoctruyen.model.truyen

class HomeFragment : Fragment(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {
    var imageSlider: ImageSlider? = null
    var rootView: View? = null
    var headerLayout: View? = null
    var rc1: RecyclerView? = null
    var rc2: RecyclerView? = null
    var rc3: RecyclerView? = null
    var truyenAdapter: truyenAdapter? = null
    var truyenMoiAdapter: truyenAdapter? = null
    var truyenTopAdapter: truyenAdapter? = null
    var truyenList: List<truyen>? = null
    var btnmenu: Button? = null
    var truyenMoi: List<truyen>? = null
    var truyenTop: List<truyen>? = null
    var drlo: DrawerLayout? = null
    var tv_TimKemHome: TextView? = null
    var tv_theloai: TextView? = null
    var tv_xephang: TextView? = null
    var tv_emailhome: TextView? = null
    var email: String? = null
    var navi: NavigationView? = null
    var user: FirebaseUser? = null
    var menu: Menu? = null
    var menuquantri: MenuItem? = null
    var btn_login: Button? = null
    var btn_logout: Button? = null
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
        rootView = inflater.inflate(R.layout.fragment_home, container, false)

        AnhXa()
        setupSlider()
        navi!!.setNavigationItemSelectedListener(this)
        navi!!.bringToFront()
        //Xét quyền hiển thị chức năng
        user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            email = user!!.email
            if (user!!.email == "admin@gmail.com") {
                menuquantri!!.setVisible(true)
            } else menuquantri!!.setVisible(false)
            tv_emailhome!!.text = user!!.email
            tv_emailhome!!.visibility = View.VISIBLE
            btn_logout!!.visibility = View.VISIBLE
            btn_login!!.visibility = View.GONE
        } else {
            menuquantri!!.setVisible(false)
            tv_emailhome!!.visibility = View.GONE
            btn_logout!!.visibility = View.GONE
            btn_login!!.visibility = View.VISIBLE
        }
        rc1?.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        rc2?.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
        rc3?.layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)

        println(requireActivity())
        truyenAdapter = truyenAdapter(requireActivity(), truyenList, email ?: "")
        truyenMoiAdapter = truyenAdapter(requireActivity(), truyenMoi, email ?: "")
        truyenTopAdapter = truyenAdapter(requireActivity(), truyenTop, email ?: "")

        rc1?.adapter = truyenAdapter
        rc2?.adapter = truyenMoiAdapter
        rc3?.adapter = truyenTopAdapter
        GetTruyen()
        setOnClickListener()
        return rootView
    }

    private fun setupSlider() {
        imageSlider = rootView?.findViewById(R.id.imageSlider)
        val slideModels = listOf(
            SlideModel(R.drawable.image1, ScaleTypes.FIT),
            SlideModel(R.drawable.image2, ScaleTypes.FIT),
            SlideModel(R.drawable.image3, ScaleTypes.FIT),
            SlideModel(R.drawable.image5, ScaleTypes.FIT),
            SlideModel(R.drawable.image4, ScaleTypes.FIT)
        )
        imageSlider?.setImageList(slideModels, ScaleTypes.FIT)
    }
    private fun setOnClickListener() {
        btnmenu!!.setOnClickListener(this)
        tv_theloai!!.setOnClickListener(this)
        tv_xephang!!.setOnClickListener(this)
        tv_TimKemHome!!.setOnClickListener(this)
        btn_login!!.setOnClickListener(this)
        btn_logout!!.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.btn_menu) {
            drlo!!.openDrawer(navi!!)
        }
        if (v.id == R.id.bt_dnhome) {
            val dialog_box = Intent(activity, Login::class.java)
            startActivity(dialog_box)
            requireActivity().finish()
        }

        //switch (v.getId()){
        if (v.id == R.id.tv_theloai) {
            val dialog_box3 = Intent(activity, TheLoaiFragment::class.java)
            dialog_box3.putExtra("email", email)
            startActivity(dialog_box3)
        }
        if (v.id == R.id.tv_xephang) {
            val dialog_box4 = Intent(activity, XepHangFragment::class.java)
            dialog_box4.putExtra("email", email)
            startActivity(dialog_box4)
        }
        if (v.id == R.id.tv_TimKiemHome) {
            val dialog_box1 = Intent(activity, TimKiem::class.java)
            dialog_box1.putExtra("email", email)
            startActivity(dialog_box1)
        }
        if (v.id == R.id.bt_dxhome) {
            val m = FirebaseAuth.getInstance()
            m.signOut()
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            Toast.makeText(
                requireActivity().applicationContext,
                "Đăng xuất thành công",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(intent)
            requireActivity().finish()
        }
    }

    private fun AnhXa() {
        drlo = rootView?.findViewById<View>(R.id.drlo) as DrawerLayout
        btnmenu = rootView?.findViewById(R.id.btn_menu)
        rc1 = rootView?.findViewById<View>(R.id.rv3) as RecyclerView
        rc2 = rootView?.findViewById<View>(R.id.rv) as RecyclerView
        rc3 = rootView?.findViewById<View>(R.id.rv2) as RecyclerView
        tv_theloai = rootView?.findViewById<View>(R.id.tv_theloai) as TextView
        tv_xephang = rootView?.findViewById<View>(R.id.tv_xephang) as TextView
        tv_TimKemHome = rootView?.findViewById<View>(R.id.tv_TimKiemHome) as TextView
        navi = rootView?.findViewById<View>(R.id.menu) as NavigationView
        menu = navi!!.menu
        menuquantri = menu!!.findItem(R.id.it_chucnangquantri)
        headerLayout = navi!!.inflateHeaderView(R.layout.menuheader)
        btn_login = headerLayout!!.findViewById<View>(R.id.bt_dnhome) as Button
        btn_logout = headerLayout!!.findViewById(R.id.bt_dxhome)
        tv_emailhome = headerLayout!!.findViewById(R.id.tv_emailhome)
    }

    private fun GetTruyen() {
        APIService.apiService.truyenAll?.enqueue(object : Callback<List<truyen>?> {
            override fun onResponse(call: Call<List<truyen>?>, response: Response<List<truyen>?>) {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        truyenList = data

                        println(data)

                        truyenAdapter?.setData(truyenList)

                    }
                }
            }

            override fun onFailure(call: Call<List<truyen>?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
                Toast.makeText(context, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
        APIService.apiService.truyenMoi?.enqueue(object : Callback<List<truyen>?> {
            override fun onResponse(call: Call<List<truyen>?>, response: Response<List<truyen>?>) {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        truyenMoi = data

                        println(data)

                        truyenMoiAdapter?.setData(truyenMoi)

                    }
                }
            }

            override fun onFailure(call: Call<List<truyen>?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
                Toast.makeText(context, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
        APIService.apiService.topTruyen?.enqueue(object : Callback<List<truyen>?> {
            override fun onResponse(call: Call<List<truyen>?>, response: Response<List<truyen>?>) {
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        truyenTop = data

                        println(data)

                        truyenTopAdapter?.setData(truyenTop)

                    }
                }
            }

            override fun onFailure(call: Call<List<truyen>?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
                Toast.makeText(context, "Failure: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == R.id.it_quanlytaikhoan) {
            val dialog_box = Intent(activity, QuanLyTaiKhoan::class.java)
            startActivity(dialog_box)
        }
        if (menuItem.itemId == R.id.it_quanlytruyen) {
            val dialog_box1 = Intent(activity, QuanLyTruyen::class.java)
            startActivity(dialog_box1)
        }
        //            case R.id.it_quanlybinhluan:
//                Intent dialog_box2 = new Intent(getActivity(), QuanLyBinhLuan.class);
//                startActivity(dialog_box2);
//                break;
//            case R.id.it_quanlythongke:
//                Intent dialog_box3 = new Intent(getActivity(), QuanLyThongKe.class);
//                startActivity(dialog_box3);
//                break;
        if (menuItem.itemId == R.id.it_xephang) {
            val dialog_box4 = Intent(activity, XepHangFragment::class.java)
            startActivity(dialog_box4)
        }
        if (menuItem.itemId == R.id.it_theloai) {
            val dialog_box5 = Intent(activity, TheLoaiFragment::class.java)
            startActivity(dialog_box5)
        }
        return true
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "email"
        fun newInstance(email: String?): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM3, email)
            fragment.setArguments(args)
            return fragment
        }
    }
}