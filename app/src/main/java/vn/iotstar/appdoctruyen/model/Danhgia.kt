package vn.iotstar.appdoctruyen.model

import lombok.Getter
import lombok.Setter
import java.time.LocalDate

@Getter
@Setter
class Danhgia {
    private var id: Int? = null
    private var idchapter: Chapter? = null
    private var idtaikhoan: Taikhoan? = null

    constructor(id: Int?, idchapter: Chapter?, idtaikhoan: Taikhoan?, sosao: Double?, ngaydanhgia: LocalDate?) {
        this.id = id
        this.idchapter = idchapter
        this.idtaikhoan = idtaikhoan
        this.sosao = sosao
        this.ngaydanhgia = ngaydanhgia
    }

    constructor()

    private var sosao: Double? = null
    private var ngaydanhgia: LocalDate? = null
}