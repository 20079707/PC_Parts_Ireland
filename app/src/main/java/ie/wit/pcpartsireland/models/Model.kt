package ie.wit.pcpartsireland.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.DecimalFormat

@Parcelize
data class Model(var id: Long = 0,
                 var title: String = "N/A",
                 var category: String = "",
                 var price: Int = 0,
                 var quantity: Int = 1,
                 var image: String = "",
                 var description: String = "",
                 ) : Parcelable