package vn.iotstar.appdoctruyen.model

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class Noidungchapter {
    var id: Int? = null
    var idchapter: Int? = null
    var linkanh: String? = null

    constructor(linkanh: String?) {
        this.linkanh = linkanh
    }

    constructor(idchapter: Int?, linkanh: String?) {
        this.idchapter = idchapter
        this.linkanh = linkanh
    }

    constructor(id: Int?, idchapter: Int?, linkanh: String?) {
        this.id = id
        this.idchapter = idchapter
        this.linkanh = linkanh
    }

    constructor()
}