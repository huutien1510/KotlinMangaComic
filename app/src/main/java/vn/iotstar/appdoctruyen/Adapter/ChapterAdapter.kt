package vn.iotstar.appdoctruyen.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import vn.iotstar.appdoctruyen.Adapter.ChapterAdapter.ChapterViewHolder
import vn.iotstar.appdoctruyen.DocChapter
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.ChapterDto

class ChapterAdapter(private val context: Context, private val list: List<ChapterDto>?, private val email: String?) :
    RecyclerView.Adapter<ChapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_chapter, parent, false)
        return ChapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        var user = FirebaseAuth.getInstance().currentUser
        val chapter = list!![position] ?: return
        holder.tv_chapter.text = chapter.tenchapter
        holder.tv_luotxem.text = "Lượt xem: " + chapter.soluotxem
        holder.tv_ngaydang.text = "Ngày đăng: " + chapter.ngaydang
        holder.ll_rcv_chapter.setOnClickListener { view: View? ->
            if (user != null) {
                val intent = Intent(holder.itemView.context, DocChapter::class.java)
                intent.putExtra("id_chapter", chapter.id)
                intent.putExtra("id_truyen", chapter.idtruyen)
                intent.putExtra("email", email)
                holder.itemView.context.startActivity(intent)
            } else {
                Toast.makeText(context, "Vui lòng đăng nhập để xem nội dung truyện!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class ChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_chapter: TextView
        val tv_ngaydang: TextView
        val tv_luotxem: TextView
        val ll_rcv_chapter: LinearLayout

        init {
            tv_chapter = itemView.findViewById(R.id.tv_chapter)
            tv_ngaydang = itemView.findViewById(R.id.tv_ngaydang)
            tv_luotxem = itemView.findViewById(R.id.tv_luotxem)
            ll_rcv_chapter = itemView.findViewById(R.id.ll_rcv_chapter)
        }
    }
}
