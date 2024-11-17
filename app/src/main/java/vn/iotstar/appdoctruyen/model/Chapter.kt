package vn.iotstar.appdoctruyen.model

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDate

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class Chapter {
    var id: Int? = null
    var idtruyen: truyen? = null
    var tenchapter: String
    var ngaydang: LocalDate
    var soluotxem: Int
    var danhgia: Double
    var binhluans: Set<Binhluan>? = null
    var danhgias: Set<Danhgia>? = null
    var lichsudoctruyens: Set<Lichsudoctruyen>? = null
    var noidungchapters: Set<Noidungchapter>? = null

    constructor(idtruyen: truyen?, tenchapter: String, ngaydang: LocalDate, soluotxem: Int, danhgia: Double) {
        this.idtruyen = idtruyen
        this.tenchapter = tenchapter
        this.ngaydang = ngaydang
        this.soluotxem = soluotxem
        this.danhgia = danhgia
    }

    constructor(tenchapter: String, ngaydang: LocalDate?, soluotxem: Int, danhgia: Double) {
        this.tenchapter = tenchapter
        this.ngaydang = ngaydang!!
        this.soluotxem = soluotxem
        this.danhgia = danhgia
    }
}