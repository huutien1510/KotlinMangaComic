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
    var sosao: Double? = null
    var ngaydanhgia: String? = null
}
