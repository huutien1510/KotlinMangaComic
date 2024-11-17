package vn.iotstar.appdoctruyen.model

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class Lichsudoctruyen {
    var id: Int? = null
    var idchapter: Chapter? = null
    var idtaikhoan: Taikhoan? = null

    constructor()
    constructor(id: Int?, idchapter: Chapter?, idtaikhoan: Taikhoan?) {
        this.id = id
        this.idchapter = idchapter
        this.idtaikhoan = idtaikhoan
    }
}