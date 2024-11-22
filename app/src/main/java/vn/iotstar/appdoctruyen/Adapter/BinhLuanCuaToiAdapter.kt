package vn.iotstar.appdoctruyen.Adapter

import android.content.Context
import retrofit2.Call
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.BinhLuanCuaToiAdapter.BinhLuanCuaToiViewHolder
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.BinhLuanCuaToiDto
import vn.iotstar.appdoctruyen.model.Truyen1
import retrofit2.Callback
import vn.iotstar.appdoctruyen.model.ChapterDto

class BinhLuanCuaToiAdapter(
    private val context: Context,
    private val layout: Int,
    private val list: List<BinhLuanCuaToiDto>?
) : RecyclerView.Adapter<BinhLuanCuaToiViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BinhLuanCuaToiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_binhluancuatoi, parent, false)
        return BinhLuanCuaToiViewHolder(view)
    }

    override fun onBindViewHolder(holder: BinhLuanCuaToiViewHolder, position: Int) {
        val binhluan = list!![position] ?: return

        holder.tv_chapter.text = "Chapter: " + binhluan.idchapter
        holder.tv_noidung.text = "Nội dung: " + binhluan.noidung
        holder.tv_ngaydang.text = "Ngày đăng: " + binhluan.ngaydang

        val idchapter = binhluan.idchapter

        // Gọi API để lấy tên truyện
        APIService.apiService.getOneTruyen(idchapter)?.enqueue(object : Callback<Truyen1> {
            override fun onResponse(call: Call<Truyen1>, response: Response<Truyen1>) {
                if (response.isSuccessful) {
                    val truyen = response.body()
                    holder.tv_tentruyen.text = truyen?.tentruyen ?: "Tên truyện không xác định"
                    Glide.with(context)
                        .load(truyen?.linkanh)
                        .into(holder.img_truyen)
                } else {
                    holder.tv_tentruyen.text = "Không tìm thấy truyện"
                }
            }

            override fun onFailure(call: Call<Truyen1>, t: Throwable) {
                holder.tv_tentruyen.text = "Lỗi khi lấy tên truyện"
            }
        })

        // Gọi API để lấy thông tin chapter
        APIService.apiService.getOneChapter(idchapter)?.enqueue(object : Callback<ChapterDto> {
            override fun onResponse(call: Call<ChapterDto>, response: Response<ChapterDto>) {
                if (response.isSuccessful) {
                    val chapter = response.body()
                    holder.tv_chapter.text = "Chapter: ${chapter?.tenchapter ?: "Không xác định"}"
                } else {
                    holder.tv_chapter.text = "Không tìm thấy chapter"
                }
            }

            override fun onFailure(call: Call<ChapterDto>, t: Throwable) {
                holder.tv_chapter.text = "Lỗi khi lấy chapter"
            }
        })
    }




    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class BinhLuanCuaToiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_tentruyen: TextView
        var tv_chapter: TextView
        var tv_noidung: TextView
        var tv_ngaydang: TextView
        var img_truyen: ImageView

        init {
            tv_tentruyen = itemView.findViewById(R.id.tv_tentruyen)
            tv_chapter = itemView.findViewById(R.id.tv_chapter)
            tv_noidung = itemView.findViewById(R.id.tv_binhluan)
            tv_ngaydang = itemView.findViewById(R.id.tv_ngaydang)
            img_truyen = itemView.findViewById(R.id.img_truyen)
        }
    }
}