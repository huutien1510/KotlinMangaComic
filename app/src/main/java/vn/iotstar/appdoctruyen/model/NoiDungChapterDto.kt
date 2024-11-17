package vn.iotstar.appdoctruyen.model

class NoiDungChapterDto {
    var id: Int? = null
    var idchapter: Int? = null
    var linkanh: String? = null

    constructor()
    constructor(id: Int?, idchapter: Int?, linkanh: String?) {
        this.id = id
        this.idchapter = idchapter
        this.linkanh = linkanh
    }
}
