package ie.wit.pcpartsireland.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.databinding.ActivityCreateAdvertBinding

import ie.wit.pcpartsireland.helpers.readImageFromPath
import ie.wit.pcpartsireland.helpers.showImagePicker
import ie.wit.pcpartsireland.main.MainApp
import ie.wit.pcpartsireland.models.Model
import ie.wit.pcpartsireland.utils.hideLoader
import ie.wit.pcpartsireland.utils.showLoader
import kotlinx.android.synthetic.main.activity_create_advert.*
import kotlinx.android.synthetic.main.activity_home.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlinx.android.synthetic.main.activity_create_advert.advertQuantity as advertQuantity1

class CreateAdvertActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAdvertBinding
    private lateinit var app: MainApp
    private var part = Model()
    private val imageRequest = 1
    var edit = false
    lateinit var eventListener : ValueEventListener
    lateinit var loader : AlertDialog

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
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

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
            advertPrice.setText(part.price.toString())
            advertQuantity1.setText(part.price.toString())

            createAdvertBtn.setText(R.string.saveBtn)
            imageBtn.setText(R.string.changeImageBtn)
            Glide.with(this).load(part.image).into(partImage);
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
                val text = (R.string.prompt_enterTitle)
                val duration = Toast.LENGTH_SHORT

                val toast = Toast.makeText(applicationContext, text, duration)
                toast.show()
            } else {
                if (edit) {
                    app.Store.updatePart(part.uid, part)
                    app.Store.updateUserPart(app.auth.currentUser!!.uid,
                        part.uid, part)
                } else {
                    app.Store.create(part)
                }
            }
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
                    Glide.with(this).load(part.image).into(partImage)
                }
            }
        }
    }
}