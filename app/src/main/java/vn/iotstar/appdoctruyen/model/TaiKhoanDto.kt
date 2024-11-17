package vn.iotstar.appdoctruyen.model

class TaiKhoanDto {
    @JvmField
    var id: Int? = null
    @JvmField
    var email: String? = null
    var matkhau: String? = null
    @JvmField
    var hoten: String? = null
    @JvmField
    var dienthoai: String? = null
    @JvmField
    var diemthuong: Int? = null
    var loaitaikhoan: Int? = null

    constructor()
    constructor(
        email: String?,
        matkhau: String?,
        hoten: String?,
        dienthoai: String?,
        diemthuong: Int?,
        loaitaikhoan: Int?
    ) {
        this.email = email
        this.matkhau = matkhau
        this.hoten = hoten
        this.dienthoai = dienthoai
        this.diemthuong = diemthuong
        this.loaitaikhoan = loaitaikhoan
    }

    constructor(
        id: Int?,
        email: String?,
        matkhau: String?,
        hoten: String?,
        dienthoai: String?,
        diemthuong: Int?,
        loaitaikhoan: Int?
    ) {
        this.id = id
        this.email = email
        this.matkhau = matkhau
        this.hoten = hoten
        this.dienthoai = dienthoai
        this.diemthuong = diemthuong
        this.loaitaikhoan = loaitaikhoan
    }
}
