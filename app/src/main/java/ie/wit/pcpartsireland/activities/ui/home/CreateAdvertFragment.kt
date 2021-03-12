package ie.wit.pcpartsireland.activities.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.helpers.showImagePicker
import ie.wit.pcpartsireland.main.MainApp
import ie.wit.pcpartsireland.models.Model
import kotlinx.android.synthetic.main.fragment_create_advert.view.*
import org.jetbrains.anko.AnkoLogger

class CreateAdvertFragment : Fragment(), AnkoLogger {

    lateinit var app: MainApp
    private var part = Model()
    private val imageRequest = 1
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as MainApp
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
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
            setButtonListener(root)
            return root
        }
    }



    private fun setButtonListener(layout: View) {
        layout.createAdvertBtn.setOnClickListener {
            part.title = layout.advertTitle.text.toString()
            part.price = layout.advertPrice.text.toString().toInt()

            if (part.title.isEmpty()) {
                Toast.makeText(context, "Please Enter the Advert Title", Toast.LENGTH_SHORT).show()
            } else {
                if (edit) {
                    app.Store.update(part.copy())
                } else {
                    app.Store.create(part.copy())
                }

            }
        }
    }
}

