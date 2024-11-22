package vn.iotstar.appdoctruyen.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.DanhGiaCuaToiDto

class DanhGiaCuaToiAdapter(
    private val context: Context,
    private val layout: Int,
    private val list: List<DanhGiaCuaToiDto>?
) : RecyclerView.Adapter<DanhGiaCuaToiAdapter.DanhGiaCuaToiViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DanhGiaCuaToiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return DanhGiaCuaToiViewHolder(view)
    }

    override fun onBindViewHolder(holder: DanhGiaCuaToiViewHolder, position: Int) {
        val danhGia = list?.get(position) ?: return

        // Gán dữ liệu vào các view
        holder.tv_tentruyen.text = danhGia.tenTruyen
        holder.tv_chapter.text = "Chapter: ${danhGia.tenChapter}"
        holder.tv_pl.text = "Đánh giá: ${danhGia.sosao} sao"
        holder.tv_ngaydang.text = "Ngày đăng: ${danhGia.ngaydanhgia}"

        // Load ảnh truyện
        Glide.with(context)
            .load(danhGia.linkAnh)
            .placeholder(R.drawable.ic_launcher_foreground) // Ảnh tạm khi chưa load xong
            .into(holder.img_truyen)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class DanhGiaCuaToiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_tentruyen: TextView = itemView.findViewById(R.id.tv_tong_tentruyen)
        val tv_chapter: TextView = itemView.findViewById(R.id.tv_tong_tenchapter)
        val tv_pl: TextView = itemView.findViewById(R.id.tv_tong_pl)
        val tv_ngaydang: TextView = itemView.findViewById(R.id.tv_tong_ngaydang)
        val img_truyen: ImageView = itemView.findViewById(R.id.img_tong_truyen)
    }
}
