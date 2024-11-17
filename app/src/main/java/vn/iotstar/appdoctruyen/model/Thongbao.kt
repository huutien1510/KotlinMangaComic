package vn.iotstar.appdoctruyen.model

import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import java.time.LocalDate

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
class Thongbao {
    private val id: Int? = null
    private val tieude: String? = null
    private val noidung: String? = null
    private val ngaydang: LocalDate? = null
}