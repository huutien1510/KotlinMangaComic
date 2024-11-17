package vn.iotstar.appdoctruyen.model

import lombok.Getter
import lombok.Setter

@Getter
@Setter
class Vatpham {
    private var id: Int? = null
    private var tenvatpham: String? = null
    private var diem: Int? = null
    private var linkanh: String? = null
    private var doithuongs: Set<Doithuong>? = null

    constructor()
    constructor(id: Int?, tenvatpham: String?, diem: Int?, linkanh: String?, doithuongs: Set<Doithuong>?) {
        this.id = id
        this.tenvatpham = tenvatpham
        this.diem = diem
        this.linkanh = linkanh
        this.doithuongs = doithuongs
    }
}