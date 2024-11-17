package vn.iotstar.appdoctruyen.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.iotstar.appdoctruyen.Adapter.BinhLuanTruyenAdapter.BinhLuanTruyenViewHolder
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.BinhLuanTruyenDto

class BinhLuanTruyenAdapter(private val context: Context, private val list: List<BinhLuanTruyenDto>?) :
    RecyclerView.Adapter<BinhLuanTruyenViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinhLuanTruyenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_binhluan_truyen, parent, false)
        return BinhLuanTruyenViewHolder(view)
    }

    override fun onBindViewHolder(holder: BinhLuanTruyenViewHolder, position: Int) {
        val binhLuan = list!![position] ?: return
        Glide.with(context).load(binhLuan.linkAnh).into(holder.img_avatar)
        holder.tv_taikhoan_blt.text = binhLuan.email
        holder.tv_nd_blt.text = binhLuan.noidung
        holder.tv_ngaybinhluant.text = binhLuan.ngaydang
        holder.tv_tenchapter_blt.text = binhLuan.tenChapter
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class BinhLuanTruyenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_taikhoan_blt: TextView
        val tv_nd_blt: TextView
        val tv_ngaybinhluant: TextView
        val tv_tenchapter_blt: TextView
        val img_avatar: ImageView

        init {
            tv_nd_blt = itemView.findViewById(R.id.tv_nd_blt)
            tv_taikhoan_blt = itemView.findViewById(R.id.tv_taikhoan_blt)
            tv_ngaybinhluant = itemView.findViewById(R.id.tv_ngaybinhluant)
            tv_tenchapter_blt = itemView.findViewById(R.id.tv_tenchapter_blt)
            img_avatar = itemView.findViewById(R.id.img_avatar)
        }
    }
}
