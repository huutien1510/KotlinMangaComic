package vn.iotstar.appdoctruyen.AdminAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.iotstar.appdoctruyen.AdminAdapter.QuanLyTruyenAdapter.QuanLyTruyenViewHolder
import vn.iotstar.appdoctruyen.AdminController.ShowThongTinTruyen
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.truyen

class QuanLyTruyenAdapter(private val context: Context, private val mList: List<truyen>?) :
    RecyclerView.Adapter<QuanLyTruyenViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuanLyTruyenViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_qlthongke, parent, false)
        return QuanLyTruyenViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuanLyTruyenViewHolder, position: Int) {
        val Truyen = mList!![position] ?: return
        holder.tv_idtruyen.text = "" + Truyen.id
        holder.tv_tentruyen.text = Truyen.tentruyen
        holder.ll_rcv_qltruyen.setOnClickListener { view: View? ->
            val intent = Intent(holder.itemView.context, ShowThongTinTruyen::class.java)
            intent.putExtra("id_truyen", Truyen.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mList?.size ?: 0
    }

    class QuanLyTruyenViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_idtruyen: TextView
        val tv_tentruyen: TextView
        val ll_rcv_qltruyen: LinearLayout

        init {
            tv_idtruyen = itemView.findViewById(R.id.tv_idqlthongke)
            tv_tentruyen = itemView.findViewById(R.id.tv_qltktentruyen)
            ll_rcv_qltruyen = itemView.findViewById(R.id.ll_rcv_qlthongke)
        }
    }
}
