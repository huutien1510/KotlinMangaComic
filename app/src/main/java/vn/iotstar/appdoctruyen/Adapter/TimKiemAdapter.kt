package vn.iotstar.appdoctruyen.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.iotstar.appdoctruyen.Adapter.TimKiemAdapter.TimKiemViewHolder
import vn.iotstar.appdoctruyen.CTTruyen
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.Model_TimKiem

class TimKiemAdapter(
    private val context: Context,
    private val list: ArrayList<Model_TimKiem>?,
    private val email: String
) : RecyclerView.Adapter<TimKiemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimKiemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_timkiem, parent, false)
        return TimKiemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimKiemViewHolder, position: Int) {
        val truyen = list!![position] ?: return
        Glide.with(context).load(truyen.linkanh).into(holder.img_timkiem)
        holder.tv_timkiem_tentruyen.text = truyen.tentruyen
        holder.tv_timkiem_lx.text = "Lượt xem: " + truyen.luotxem
        holder.tv_timkiem_ch.text = "Chapter: " + truyen.chapter
        holder.tv_timkiem_dg.text = "Đánh giá: " + truyen.danhgia
        holder.tv_timkiem_theloai.text = truyen.theloai
        holder.ll_rcv_timkiem.setOnClickListener { view: View? ->
            val intent = Intent(holder.itemView.context, CTTruyen::class.java)
            intent.putExtra("email", email)
            intent.putExtra("id_truyen", truyen.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class TimKiemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_timkiem_tentruyen: TextView
        val tv_timkiem_lx: TextView
        val tv_timkiem_dg: TextView
        val tv_timkiem_ch: TextView
        val tv_timkiem_theloai: TextView
        val ll_rcv_timkiem: LinearLayout
        val img_timkiem: ImageView

        init {
            tv_timkiem_tentruyen = itemView.findViewById(R.id.tv_timkiem_tentruyen)
            tv_timkiem_dg = itemView.findViewById(R.id.tv_timkiem_dg)
            tv_timkiem_lx = itemView.findViewById(R.id.tv_timkiem_lx)
            tv_timkiem_ch = itemView.findViewById(R.id.tv_timkiem_ch)
            img_timkiem = itemView.findViewById(R.id.img_timkiem)
            ll_rcv_timkiem = itemView.findViewById(R.id.ll_rcv_timkiem)
            tv_timkiem_theloai = itemView.findViewById(R.id.tv_timkiem_theloai)
        }
    }
}
