package ie.wit.pcpartsireland.activities.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ie.wit.pcpartsireland.R
import it.sephiroth.android.library.numberpicker.doOnProgressChanged
import it.sephiroth.android.library.numberpicker.doOnStartTrackingTouch
import it.sephiroth.android.library.numberpicker.doOnStopTrackingTouch
import kotlinx.android.synthetic.main.fragment_create_advert.*
import kotlinx.android.synthetic.main.fragment_create_advert.view.*
import kotlinx.android.synthetic.main.list_cards.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


class CreateAdvertFragment : Fragment() {
    val types = arrayOf("Java", "Python", "C++", "C#", "Angular", "Go")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment



        val root = inflater.inflate(R.layout.fragment_create_advert, container, false)



        val spinner = root.findViewById<Spinner>(R.id.mySpinner)

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(requireContext(), R.array.categories, R.layout.spinner_item_selected).also { adapter ->

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

            // Apply the adapter to the spinner
            spinner.adapter = adapter
            spinner.onItemSelectedListener = object :

                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
            return root
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CreateAdvertFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}