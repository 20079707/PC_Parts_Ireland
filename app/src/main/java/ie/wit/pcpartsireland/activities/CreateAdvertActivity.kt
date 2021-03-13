package ie.wit.pcpartsireland.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.databinding.ActivityCreateAdvertBinding
import ie.wit.pcpartsireland.helpers.readImage
import ie.wit.pcpartsireland.helpers.readImageFromPath
import ie.wit.pcpartsireland.helpers.showImagePicker
import ie.wit.pcpartsireland.main.MainApp
import ie.wit.pcpartsireland.models.Model
import kotlinx.android.synthetic.main.activity_create_advert.*
import kotlinx.android.synthetic.main.activity_create_advert.advertDescription
import kotlinx.android.synthetic.main.activity_create_advert.advertPrice
import kotlinx.android.synthetic.main.activity_create_advert.advertTitle
import kotlinx.android.synthetic.main.activity_create_advert.createAdvertBtn
import kotlinx.android.synthetic.main.activity_create_advert.imageBtn
import kotlinx.android.synthetic.main.activity_create_advert.mySpinner
import kotlinx.android.synthetic.main.activity_create_advert.partImage
import kotlinx.android.synthetic.main.activity_create_advert.radioButtons
import kotlinx.android.synthetic.main.activity_home.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import kotlinx.android.synthetic.main.activity_create_advert.advertQuantity as advertQuantity1

class CreateAdvertActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var binding: ActivityCreateAdvertBinding
    private lateinit var app: MainApp
    private var part = Model()
    private val imageRequest = 1
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

        if (intent.hasExtra("part_edit")) {
            edit = true
            part = intent.extras?.getParcelable("part_edit")!!
            advertTitle.setText(part.title)
            advertDescription.setText(part.description)
            createAdvertBtn.setText(R.string.saveBtn)
            imageBtn.setText(R.string.changeImageBtn)
            partImage.setImageBitmap(readImageFromPath(this, part.image))
        }

        createAdvertBtn.setOnClickListener {
            part.category = part.category
            part.image = part.image
            part.title = advertTitle.text.toString()
            part.price = advertPrice.text.toString().toInt()
            part.adtype = if(radioButtons.checkedRadioButtonId == R.id.radio_for_sale) "For Sale" else "Wanted"
            part.description = advertDescription.text.toString()
            part.quantity = advertQuantity1.text.toString().toInt()
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

        imageBtn.setOnClickListener {
            showImagePicker(this, imageRequest)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, requestCode, data)
        when(requestCode) {
            imageRequest -> {
                if (data != null) {
                    part.image = data.data.toString()
                    partImage.setImageBitmap(readImage(this, resultCode, data))
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_delete_part, menu)

        // sets delete button for edit view
        if (edit && menu != null) menu.getItem(0).isVisible = true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                app.Store.delete(part)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}