package vn.iotstar.appdoctruyen.model

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class Thongke {
    var id: Int? = null
    var idtruyen: truyen? = null
    var tongluotxem: Int? = null
    var sosaotb: Double? = null

    constructor()
    constructor(id: Int?, idtruyen: truyen?, tongluotxem: Int?, sosaotb: Double?) {
        this.id = id
        this.idtruyen = idtruyen
        this.tongluotxem = tongluotxem
        this.sosaotb = sosaotb
    }
}