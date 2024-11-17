package vn.iotstar.appdoctruyen.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
class BinhLuanCuaToiDto {
    var id: Int? = null
    var idchapter: Int? = null
    var idtaikhoan: Int? = null
    var noidung: String? = null
    var ngaydang: String? = null
    var trangthai: Int? = null
}