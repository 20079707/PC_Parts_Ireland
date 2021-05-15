package ie.wit.pcpartsireland.activities.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.activities.CreateAdvertActivity
import ie.wit.pcpartsireland.adapters.EditAdAdapter
import ie.wit.pcpartsireland.adapters.CardViewPartListener
import ie.wit.pcpartsireland.main.MainApp
import ie.wit.pcpartsireland.models.Model
import ie.wit.pcpartsireland.utils.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.fragment_my_adverts.*
import kotlinx.android.synthetic.main.fragment_my_adverts.view.*


class MyAdvertsFragment : Fragment(), CardViewPartListener {

    lateinit var app: MainApp
    lateinit var root: View
    lateinit var loader : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        root = inflater.inflate(R.layout.fragment_my_adverts, container, false)

        root.recyclerViewEdit.layoutManager = LinearLayoutManager(activity)


        setSwipeRefresh()

        val swipeDeleteHandler = object : SwipeToDeleteCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = root.recyclerViewEdit.adapter as EditAdAdapter
                adapter.removeAt(viewHolder.adapterPosition)
                deletePart((viewHolder.itemView.tag as Model).uid)
                deleteUserPart(app.auth.currentUser!!.uid,
                    (viewHolder.itemView.tag as Model).uid)
            }
        }
        val itemTouchDeleteHelper = ItemTouchHelper(swipeDeleteHandler)
        itemTouchDeleteHelper.attachToRecyclerView(root.recyclerViewEdit)

        val swipeEditHandler = object : SwipeToEditCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onPartClick(viewHolder.itemView.tag as Model)
            }
        }

        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(root.recyclerViewEdit)

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyAdvertsFragment().apply {
                arguments = Bundle().apply { }
            }
    }


    override fun onPartClick(part: Model) {

        val intent = Intent(activity, CreateAdvertActivity::class.java).putExtra("part_edit", part)
        startActivityForResult(intent, 0)
    }

    fun deleteUserPart(userId: String, uid: String?) {
        app.database.child("user-parts").child(userId).child(uid!!)
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

    fun deletePart(uid: String?) {
        app.database.child("parts").child(uid!!)
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

    fun setSwipeRefresh() {
        root.swiperefresh.setOnRefreshListener {
            root.swiperefresh.isRefreshing = true
            getAllDonations(app.auth.currentUser!!.uid)
        }
    }

    fun checkSwipeRefresh() {
        if (root.swiperefresh.isRefreshing) root.swiperefresh.isRefreshing = false
    }


    override fun onResume() {
        super.onResume()
        getAllDonations(app.auth.currentUser!!.uid)
    }

    fun getAllDonations(userId: String?) {
        loader = createLoader(requireActivity())
        showLoader(loader, "Downloading Parts from Firebase")
        val partsList = ArrayList<Model>()
        app.database.child("user-parts").child(userId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val part = it.
                        getValue<Model>(Model::class.java)

                        partsList.add(part!!)
                        root.recyclerViewEdit.adapter =
                            EditAdAdapter(partsList, this@MyAdvertsFragment)
                        root.recyclerViewEdit.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("user-parts").child(userId)
                            .removeEventListener(this)
                    }
                }
            })
    }



}

