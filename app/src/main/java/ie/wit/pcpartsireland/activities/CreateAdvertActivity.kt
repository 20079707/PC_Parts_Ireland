package ie.wit.pcpartsireland.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.databinding.ActivityCreateAdvertBinding
import ie.wit.pcpartsireland.main.MainApp
import ie.wit.pcpartsireland.models.Model
import kotlinx.android.synthetic.main.activity_create_advert.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_create_advert.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class CreateAdvertActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var binding: ActivityCreateAdvertBinding
    private lateinit var app: MainApp
    private var part = Model()
    var edit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp


        binding = ActivityCreateAdvertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        //spinner Function
        val data = resources.getStringArray(R.array.categories)
        val adapter = ArrayAdapter(this, R.layout.spinner_item_selected, data)
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item)

        val spinner = findViewById<Spinner>(R.id.mySpinner)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long, ) {

                Toast.makeText(this@CreateAdvertActivity,
                    parent.getItemAtPosition(position).toString(),
                    Toast.LENGTH_LONG).show()
                part.category = mySpinner.getItemAtPosition(position) as String

            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        if (intent.hasExtra("tractor_edit")) {
            edit = true
            part = intent.extras?.getParcelable("tractor_edit")!!
            advertTitle.setText(part.title)
            advertPrice.setText(part.price)
        }


        createAdvertBtn.setOnClickListener {
            part.title = advertTitle.text.toString()
            part.price = advertPrice.text.toString().toInt()
            if (part.title.isEmpty()) {
                toast(R.string.prompt_enterTitle)
            } else {
                if (edit) {
                    app.Store.update(part.copy())
                } else {
                    app.Store.create(part.copy())
                }
            }
            info("add Button Pressed: $part")
            setResult(RESULT_OK)
            finish()
        }
    }


}