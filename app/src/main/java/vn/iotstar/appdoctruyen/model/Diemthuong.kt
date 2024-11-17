package vn.iotstar.appdoctruyen.model

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class Diemthuong {
    private var id: Int? = null

    constructor(id: Int?, idtaikhoan: Taikhoan?) {
        this.id = id
        this.idtaikhoan = idtaikhoan
    }

    private var idtaikhoan: Taikhoan? = null

    constructor() //TODO [JPA Buddy] generate columns from DB
}