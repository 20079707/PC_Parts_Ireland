package ie.wit.pcpartsireland.activities.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.activities.CreateAdvertActivity
import ie.wit.pcpartsireland.adapters.ViewCardAdapter
import ie.wit.pcpartsireland.adapters.CardViewPartListener
import ie.wit.pcpartsireland.adapters.EditAdAdapter
import ie.wit.pcpartsireland.main.MainApp
import ie.wit.pcpartsireland.models.Model
import ie.wit.pcpartsireland.utils.createLoader
import ie.wit.pcpartsireland.utils.hideLoader
import ie.wit.pcpartsireland.utils.showLoader
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_my_adverts.view.*
import kotlinx.android.synthetic.main.list_cards.*


class HomeFragment : Fragment(), CardViewPartListener {

    lateinit var app: MainApp
    var part = Model()
    lateinit var root: View
    lateinit var loader : AlertDialog
    var favourite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        root = inflater.inflate(R.layout.fragment_home, container, false)
        root.recyclerView.layoutManager = LinearLayoutManager(activity)

        setSwipeRefresh()
        return root
    }

    override fun onPartClick(part: Model) {

    }

    fun setSwipeRefresh() {
        root.swiperefreshhome.setOnRefreshListener {
            root.swiperefreshhome.isRefreshing = true
            getAllUsersParts()
        }
    }

    fun checkSwipeRefresh() {
        if (root.swiperefreshhome.isRefreshing) root.swiperefreshhome.isRefreshing = false
    }


    override fun onResume() {
        super.onResume()
        getAllUsersParts()
    }



    fun getAllUsersParts() {
        loader = createLoader(requireActivity())
        showLoader(loader, "Downloading All Users Adverts from Firebase")
        val advertsList = ArrayList<Model>()
        app.database.child("parts")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    ("Firebase Advert error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val part = it.
                        getValue<Model>(Model::class.java)

                        advertsList.add(part!!)
                        root.recyclerView.adapter =
                            ViewCardAdapter(advertsList, this@HomeFragment)
                        root.recyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("parts").removeEventListener(this)
                    }
                }
            })
    }
}
