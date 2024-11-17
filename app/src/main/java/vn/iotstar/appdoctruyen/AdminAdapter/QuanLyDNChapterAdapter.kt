package vn.iotstar.appdoctruyen.AdminAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.iotstar.appdoctruyen.AdminAdapter.QuanLyDNChapterAdapter.QuanLyNDChapterViewHolder
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.Noidungchapter

class QuanLyDNChapterAdapter(private val context: Context, private val list: List<Noidungchapter>?) :
    RecyclerView.Adapter<QuanLyNDChapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuanLyNDChapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_qlthongke, parent, false)
        return QuanLyNDChapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuanLyNDChapterViewHolder, position: Int) {
        val noiDungChapter = list!![position] ?: return
        holder.tv_idqlthongke.text = "" + noiDungChapter.id
        holder.tv_qltktentruyen.text = noiDungChapter.linkanh
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class QuanLyNDChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_idqlthongke: TextView
        val tv_qltktentruyen: TextView

        init {
            tv_idqlthongke = itemView.findViewById(R.id.tv_idqlthongke)
            tv_qltktentruyen = itemView.findViewById(R.id.tv_qltktentruyen)
        }
    }
}
