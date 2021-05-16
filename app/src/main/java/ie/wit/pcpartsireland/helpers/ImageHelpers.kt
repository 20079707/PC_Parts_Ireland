package ie.wit.pcpartsireland.helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.widget.ImageView
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.activities.CreateAdvertActivity
import ie.wit.pcpartsireland.main.MainApp
import java.io.ByteArrayOutputStream
import java.io.IOException

fun showImagePicker(parent: CreateAdvertActivity, id: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_OPEN_DOCUMENT
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    val chooser = Intent.createChooser(intent, R.string.image_picker.toString())
    parent.startActivityForResult(chooser, id)
}

fun readImage(activity: Activity, resultCode: Int, data: Intent?): Bitmap? {
    var bitmap: Bitmap? = null
    if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
        try {
            //bitmap = MediaStore.Images.Media.getBitmap(activity.contentResolver, data.data)
            bitmap = ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(activity.contentResolver, data.data!!
                ))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return bitmap
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

fun readImageFromPath(context: Context, path: String) : Bitmap? {
    var bitmap : Bitmap? = null
    val uri = Uri.parse(path)
    if (uri != null) {
        try {
            val parcelFileDescriptor = context.contentResolver.openFileDescriptor(uri, "r")
            val fileDescriptor = parcelFileDescriptor?.fileDescriptor
            bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
            parcelFileDescriptor?.close()
        } catch (e: Exception) {
        }
    }
    return bitmap
}