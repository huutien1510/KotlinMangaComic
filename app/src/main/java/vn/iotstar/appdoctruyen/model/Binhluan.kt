package vn.iotstar.appdoctruyen.model

class Binhluan {
    var id: Int? = null
    var idchapter: Chapter? = null
    var idtaikhoan: Taikhoan? = null
    var noidung: String? = null
    var ngaydang: String? = null

    constructor(
        id: Int?,
        idchapter: Chapter?,
        idtaikhoan: Taikhoan?,
        noidung: String?,
        ngaydang: String?,
        trangthai: Int?
    ) {
        this.id = id
        this.idchapter = idchapter
        this.idtaikhoan = idtaikhoan
        this.noidung = noidung
        this.ngaydang = ngaydang
        this.trangthai = trangthai
    }

    constructor()

    var trangthai: Int? = null
}