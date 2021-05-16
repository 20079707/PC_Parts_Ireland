package ie.wit.pcpartsireland.activities.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.helpers.getAllParts
import ie.wit.pcpartsireland.helpers.getFavouriteParts
import ie.wit.pcpartsireland.helpers.setMapMarker
import ie.wit.pcpartsireland.helpers.trackLocation
import ie.wit.pcpartsireland.main.MainApp
import kotlinx.android.synthetic.main.fragment_favourites.*

class FavouritesFragment : Fragment() {

    lateinit var app: MainApp
    var viewFavourites = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_favourites, container, false)

        return layout;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.menu_favourites)

        imageMapFavourites.setOnClickListener {
            app.mMap.clear()
            setMapMarker(app)
            if (!viewFavourites) {
                imageMapFavourites.setImageResource(R.drawable.favourite_icon_on)
                viewFavourites = true
                getFavouriteParts(app)
            } else {
                imageMapFavourites.setImageResource(R.drawable.favourite_icon_off)
                viewFavourites = false
                getFavouriteParts(app)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FavouritesFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}