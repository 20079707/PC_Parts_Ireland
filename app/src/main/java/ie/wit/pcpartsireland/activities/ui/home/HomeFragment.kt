package ie.wit.pcpartsireland.activities.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.activities.CreateAdvertActivity
import ie.wit.pcpartsireland.adapters.Adapter
import ie.wit.pcpartsireland.adapters.PartListener
import ie.wit.pcpartsireland.main.MainApp
import ie.wit.pcpartsireland.models.Model
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment(), PartListener {

    lateinit var app: MainApp
    var part = Model()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        root.recyclerView.layoutManager = LinearLayoutManager(activity)
        root.recyclerView.adapter = Adapter(app.Store.findAll(), this)

        return root
    }

    override fun onPartClick(part: Model) {

        val intent = Intent(activity, CreateAdvertActivity::class.java).putExtra("part_edit", part)
        startActivityForResult(intent, 0)

    }
}
