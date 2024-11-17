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
import vn.iotstar.appdoctruyen.Adapter.TheLoaiAdapter.TheLoaiViewHolder
import vn.iotstar.appdoctruyen.CTTruyen
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.PhanLoaiTruyen

class TheLoaiAdapter(private val context: Context, private val list: List<PhanLoaiTruyen>?, private val email: String) :
    RecyclerView.Adapter<TheLoaiViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheLoaiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_theloainew, parent, false)
        return TheLoaiViewHolder(view)
    }

    override fun onBindViewHolder(holder: TheLoaiViewHolder, position: Int) {
//        PhanLoaiTruyen truyen=list.get(position);
//        if(truyen==null) {
//            return;
//        }
//        Glide.with(this.context).load(truyen.getLinkanh()).into(holder.img_theloai);
//        holder.tv_tentruyen.setText(truyen.getTentruyen());
//        holder.tv_pl.setText("Ngày đăng: "+truyen.getNgaydang());
//        holder.ll_rcv_theloai.setOnClickListener(view -> {
//            Intent intent=new Intent(holder.itemView.getContext(), ChiTietTruyen.class);
//            intent.putExtra("id_truyen",truyen.getId());
//            intent.putExtra("email",email);
//            holder.itemView.getContext().startActivity(intent);
//        });
        val phanLoaiTruyen = list!![position] ?: return
        holder.tv_tentruyen.text = phanLoaiTruyen.tentruyen
        holder.tv_pl.text = "Ngày đăng: " + phanLoaiTruyen.ngaydang
        Glide.with(context).load(phanLoaiTruyen.linkanh).into(holder.img_theloai)
        holder.ll_rcv_theloai.setOnClickListener { view: View? ->
            val intent = Intent(holder.itemView.context, CTTruyen::class.java)
            intent.putExtra("id_truyen", phanLoaiTruyen.id)
            intent.putExtra("email", email)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    class TheLoaiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
