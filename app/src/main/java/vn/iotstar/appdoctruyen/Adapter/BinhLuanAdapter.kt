package vn.iotstar.appdoctruyen.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.iotstar.appdoctruyen.Adapter.BinhLuanAdapter.BinhLuanViewHolder
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.BinhLuanTruyenDto

class BinhLuanAdapter(private val context: Context, private val list: List<BinhLuanTruyenDto>?) :
    RecyclerView.Adapter<BinhLuanViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinhLuanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_binhluan, parent, false)
        return BinhLuanViewHolder(view)
    }

    override fun onBindViewHolder(holder: BinhLuanViewHolder, position: Int) {
        val binhLuan = list!![position] ?: return
        Glide.with(context).load(binhLuan.linkAnh).into(holder.img_avatar)
        holder.tv_taikhoan_bl.text = binhLuan.email
        holder.tv_nd_bl.text = binhLuan.noidung
        holder.tv_ngaybinhluan.text = binhLuan.ngaydang
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class BinhLuanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_taikhoan_bl: TextView
        val tv_nd_bl: TextView
        val tv_ngaybinhluan: TextView
        private val tv_: TextView? = null
        val img_avatar: ImageView

        init {
            tv_nd_bl = itemView.findViewById(R.id.tv_nd_bl)
            tv_taikhoan_bl = itemView.findViewById(R.id.tv_taikhoan_bl)
            tv_ngaybinhluan = itemView.findViewById(R.id.tv_ngaybinhluan)
            img_avatar = itemView.findViewById(R.id.img_avatar)
        }
    }
}
