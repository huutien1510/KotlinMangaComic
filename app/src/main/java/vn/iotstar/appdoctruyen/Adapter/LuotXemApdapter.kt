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
import vn.iotstar.appdoctruyen.Adapter.LuotXemApdapter.LuotXemViewHolder
import vn.iotstar.appdoctruyen.CTTruyen
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.TruyenVotes

class LuotXemApdapter(private val context: Context, private val list: List<TruyenVotes>?, private val email: String) :
    RecyclerView.Adapter<LuotXemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LuotXemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_theloainew, parent, false)
        return LuotXemViewHolder(view)
    }

    override fun onBindViewHolder(holder: LuotXemViewHolder, position: Int) {
        val truyenVotes = list!![position] ?: return
        holder.tv_tentruyen.text = truyenVotes.tentruyen
        holder.tv_pl.text = "Tổng lượt xem: " + truyenVotes.tongluotxem
        Glide.with(context).load(truyenVotes.linkanh).into(holder.img_theloai)
        holder.ll_rcv_theloai.setOnClickListener { view: View? ->
            val intent = Intent(holder.itemView.context, CTTruyen::class.java)
            intent.putExtra("id_truyen", truyenVotes.id)
            intent.putExtra("email", email)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class LuotXemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_tentruyen: TextView
        val tv_pl: TextView
        val ll_rcv_theloai: LinearLayout
        val img_theloai: ImageView

        init {
            tv_tentruyen = itemView.findViewById(R.id.tv_theloai_tentruyen)
            tv_pl = itemView.findViewById(R.id.tv_theloai_ngaydang)
            ll_rcv_theloai = itemView.findViewById(R.id.ll_rcv_theloai)
            img_theloai = itemView.findViewById(R.id.img_theloai)
        }
    }
}
