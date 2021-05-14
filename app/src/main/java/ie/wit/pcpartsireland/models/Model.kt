package ie.wit.pcpartsireland.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class Model(
    var id: Long = 0,
    var uid: String = "",
    var title: String = "N/A",
    var category: String = "",
    var price: Int = 0,
    var quantity: Int = 1,
    var image: String = "",
    var description: String = "",
    var adtype: String = "",
    var email: String? = "joe@bloggs.com"
                 ) : Parcelable {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "category" to category,
            "price" to price,
            "quantity" to quantity,
            "image" to image,
            "description" to description,
            "adtype" to adtype,
            "email" to email
        )
    }
                 }

