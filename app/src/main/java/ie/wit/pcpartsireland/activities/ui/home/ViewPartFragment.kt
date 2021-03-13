package ie.wit.pcpartsireland.activities.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.helpers.readImageFromPath
import ie.wit.pcpartsireland.models.Model
import kotlinx.android.synthetic.main.fragment_view_part.*

class ViewPartFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_view_part, container, false)
        val part = Model()

        viewTitle.text = part.title
        viewCategory.text = part.category
        viewAdType.text = part.adtype
        viewImage.setImageBitmap(context?.let { readImageFromPath(it, part.image) })
        viewDescription.text = part.description

        return root
    }

}