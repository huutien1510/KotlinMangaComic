package vn.iotstar.appdoctruyen.API

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import vn.iotstar.appdoctruyen.model.*

interface APIService {

    @get:GET("truyen")
    val truyenAll: Call<List<truyen>>?

    @get:GET("theloai")
    val theLoai: Call<List<String>>?

    @get:GET("truyen/truyennewest")
    val newestComics: Call<List<PhanLoaiTruyen>>?

    @GET("truyen/truyennewest/{theloai}")
    fun getNewestComicsByTheLoai(@Path("theloai") theloai: String?): Call<List<PhanLoaiTruyen>>?

    @get:GET("truyen/truyenvotes")
    val voteComics: Call<List<TruyenVotes>>?

    @GET("truyen/truyenvotes/{theloai}")
    fun getVoteComicsByTheLoai(@Path("theloai") theloai: String?): Call<List<TruyenVotes>>?

    @get:GET("truyen/truyenview")
    val viewComics: Call<List<TruyenVotes>>?

    @GET("truyen/truyenview/{theloai}")
    fun getViewComicsByTheLoai(@Path("theloai") theloai: String): Call<List<TruyenVotes>>?

    @get:GET("truyen/toptruyenmoi")
    val truyenMoi: Call<List<truyen>>?

    @get:GET("truyen/toptruyen")
    val topTruyen: Call<List<truyen>>?

    @get:GET("truyen/tentruyen")
    val tenTruyen: Call<List<String>>?

    @POST("truyen")
    fun addTruyen(@Body truyen1: truyen): Call<truyen>?

    @GET("truyen/chapter/{id}")
    fun getChapterByIdAdmin(@Path("id") id: Int): Call<List<ChapterAdmin>>?

    @PUT("truyen/{id}")
    fun updateTruyen(@Body truyen: truyen, @Path("id") id: Int): Call<truyen>?

    @POST("truyen/{id}/chapterupdate")
    fun addChapter(@Path("id") id: Int, @Body chapter: Chapter): Call<Chapter>?

    @GET("truyen/chapter/noidung/{id}")
    fun getChapterContentById(@Path("id") id: Int): Call<List<ChapterAdmin>>?

    @GET("truyen/chapter/noidung/linkanh/{id}")
    fun getLinkChapterById(@Path("id") id: Int): Call<List<Noidungchapter>>?

    @POST("truyen/chapter/{id}/noidung")
    fun addLinkChapter(@Path("id") id: Int, @Body noidungchapter: Noidungchapter): Call<Noidungchapter>?

    @GET("truyen/gettruyen/{id}")
    fun getTruyenById(@Path("id") id: Int): Call<List<truyen>>?

    @GET("truyen/getbinhluan/{id}")
    fun getBinhLuan(@Path("id") id: Int): Call<List<BinhLuanTruyenDto>>?

    @GET("truyen/gettbdanhgia/{id}")
    fun getAverageRatingByTruyenId(@Path("id") id: Int): Call<Double>?

    @GET("truyen/gettongbinhluan/{id}")
    fun countBinhLuanByTruyenId(@Path("id") id: Int): Call<Long>?

    @GET("truyen/gettongluotxem/{id}")
    fun sumSoluotxemByTruyenId(@Path("id") id: Int): Call<Long>?

    @GET("truyen/getchapterbyidtruyen/{id}")
    fun getChapterById(@Path("id") id: Int): Call<List<ChapterDto>>?

    @GET("truyen/gettenchapter/{id}")
    fun getTenById(@Path("id") id: Int): Call<List<ChapterDto>>?

    @GET("truyen/getnoidungchapter/{id}")
    fun getNoiDungChapterById(@Path("id") id: Int): Call<List<NoiDungChapterDto>>?

    @PUT("/updateLuotXem/{id}")
    fun updateLuotXemChapter(@Path("id") id: Int): Call<Void>?

