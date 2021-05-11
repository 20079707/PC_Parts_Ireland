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
import ie.wit.pcpartsireland.adapters.EditAdAdapter
import ie.wit.pcpartsireland.adapters.CardViewPartListener
import ie.wit.pcpartsireland.main.MainApp
import ie.wit.pcpartsireland.models.Model
import kotlinx.android.synthetic.main.fragment_my_adverts.view.*


class MyAdvertsFragment : Fragment(), CardViewPartListener {

    lateinit var app: MainApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_my_adverts, container, false)

        root.recyclerViewEdit.layoutManager = LinearLayoutManager(activity)
        root.recyclerViewEdit.adapter = EditAdAdapter(app.Store.findAll(), this)

        return root
    }

    override fun onPartClick(part: Model) {

        val intent = Intent(activity, CreateAdvertActivity::class.java).putExtra("part_edit", part)
        startActivityForResult(intent, 0)
    }

}

