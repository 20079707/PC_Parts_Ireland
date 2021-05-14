package ie.wit.pcpartsireland.main

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import ie.wit.pcpartsireland.models.FireStore
import ie.wit.pcpartsireland.models.JSONStore
import ie.wit.pcpartsireland.models.MemStore
import ie.wit.pcpartsireland.models.Store


class MainApp : Application() {

    lateinit var Store: Store
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference

    override fun onCreate() {
        super.onCreate()
        //Store = JSONStore(applicationContext)
        Store = FireStore(applicationContext) //store tractors in firebase db
        //Store = MemStore()

    }
}