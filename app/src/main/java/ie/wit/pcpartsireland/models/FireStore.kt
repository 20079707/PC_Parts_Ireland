package ie.wit.pcpartsireland.models

import android.content.Context
import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ie.wit.pcpartsireland.helpers.readImageFromPath
import ie.wit.pcpartsireland.main.MainApp
import java.io.ByteArrayOutputStream
import java.io.File

class FireStore(val context: Context) : Store {

    val parts = ArrayList<Model>()
    private lateinit var userId: String
    private lateinit var db: DatabaseReference
    private lateinit var st: StorageReference
    lateinit var app: MainApp



    override fun findAll(): List<Model> {
        return parts
    }

    override fun findById(id: Long): Model? {
        return parts.find { p -> p.id == id }
    }

    override fun create(part: Model) {
        val key = db.child("users").child(userId).child("parts").push().key
        key?.let {
            part.uid = key
            parts.add(part)
            db.child("users").child(userId).child("parts").child(key).setValue(part)
            updateImage(part)
        }
    }

    override fun update(part: Model) {
        val foundPart: Model? = parts.find { p -> p.uid == part.uid }
        if (foundPart != null) {
            foundPart.title = part.title
            foundPart.price = part.price
            foundPart.quantity = part.quantity
            foundPart.image = part.image
            foundPart.description = part.description
            foundPart.category = part.category
            foundPart.adtype = part.adtype

        }

        db.child("users").child(userId).child("parts").child(part.uid).setValue(part)
        if ((part.image.length) > 0 && (part.image[0] != 'h')) {
            updateImage(part)
        }
    }

    override fun delete(part: Model) {
        db.child("users").child(userId).child("parts").child(part.uid).removeValue()
        parts.remove(part)
        updateImage(part)

    }

    override fun clear() {
        parts.clear()
    }

    private fun updateImage(part: Model) {
        if (part.image != "") {
            val fileName = File(part.image)
            val imageName = fileName.name

            val imageRef = st.child("$userId/$imageName")
            val baos = ByteArrayOutputStream()
            val bitmap = readImageFromPath(context, part.image)

            bitmap?.let {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                val data = baos.toByteArray()
                val uploadTask = imageRef.putBytes(data)
                uploadTask.addOnFailureListener {
                    println(it.message)
                }.addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener {
                        part.image = it.toString()
                        db.child("user-parts").child(userId).child("parts").child(part.uid)
                            .setValue(part)
                    }
                }
            }

        }
    }


    fun fetchParts(partsReady: () -> Unit) {
        val valueEventListener = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(parts) { it.getValue(Model::class.java) }
                partsReady()
            }
        }
        userId = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseDatabase.getInstance().reference
        st = FirebaseStorage.getInstance().reference
        parts.clear()
        db.child("user-parts").child(userId).child("parts")
            .addListenerForSingleValueEvent(valueEventListener)
    }
}