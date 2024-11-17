package vn.iotstar.appdoctruyen.model

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class Taikhoan {
    var id: Int? = null
    var email: String? = null
    var matkhau: String? = null
    var hoten: String? = null
    var dienthoai: String? = null
    var diemthuong: Int? = null
    var loaitk: Int? = null
    var linkanh: String? = null
    var binhluans: Set<Binhluan>? = null

    constructor(hoten: String?, dienthoai: String?) {
        this.hoten = hoten
        this.dienthoai = dienthoai
    }

    constructor(
        email: String?,
        matkhau: String?,
        hoten: String?,
        dienthoai: String?,
        diemthuong: Int?,
        loaitk: Int?,
        linkanh: String?
    ) {
        this.email = email
        this.matkhau = matkhau
        this.hoten = hoten
        this.dienthoai = dienthoai
        this.diemthuong = diemthuong
        this.loaitk = loaitk
        this.linkanh = linkanh
    }

    constructor()
    constructor(email: String?, matkhau: String?, hoten: String?, dienthoai: String?, diemthuong: Int?, loaitk: Int?) {
        this.email = email
        this.matkhau = matkhau
        this.hoten = hoten
        this.dienthoai = dienthoai
        this.diemthuong = diemthuong
        this.loaitk = loaitk
    }

    var danhgias: Set<Danhgia>? = null
    var diemthuongs: Set<Diemthuong>? = null
    var doithuongs: Set<Doithuong>? = null
    var lichsudoctruyens: Set<Lichsudoctruyen>? = null
}