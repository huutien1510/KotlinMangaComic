package vn.iotstar.appdoctruyen.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize

@Parcelize
data class Truyen1(
    var id: Int? = null,
    var tentruyen: String? = null,
    var tacgia: String? = null,
    var mota: String? = null,
    var theloai: String? = null,
    var linkanh: String? = null,
    var trangthai: Int? = null,
    var keySearch: String? = null
) : Parcelable {
    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    companion object : Parceler<Truyen1> {
        override fun Truyen1.write(dest: Parcel, flags: Int) {
            TODO("Not yet implemented")
        }

        override fun create(parcel: Parcel): Truyen1 = TODO()
    }
}
