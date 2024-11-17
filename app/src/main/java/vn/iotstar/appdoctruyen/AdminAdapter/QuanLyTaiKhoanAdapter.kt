package vn.iotstar.appdoctruyen.AdminAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import vn.iotstar.appdoctruyen.API.APIService
import vn.iotstar.appdoctruyen.AdminAdapter.QuanLyTaiKhoanAdapter.QuanLyTaiKhoanViewHolder
import vn.iotstar.appdoctruyen.AdminController.ShowThongTinTaiKhoan
import vn.iotstar.appdoctruyen.R
import vn.iotstar.appdoctruyen.model.Taikhoan

class QuanLyTaiKhoanAdapter(private val context: Context, private val list: List<Taikhoan?>?) :
    RecyclerView.Adapter<QuanLyTaiKhoanViewHolder>() {
    private val trangthai: Int? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuanLyTaiKhoanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcv_qltaikhoan, parent, false)
        return QuanLyTaiKhoanViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuanLyTaiKhoanViewHolder, position: Int) {
        val taiKhoan = list!![position] ?: return
        holder.tv_id.text = "" + taiKhoan.id
        holder.tv_email.text = taiKhoan.email
        val trangthai = taiKhoan.loaitk
        if (trangthai != 2) {
            holder.bt_hien.visibility = View.GONE
            holder.bt_an.visibility = View.VISIBLE
            holder.tv_trangthai.text = "Hoạt động"
        } else {
            holder.bt_hien.visibility = View.VISIBLE
            holder.bt_an.visibility = View.GONE
            holder.tv_trangthai.text = "Bị khóa"
        }
        holder.ll_rcv_qltaikhoan.setOnClickListener { view: View? ->
            val intent = Intent(holder.itemView.context, ShowThongTinTaiKhoan::class.java)
            intent.putExtra("email", taiKhoan.email)
            holder.itemView.context.startActivity(intent)
        }
        holder.bt_an.setOnClickListener { view: View? ->
            //db.updateTrangThai(taiKhoan.getId(),2);
            // @PUT("taikhoan/{id}/{loaitk}")
            //    Call<Taikhoan> updateLoaiTk(@Path("id") int id, @Path("loaitk") int loaitk);
            APIService.apiService.updateLoaiTk(taiKhoan.id, 2)?.enqueue(object : Callback<Taikhoan?> {
                override fun onResponse(call: Call<Taikhoan?>, response: Response<Taikhoan?>) {
                    Toast.makeText(context, "Khóa tài khoản thành công", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Taikhoan?>, t: Throwable) {
                    Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
                }
            })
            holder.bt_hien.visibility = View.VISIBLE
            holder.bt_an.visibility = View.GONE
            holder.tv_trangthai.text = "Bị khóa"
        }
        holder.bt_hien.setOnClickListener { view: View? ->
            //db.updateTrangThai(taiKhoan.getId(),0);
            //@PUT("taikhoan/{id}/{loaitk}")
            //    Call<Taikhoan> updateLoaiTk(@Path("id") int id, @Path("loaitk") int loaitk);
            APIService.apiService.updateLoaiTk(taiKhoan.id, 0)?.enqueue(object : Callback<Taikhoan?> {
                override fun onResponse(call: Call<Taikhoan?>, response: Response<Taikhoan?>) {
                    Toast.makeText(context, "Mở khóa thành công", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(call: Call<Taikhoan?>, t: Throwable) {
                    Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show()
                }
            })
            holder.bt_hien.visibility = View.GONE
            holder.bt_an.visibility = View.VISIBLE
            holder.tv_trangthai.text = "Hoạt động"
        }


//        APIService.apiService.getTaiKhoan().enqueue(new Callback<List<Taikhoan>>() {
//            @Override
//            public void onResponse(Call<List<Taikhoan>> call, Response<List<Taikhoan>> response) {
//                list = response.body();
//                if (list != null) {
//                    if (list.get(0).getLoaitk()!= 2){
//                        holder.bt_hien.setVisibility(View.GONE);
//                        holder.bt_an.setVisibility(View.VISIBLE);
//                        holder.tv_trangthai.setText("Hoạt động");
//                    }else{
//                        holder.bt_hien.setVisibility(View.VISIBLE);
//                        holder.bt_an.setVisibility(View.GONE);
//                        holder.tv_trangthai.setText("Bị khóa");
//                    }
//
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Taikhoan>> call, Throwable throwable) {
//                Toast.makeText(context, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
//            }
//        });
//        if(trangthai!=2){
//            holder.bt_hien.setVisibility(View.GONE);
//            holder.bt_an.setVisibility(View.VISIBLE);
//            holder.tv_trangthai.setText("Hoạt động");
//        }else {
//            holder.bt_hien.setVisibility(View.VISIBLE);
//            holder.bt_an.setVisibility(View.GONE);
//            holder.tv_trangthai.setText("Bị khóa");
//        }
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }

    inner class QuanLyTaiKhoanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_id: TextView
        val tv_email: TextView
        val tv_trangthai: TextView
        val bt_an: Button
        val bt_hien: Button
        val ll_rcv_qltaikhoan: LinearLayout

        init {
            tv_id = itemView.findViewById(R.id.tv_idqltaikhoan)
            tv_email = itemView.findViewById(R.id.tv_emailqltaikhoan)
            tv_trangthai = itemView.findViewById(R.id.tv_trangthaiqltaikhoan)
            bt_an = itemView.findViewById(R.id.bt_anqltaikhoan)
            bt_hien = itemView.findViewById(R.id.bt_hienqltaikhoan)
            ll_rcv_qltaikhoan = itemView.findViewById(R.id.ll_rcv_qltaikhoan)
        }
    }
}
