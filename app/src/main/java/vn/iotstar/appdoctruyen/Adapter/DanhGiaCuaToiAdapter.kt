package vn.iotstar.appdoctruyen.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.iotstar.appdoctruyen.Adapter.DanhGiaCuaToiAdapter.DanhGiaCuaToiViewHolder
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.DanhGiaCuaToiDto

class DanhGiaCuaToiAdapter(
    private val context: Context,
    private val layout: Int,
    private val list: List<DanhGiaCuaToiDto>?
) : RecyclerView.Adapter<DanhGiaCuaToiViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DanhGiaCuaToiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_danhgiacuatoi, parent, false)
        return DanhGiaCuaToiViewHolder(view)
    }

    override fun onBindViewHolder(holder: DanhGiaCuaToiViewHolder, position: Int) {
        val danhgia = list!![position] ?: return
        holder.tv_tentruyen.text = danhgia.idchapter.toString()
        holder.tv_chapter.text = "Chapter: " + danhgia.idchapter
        holder.tv_pl.text = "Đánh giá: " + danhgia.sosao + "sao"
        holder.tv_ngaydang.text = "Ngày đăng: " + danhgia.ngaydanhgia
        //Glide.with(this.context).load(truyenVotes.getLinkanh()).into(holder.img_theloai);
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class DanhGiaCuaToiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_tentruyen: TextView
        var tv_chapter: TextView
        var tv_pl: TextView
        var tv_ngaydang: TextView
        var img_truyen: ImageView

        init {
            tv_tentruyen = itemView.findViewById(R.id.tv_tong_tentruyen)
            tv_chapter = itemView.findViewById(R.id.tv_tong_tenchapter)
            tv_pl = itemView.findViewById(R.id.tv_tong_pl)
            tv_ngaydang = itemView.findViewById(R.id.tv_tong_ngaydang)
            img_truyen = itemView.findViewById(R.id.img_tong_truyen)
        }
    }
}