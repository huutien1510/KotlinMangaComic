package vn.iotstar.appdoctruyen.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.iotstar.appdoctruyen.Adapter.BinhLuanCuaToiAdapter.BinhLuanCuaToiViewHolder
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.BinhLuanCuaToiDto

class BinhLuanCuaToiAdapter(
    private val context: Context,
    private val layout: Int,
    private val list: List<BinhLuanCuaToiDto>?
) : RecyclerView.Adapter<BinhLuanCuaToiViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinhLuanCuaToiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_binhluancuatoi, parent, false)
        return BinhLuanCuaToiViewHolder(view)
    }

    override fun onBindViewHolder(holder: BinhLuanCuaToiViewHolder, position: Int) {
        val binhluan = list!![position] ?: return
        holder.tv_tentruyen.text = binhluan.idchapter.toString()
        holder.tv_chapter.text = "Chapter: " + binhluan.idchapter
        holder.tv_noidung.text = "Nội dung: " + binhluan.noidung
        holder.tv_ngaydang.text = "Ngày đăng: " + binhluan.ngaydang
        //Glide.with(this.context).load(truyenVotes.getLinkanh()).into(holder.img_theloai);
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class BinhLuanCuaToiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_tentruyen: TextView
        var tv_chapter: TextView
        var tv_noidung: TextView
        var tv_ngaydang: TextView
        var img_truyen: ImageView

        init {
            tv_tentruyen = itemView.findViewById(R.id.tv_tentruyen)
            tv_chapter = itemView.findViewById(R.id.tv_chapter)
            tv_noidung = itemView.findViewById(R.id.tv_binhluan)
            tv_ngaydang = itemView.findViewById(R.id.tv_ngaydang)
            img_truyen = itemView.findViewById(R.id.img_truyen)
        }
    }
}