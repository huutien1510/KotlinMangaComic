package vn.iotstar.appdoctruyen.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.iotstar.appdoctruyen.Adapter.DocChapterAdapter.DocChapterViewHolder
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.NoiDungChapterDto

class DocChapterAdapter(private val list: List<NoiDungChapterDto>?, private val context: Context) :
    RecyclerView.Adapter<DocChapterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocChapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_docchapter, parent, false)
        return DocChapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: DocChapterViewHolder, position: Int) {
        val noiDungChapter = list!![position] ?: return
        Glide.with(context).load(noiDungChapter.linkanh).into(holder.img_docchapter)
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class DocChapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_docchapter: ImageView

        init {
            img_docchapter = itemView.findViewById(R.id.img_docchapter)
        }
    }
}
