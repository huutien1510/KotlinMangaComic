package vn.iotstar.appdoctruyen.model

import java.io.Serializable

class ChapterAdmin : Serializable {
    var id: Int? = null
    var idtruyen: Int
    var tenchapter: String
    var ngaydang: String
    var soluotxem: Int
    var danhgia: Double

    constructor(idtruyen: Int, tenchapter: String, ngaydang: String, soluotxem: Int, danhgia: Double) {
        this.idtruyen = idtruyen
        this.tenchapter = tenchapter
        this.ngaydang = ngaydang
        this.soluotxem = soluotxem
        this.danhgia = danhgia
    }

    constructor(id: Int?, idtruyen: Int, tenchapter: String, ngaydang: String, soluotxem: Int, danhgia: Double) {
        this.id = id
        this.idtruyen = idtruyen
        this.tenchapter = tenchapter
        this.ngaydang = ngaydang
        this.soluotxem = soluotxem
        this.danhgia = danhgia
    }
}
