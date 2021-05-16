package ie.wit.pcpartsireland.main

import android.app.Application
import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import ie.wit.pcpartsireland.models.FireStore
import ie.wit.pcpartsireland.models.Store


class MainApp : Application() {

    lateinit var Store: Store
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var storage: StorageReference
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var userImage: Uri

    override fun onCreate() {
        super.onCreate()

        Store = FireStore(applicationContext) //store tractors in firebase db

    }
}