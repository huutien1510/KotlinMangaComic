package vn.iotstar.appdoctruyen.Adapter

import android.content.Context
import android.os.Handler
import android.util.JsonReader
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.Adapter.TruyenDaDocAdapter.TruyenDaDocViewHolder
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.ChapterDto
import vn.iotstar.appdoctruyen.model.LichSuDocTruyenModel
import vn.iotstar.appdoctruyen.model.TaiKhoanDto
import vn.iotstar.appdoctruyen.model.Truyen1
import java.io.StringReader

class TruyenDaDocAdapter //     public TruyenDaDocAdapter(Context context, List<Lichsudoctruyen> list, TaiKhoanDto taikhoan) {
//         this.context = context;
//         this.list = list;
//         this.taikhoan = taikhoan;
    (
    private val context: Context, //private List<Lichsudoctruyen> list;
    private val list: List<LichSuDocTruyenModel>?, private val taiKhoan: TaiKhoanDto
) : RecyclerView.Adapter<TruyenDaDocViewHolder>() {
    private var chapter: ChapterDto? = null
    private var truyen: Truyen1? = null
    var tenchaptermoinhat: String? = null
    private var id = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TruyenDaDocViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rcv_truyendadoc, parent, false)
        return TruyenDaDocViewHolder(view)
    }

    override fun onBindViewHolder(holder: TruyenDaDocViewHolder, position: Int) {
        val truyendadoc = list!![position] ?: return
        val chapterId = truyendadoc.idchapter // Lấy idchapter của truyện hiện tại

        // Gọi API để lấy thông tin truyện
        getOneTruyen(chapterId) { truyen ->
            if (truyen != null) {
                // Sau khi lấy được thông tin truyện, tiếp tục gọi API lấy Chapter
                getOneChapter(chapterId) { chapter ->
                    if (chapter != null) {
                        // Hiển thị thông tin lên ViewHolder
                        Glide.with(context).load(truyen.linkanh).skipMemoryCache(true)
                            .into(holder.img_truyendadoc)
                        holder.tv_tentruyen.text = truyen.tentruyen
                        holder.tv_chapterdangxem.text = "Chapter đang xem: ${chapter.tenchapter}"
                    } else {
                        Toast.makeText(
                            context, "Không thể tải thông tin Chapter.", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(context, "Không thể tải thông tin truyện.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getOneChapter(id: Int, callback: (ChapterDto?) -> Unit) {
        APIService.apiService.getOneChapter(id)?.enqueue(object : Callback<ChapterDto?> {
            override fun onResponse(call: Call<ChapterDto?>, response: Response<ChapterDto?>) {
                if (response.isSuccessful) {
                    callback(response.body()) // Trả dữ liệu qua callback
                } else {
                    callback(null) // Trả về null nếu lỗi từ server
                }
            }

            override fun onFailure(call: Call<ChapterDto?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
                Toast.makeText(
                    context.applicationContext, "Lỗi kết nối: ${t.message}", Toast.LENGTH_SHORT
                ).show()
                callback(null) // Trả về null nếu lỗi kết nối
            }
        })
    }

    private fun getOneTruyen(chapter: Int?, callback: (Truyen1?) -> Unit) {
        APIService.apiService.getOneTruyen(chapter)?.enqueue(object : Callback<Truyen1?> {
            override fun onResponse(call: Call<Truyen1?>, response: Response<Truyen1?>) {
                if (response.isSuccessful) {
                    val truyen = response.body()
                    callback(truyen) // Trả kết quả thành công qua callback
                } else {
                    Log.e("API_CALL", "Error: ${response.code()} - ${response.message()}")
                    callback(null) // Trả về null nếu có lỗi HTTP
                }
            }

            override fun onFailure(call: Call<Truyen1?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
                Toast.makeText(context.applicationContext, "loi2: " + t.message, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun getTenChapterNew(id: Int) {
        APIService.apiService.getTenChapterNew(id)?.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                val jsonResponse = response.body() // JSON từ API
                val gson = Gson()
                val reader = JsonReader(StringReader(jsonResponse))
                reader.isLenient = true // Đặt chế độ lenient

                // Giải mã JSON
                tenchaptermoinhat = gson.fromJson(reader.toString(), String::class.java)
            }

            override fun onFailure(call: Call<String?>, t: Throwable) {
                Log.e("API_CALL", "Failed to fetch data from API", t)
                Toast.makeText(
                    context.applicationContext, "loi: " + id + t.message, Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class TruyenDaDocViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img_truyendadoc: ImageView
        val tv_tentruyen: TextView
        val tv_chapterdangxem: TextView

        init {
            img_truyendadoc = itemView.findViewById(R.id.img_truyendadoc)
            tv_tentruyen = itemView.findViewById(R.id.tv_tentruyen)
            tv_chapterdangxem = itemView.findViewById(R.id.tv_chapterdangxem)
        }
    }
}
