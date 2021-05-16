package ie.wit.pcpartsireland.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import ie.wit.pcpartsireland.helpers.readImageFromPath
import ie.wit.pcpartsireland.main.MainApp
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.HashMap

class FireStore(val context: Context) : Store {

    val parts = ArrayList<Model>()
    private lateinit var userId: String
    private lateinit var app: MainApp
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var db: DatabaseReference = FirebaseDatabase.getInstance().reference
    lateinit var storage: StorageReference

    override fun create(part: Model) {
        val uid = auth.currentUser!!.uid
        val key = db.child("parts").push().key

        if (key != null) {
            part.uid = key
        }
        val partValues = part.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/parts/$key"] = partValues
        childUpdates["/user-parts/$uid/$key"] = partValues

        db.updateChildren(childUpdates)
    }


    override fun updateUserPart(userId: String, uid: String?, part: Model) {

        db.child("user-parts").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(part)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        ("Firebase Part error : ${error.message}")
                    }
                })
    }


    override fun updatePart(uid: String?, part: Model) {

        db.child("parts").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(part)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        ("Firebase Part error : ${error.message}")
                    }
                })
    }

    override fun deleteUserPart(userId: String, uid: String?) {
        db.child("user-parts").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        "Firebase Part error : ${error.message}"
                    }
                })
    }



    override fun deletePart(uid: String?) {
        db.child("parts").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.removeValue()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        "Firebase Part error : ${error.message}"
                    }
                })
    }

    private fun updateImage(part: Model) {
        if (part.image != "") {
            val fileName = File(part.image)
            val imageName = fileName.name

            val imageRef = storage.child("$userId/$imageName")
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
                        db.child("users").child(userId).child("tractors").child(part.uid)
                            .setValue(part)
                    }
                }
            }

        }
    }

    override fun clear() {
        parts.clear()
    }

    fun uploadImageView(app: MainApp, imageView: ImageView) {
        // Get the data from an ImageView as bytes
        val uid = app.auth.currentUser!!.uid
        val imageRef = app.storage.child("photos").child("${uid}.jpg")
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        var uploadTask = imageRef.putBytes(data)
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
        storage = FirebaseStorage.getInstance().reference
        parts.clear()
        db.child("user-parts").child(userId).child("parts")
            .addListenerForSingleValueEvent(valueEventListener)
    }
}