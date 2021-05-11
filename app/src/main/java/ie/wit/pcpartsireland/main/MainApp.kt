package ie.wit.pcpartsireland.main

import android.app.Application
import ie.wit.pcpartsireland.models.JSONStore
import ie.wit.pcpartsireland.models.MemStore
import ie.wit.pcpartsireland.models.Store


class MainApp : Application() {

    lateinit var Store: Store

    override fun onCreate() {
        super.onCreate()
        Store = JSONStore(applicationContext)
        //Store = MemStore()

    }
}