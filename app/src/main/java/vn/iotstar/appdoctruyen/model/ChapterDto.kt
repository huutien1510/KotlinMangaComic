package vn.iotstar.appdoctruyen.model

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@AllArgsConstructor
@NoArgsConstructor
class ChapterDto {
    var id: Int? = null
    var idtruyen: Int? = null
    @JvmField
    var tenchapter: String? = null
    var ngaydang: String? = null
    var soluotxem: Int? = null
    var danhgia: Double? = null
}