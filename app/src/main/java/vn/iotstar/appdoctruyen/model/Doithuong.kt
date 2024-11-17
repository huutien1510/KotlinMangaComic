package vn.iotstar.appdoctruyen.model

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class Doithuong {
    private var id: Int? = null

    constructor()
    constructor(id: Int?, idtaikhoan: Taikhoan?, idvatpham: Vatpham?) {
        this.id = id
        this.idtaikhoan = idtaikhoan
        this.idvatpham = idvatpham
    }

    private var idtaikhoan: Taikhoan? = null
    private var idvatpham: Vatpham? = null //TODO [JPA Buddy] generate columns from DB
}