package vn.iotstar.appdoctruyen

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import vn.iotstar.appdoctruyen.model.Taikhoan

class GoiYTruyenFragment : Fragment(), View.OnClickListener {
    var taiKhoan: Taikhoan? = null
    var email: String? = null
    var tv_tentruyenmoi: TextView? = null
    var tv_tentruyenluotxem: TextView? = null
    var tv_tentruyendanhgia: TextView? = null
    var tv_theloaimoi: TextView? = null
    var tv_theloailuotxem: TextView? = null
    var tv_theloaidanhgia: TextView? = null
    var tv_ngaydang: TextView? = null
    var tv_luotxem: TextView? = null
    var tv_danhgia: TextView? = null
    var img_truyenmoi: ImageView? = null
    var img_truyenluotxem: ImageView? = null
    var img_truyendanhgia: ImageView? = null
    var rootView: View? = null // Đổi tên biến `view` thành `rootView`
    var cv_truyenmoi: CardView? = null
    var cv_truyenluotxem: CardView? = null
    var cv_truyendanhgia: CardView? = null
    var idmoi = 0
    var idluotxem = 0
    var iddanhgia = 0
    var theloai: String? = null

    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    fun Anhxa() {
        tv_tentruyenmoi = rootView!!.findViewById(R.id.tv_tentruyenmoi)
        tv_tentruyenluotxem = rootView!!.findViewById(R.id.tv_tentruyenluotxem)
        tv_tentruyendanhgia = rootView!!.findViewById(R.id.tv_tentruyendanhgia)
        tv_theloaimoi = rootView!!.findViewById(R.id.tv_theloaimoi)
        tv_theloailuotxem = rootView!!.findViewById(R.id.tv_theloailuotxem)
        tv_theloaidanhgia = rootView!!.findViewById(R.id.tv_theloaidanhgia)
        tv_ngaydang = rootView!!.findViewById(R.id.tv_ngaydang)
        tv_luotxem = rootView!!.findViewById(R.id.tv_luotxem)
        tv_danhgia = rootView!!.findViewById(R.id.tv_danhgia)
        img_truyenmoi = rootView!!.findViewById(R.id.img_truyenmoi)
        img_truyenluotxem = rootView!!.findViewById(R.id.img_truyenluotxem)
        img_truyendanhgia = rootView!!.findViewById(R.id.img_truyendanhgia)
        cv_truyenmoi = rootView!!.findViewById(R.id.cv_truyenmoi)
        cv_truyenluotxem = rootView!!.findViewById(R.id.cv_truyenluotxem)
        cv_truyendanhgia = rootView!!.findViewById(R.id.cv_truyendanhgia)
    }

    private fun setOnClickListener() {
        cv_truyendanhgia!!.setOnClickListener(this)
        cv_truyenluotxem!!.setOnClickListener(this)
        cv_truyenmoi!!.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        // Xử lý sự kiện click
    }

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        fun newInstance(param1: String?, param2: String?): GoiYTruyenFragment {
            val fragment = GoiYTruyenFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
