package ie.wit.pcpartsireland.activities.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.activities.ui.home.SearchFragment.Companion.newInstance
import ie.wit.pcpartsireland.adapters.Adapter
import ie.wit.pcpartsireland.main.MainApp
import ie.wit.pcpartsireland.models.Model
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment(){

    lateinit var app: MainApp
    var part = Model()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var root = inflater.inflate(R.layout.fragment_home, container, false)

        root.recyclerView.layoutManager = LinearLayoutManager(activity)
        root.recyclerView.adapter = Adapter(app.Store.findAll(), this)

        return root
    }


}