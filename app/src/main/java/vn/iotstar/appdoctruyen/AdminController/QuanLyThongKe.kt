package vn.iotstar.appdoctruyen.AdminController

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import vn.iotstar.appdoctruyen.AdminAdapter.QuanLyThongKeAdapter
import vn.iotstar.appdoctruyen.R

class QuanLyThongKe : AppCompatActivity() {
    private val rcv: RecyclerView? = null
    private val adapter: QuanLyThongKeAdapter? = null
    var tv_qltk_sltruyen: TextView? = null
    var tv_qltk_slchapter: TextView? = null
    var tv_qltk_slview: TextView? = null
    var tv_qltk_slvote: TextView? = null
    var tv_qltk_slcomment: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quan_ly_thong_ke)
    }
}