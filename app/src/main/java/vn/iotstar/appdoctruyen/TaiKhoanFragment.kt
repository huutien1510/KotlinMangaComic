package vn.iotstar.appdoctruyen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import vn.iotstar.appdoctruyen.model.Taikhoan

/**
 * A simple [Fragment] subclass.
 * Use the [TaiKhoanFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TaiKhoanFragment : Fragment(), View.OnClickListener {

    private var tv_tongngaydiemdanh: TextView? = null
    private var tv_tk_diem: TextView? = null
    private var tv_binhluancuatoi: TextView? = null
    private var tv_danhgiacuatoi: TextView? = null
    private var tv_doimatkhau: TextView? = null
    private var tv_dangxuat: TextView? = null
    private var tv_tttk: TextView? = null
    private var tv_username: TextView? = null
    private var img_tk_avatar: ImageView? = null
    private var email: String? = null
    private var taiKhoan: Taikhoan? = null
    private var rootView: View? = null // Đổi tên từ 'view' để tránh xung đột

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            email = requireArguments().getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_tai_khoan, container, false)
        Anhxa()
        val tki = ThongTinTaiKhoan()
        val user = FirebaseAuth.getInstance().currentUser
        tki.email = user!!.email
        tki.gettaikhoan(user.email)
        Handler().postDelayed({ tv_username!!.text = tki.tk!!.hoten.toString() }, 500)
        setOnClickListener()
        return rootView
    }

    private fun Anhxa() {
        tv_tongngaydiemdanh = rootView!!.findViewById(R.id.tv_tongngaydiemdanh)
        img_tk_avatar = rootView!!.findViewById(R.id.img_tk_avatar)
        tv_binhluancuatoi = rootView!!.findViewById(R.id.tv_binhluancuatoi)
        tv_danhgiacuatoi = rootView!!.findViewById(R.id.tv_danhgiacuatoi)
        tv_doimatkhau = rootView!!.findViewById(R.id.tv_doimatkhau)
        tv_dangxuat = rootView!!.findViewById(R.id.tv_dangxuat)
        tv_tttk = rootView!!.findViewById(R.id.tv_tttk)
        tv_username = rootView!!.findViewById(R.id.tv_username)
    }

    private fun setOnClickListener() {
        tv_danhgiacuatoi!!.setOnClickListener(this)
        tv_binhluancuatoi!!.setOnClickListener(this)
        tv_doimatkhau!!.setOnClickListener(this)
        tv_dangxuat!!.setOnClickListener(this)
        tv_tttk!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.tv_binhluancuatoi -> {
                val intent = Intent(activity, BinhLuanCuaToi::class.java)
                startActivity(intent)
            }
            R.id.tv_danhgiacuatoi -> {
                val intent = Intent(activity, DanhGiaCuaToi::class.java)
                startActivity(intent)
            }
            R.id.tv_doimatkhau -> {
                val intent = Intent(activity, DoiMatKhau::class.java)
                startActivity(intent)
            }
            R.id.tv_dangxuat -> {
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(activity, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                Toast.makeText(requireContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                requireActivity().finish()
            }
            R.id.tv_tttk -> {
                val intent = Intent(activity, ThongTinTaiKhoan::class.java)
                startActivity(intent)
            }
        }
    }

    companion object {
        private const val ARG_PARAM1 = "email"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param email Parameter 1.
         * @return A new instance of fragment TaiKhoanFragment.
         */
        fun newInstance(email: String?): TaiKhoanFragment {
            val fragment = TaiKhoanFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, email)
            fragment.arguments = args
            return fragment
        }
    }
}
