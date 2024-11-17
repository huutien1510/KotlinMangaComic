package vn.iotstar.appdoctruyen.model

import java.io.Serializable

class truyen : Serializable {
    var id: Int? = null
    var tentruyen: String? = null
    var tacgia: String? = null
    var mota: String? = null
    var theloai: String? = null
    @JvmField
    var linkanh: String? = null
    var trangthai: Int? = null
    var keySearch: String? = null
    override fun toString(): String {
        return "truyen{" +
                "id=" + id +
                ", tentruyen='" + tentruyen + '\'' +
                ", tacgia='" + tacgia + '\'' +
                ", mota='" + mota + '\'' +
                ", theloai='" + theloai + '\'' +
                ", linkanh='" + linkanh + '\'' +
                ", trangthai=" + trangthai +
                ", keySearch='" + keySearch + '\'' +
                '}'
    }

    constructor(
        tentruyen: String?,
        tacgia: String?,
        mota: String?,
        theloai: String?,
        linkanh: String?,
        keySearch: String?
    ) {
        this.tentruyen = tentruyen
        this.tacgia = tacgia
        this.mota = mota
        this.theloai = theloai
        this.linkanh = linkanh
        this.keySearch = keySearch
    }

    constructor(
        tentruyen: String?,
        tacgia: String?,
        mota: String?,
        theloai: String?,
        linkanh: String?,
        trangthai: Int?,
        keySearch: String?
    ) {
        this.tentruyen = tentruyen
        this.tacgia = tacgia
        this.mota = mota
        this.theloai = theloai
        this.linkanh = linkanh
        this.trangthai = trangthai
        this.keySearch = keySearch
    }

    constructor()
    constructor(
        id: Int?,
        tentruyen: String?,
        tacgia: String?,
        mota: String?,
        theloai: String?,
        linkanh: String?,
        trangthai: Int?,
        keySearch: String?
    ) {
        this.id = id
        this.tentruyen = tentruyen
        this.tacgia = tacgia
        this.mota = mota
        this.theloai = theloai
        this.linkanh = linkanh
        this.trangthai = trangthai
        this.keySearch = keySearch
    }
}
