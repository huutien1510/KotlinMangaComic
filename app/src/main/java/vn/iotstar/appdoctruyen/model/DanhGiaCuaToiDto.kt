package vn.iotstar.appdoctruyen.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
class DanhGiaCuaToiDto {
    var id: Int? = null
    var idchapter: Int? = null
    var idtaikhoan: Int? = null
    var tenTruyen: String? = null // Thêm trường này
    var tenChapter: String? = null // Thêm trường này
    var sosao: Double? = null
    var ngaydanhgia: String? = null
    var linkAnh: String? = null // Thêm trường này
}

