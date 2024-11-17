package vn.iotstar.appdoctruyen.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
class BinhLuanTruyenDto {
    var linkAnh: String? = null
    var email: String? = null
    var noidung: String? = null
    var ngaydang: String? = null
    var tenChapter: String? = null
}
