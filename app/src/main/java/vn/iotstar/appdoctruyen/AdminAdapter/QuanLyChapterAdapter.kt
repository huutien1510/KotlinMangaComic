package vn.iotstar.appdoctruyen.AdminAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.iotstar.appdoctruyen.AdminAdapter.QuanLyChapterAdapter.QuanLyChapterViewHolder
import vn.iotstar.appdoctruyen.AdminController.ShowThongTinChapter
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.ChapterAdmin

class QuanLyChapterAdapter(private val context: Context, private val list: List<ChapterAdmin>?) :
    RecyclerView.Adapter<QuanLyChapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuanLyChapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_qlthongke, parent, false)
        return QuanLyChapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuanLyChapterViewHolder, position: Int) {
        val chapter = list!![position] ?: return
        holder.tv_idqlthongke.text = "" + chapter.id
        holder.tv_qltktentruyen.text = chapter.tenchapter
        holder.ll_rcv_qlthongke.setOnClickListener { view: View? ->
            val intent = Intent(holder.itemView.context, ShowThongTinChapter::class.java)
            intent.putExtra("id_chapter", chapter.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class QuanLyChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_idqlthongke: TextView
        val tv_qltktentruyen: TextView
        val ll_rcv_qlthongke: LinearLayout

        init {
            tv_idqlthongke = itemView.findViewById(R.id.tv_idqlthongke)
            tv_qltktentruyen = itemView.findViewById(R.id.tv_qltktentruyen)
            ll_rcv_qlthongke = itemView.findViewById(R.id.ll_rcv_qlthongke)
        }
    }
}
