package vn.iotstar.appdoctruyen.AdminAdapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import vn.iotstar.appdoctruyen.AdminAdapter.QuanLyThongKeAdapter.QuanLyThongKeViewHolder
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.Thongke

class QuanLyThongKeAdapter(private val context: Context, private val list: List<Thongke>) :
    RecyclerView.Adapter<QuanLyThongKeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuanLyThongKeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_qlthongke, parent, false)
        return QuanLyThongKeViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuanLyThongKeViewHolder, position: Int) {
        val thongKe = list[position] ?: return
        holder.tv_idqlthongke.text = "" + thongKe.id
        //Truyen truyen=db.getTruyenById(thongKe.getIdtruyen());

//        holder.tv_qltktentruyen.setText(truyen.getTentruyen());
    }

    override fun getItemCount(): Int {
        return 0
    }

    inner class QuanLyThongKeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_idqlthongke: TextView
        private val tv_qltktentruyen: TextView
        private val ll_rcv_qlthongke: LinearLayout

        init {
            tv_idqlthongke = itemView.findViewById(R.id.tv_idqlthongke)
            tv_qltktentruyen = itemView.findViewById(R.id.tv_qltktentruyen)
            ll_rcv_qlthongke = itemView.findViewById(R.id.ll_rcv_qlthongke)
        }
    }
}
