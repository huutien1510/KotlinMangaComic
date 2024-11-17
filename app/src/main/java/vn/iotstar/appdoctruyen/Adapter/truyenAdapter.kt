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
import vn.iotstar.appdoctruyen.Adapter.truyenAdapter.MyViewHolder
import vn.iotstar.appdoctruyen.CTTruyen
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.truyen

class truyenAdapter(private val context: Context, private var array: List<truyen>?, private val email: String) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rv, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val Truyen = array!![position] ?: return
        holder.tenTruyen.text = Truyen.tentruyen
        Glide.with(context).load(Truyen.linkanh).into(holder.images)
        holder.ll_rcv.setOnClickListener { view: View? ->
            val intent = Intent(holder.itemView.context, CTTruyen::class.java)
            intent.putExtra("id_truyen", Truyen.id)
            intent.putExtra("email", email)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return array?.size ?: 0
    }

    // Phương thức cập nhật dữ liệu
    fun setData(newData: List<truyen>?) {
        if (newData != null) {
            array = newData
            notifyDataSetChanged() // Cập nhật lại RecyclerView
        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var images: ImageView
        var tenTruyen: TextView
        val ll_rcv: LinearLayout

        init {
            images = itemView.findViewById<View>(R.id.img_truyen) as ImageView
            tenTruyen = itemView.findViewById<View>(R.id.tv_title) as TextView
            ll_rcv = itemView.findViewById(R.id.ll_rcv)
        }
    }
}
