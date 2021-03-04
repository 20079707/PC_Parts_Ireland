package ie.wit.pcpartsireland.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Model(var id: Long = 0,
                         val price: Int = 0,
                         val name: String = "N/A") : Parcelable