    @GET("truyen/getbinhluantheoIdChapter/{id}")
    fun getBinhLuanTheoIdChapter(@Path("id") id: Int): Call<List<BinhLuanTruyenDto>>?

    @GET("truyen/getminidchapter/{id}")
    fun getMinIdChapter(@Path("id") id: Int): Call<Int>?

    @GET("truyen/getmaxidchapter/{id}")
    fun getMaxIdChapter(@Path("id") id: Int): Call<Int>?

    @POST("addBinhLuan")
    fun themBinhLuan(@Body binhLuanDto: BinhLuanDto): Call<BinhLuanDto>?

    @GET("tusach/lichsu/{idtaikhoan}")
    fun getListTruyenDaDoc(@Path("idtaikhoan") idtaikhoan: Int): Call<List<LichSuDocTruyenModel>>?

    @GET("/truyen/getone/{idchapter}")
    fun getOneTruyen(@Path("idchapter") idchapter: Int?): Call<Truyen1>?

    @GET("/truyen/chapter/getone/{idchapter}")
    fun getOneChapter(@Path("idchapter") id: Int): Call<ChapterDto>?

    @GET("/truyen/chapter/tenchapter/{idtruyen}")
    fun getTenChapterNew(@Path("idtruyen") idtruyen: Int): Call<String>?

    @GET("/search")
    fun getListTimKiem(@Query("textsearch") textsearch: String): Call<ArrayList<Model_TimKiem>>?

    @get:GET("/taikhoan")
    val taiKhoan: Call<List<Taikhoan>>?

    @GET("timtaikhoan/{email}")
    fun getTaiKhoanByEmail(@Path("email") email: String?): Call<List<Taikhoan>>?

    @PUT("taikhoan/{id}/{loaitk}")
    fun updateLoaiTk(@Path("id") id: Int?, @Path("loaitk") loaitk: Int): Call<Taikhoan>?

    @POST("taikhoan")
    fun addTaiKhoan(@Body taikhoan: Taikhoan): Call<Taikhoan>?

    @GET("taikhoan/{email}")
    fun findByEmail(@Path("email") email: String?): Call<TaiKhoanDto>?

    @GET("/binhluancuatoi/{id}")
    fun findByIdn(@Path("id") id: Int?): Call<List<BinhLuanCuaToiDto>>?

    @GET("/danhgiacuatoi/{id}")
    fun findDanhGiaByIdn(@Path("id") id: Int?): Call<List<DanhGiaCuaToiDto>>?

    @GET("/timtaikhoan/{email}")
    fun findByEmail1(@Path("email") email: String?): Call<List<TaiKhoanDto>>?

    @GET("/findidtaikhoan/{email}")
    fun findIdTaiKhoan(@Path("email") email: String): Call<Int>?

    @GET("/getidbychapterandtk/{idchapter}/{idtaikhoan}")
    fun getIDByChapterAndTK(@Path("idchapter") idchapter: Int, @Path("idtaikhoan") idtaikhoan: Int): Call<List<Int>>?

    @PUT("/danhgia/{idchapter}/{idtaikhoan}/{sosao}")
    fun updateDanhGia(
        @Path("idchapter") idchapter: Int,
        @Path("idtaikhoan") idtaikhoan: Int,
        @Path("sosao") sosao: Double
    ): Call<Void>?

    @GET("/truyen/gettbdanhgiatheochapter/{id}")
    fun getAverageRatingByIdChapter(@Path("id") id: Int): Call<Double>?

    @PUT("taikhoan/{id}")
    fun updateTaiKhoan(@Body taikhoan: Taikhoan, @Path("id") id: Int): Call<Taikhoan>?

    companion object {
        val gson = GsonBuilder().setDateFormat("dd-MM-yyyy").create()

        val apiService = Retrofit.Builder()
            .baseUrl("http://192.168.1.103:8090")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService::class.java)
    }
}
