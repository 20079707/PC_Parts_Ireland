package ie.wit.pcpartsireland.activities


import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ie.wit.pcpartsireland.R
import ie.wit.pcpartsireland.databinding.ActivityHomeBinding
import ie.wit.pcpartsireland.helpers.checkLocationPermissions
import ie.wit.pcpartsireland.helpers.isPermissionGranted
import ie.wit.pcpartsireland.helpers.setCurrentLocation
import ie.wit.pcpartsireland.main.MainApp
import ie.wit.pcpartsireland.utils.uploadImageView
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var app: MainApp


    private inline fun <reified T: Activity> Activity.myStartActivityForResult(requestCode: Int) {
        val intent = Intent(this, T::class.java)
        startActivityForResult(intent, requestCode)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MainApp

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

         app.currentLocation = Location("Default").apply {
           latitude = 52.245696
           longitude = -7.139102
       }

        app.locationClient = LocationServices.getFusedLocationProviderClient(this)
        if(checkLocationPermissions(this)) {
            // todo get the current location
            setCurrentLocation(app)
        }



        nav_view.getHeaderView(0).nav_header_email.text = app.auth.currentUser?.email
        nav_view.getHeaderView(0).nav_header_name.text = app.auth.currentUser?.displayName
        Picasso.get().load(app.auth.currentUser?.photoUrl)
            .resize(180, 180)
            .into(nav_view.getHeaderView(0).imageView, object : Callback {
                override fun onSuccess() {
                    // Drawable is ready
                    uploadImageView(nav_view.getHeaderView(0).imageView)
                }
                override fun onError(e: Exception) {}
            })



        app.auth = FirebaseAuth.getInstance()
        app.database = FirebaseDatabase.getInstance().reference


        binding.fab.setOnClickListener {
            myStartActivityForResult<CreateAdvertActivity>(0)
        }


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_my_adverts, R.id.nav_favourites
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        bottom_nav.setupWithNavController(navController)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            // todo get the current location
            setCurrentLocation(app)
        } else {
            // permissions denied, so use the default location
            app.currentLocation = Location("Default").apply {
                latitude = 52.245696
                longitude = -7.139102
            }
        }
        Log.v("Donation", "Home LAT: ${app.currentLocation.latitude} LNG: ${app.currentLocation.longitude}")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun signOut()
    {
        app.auth.signOut()
        val intent = Intent(this@HomeActivity, Login::class.java)
        startActivity(intent)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_delete_part, menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_sign_out -> signOut()
        }
        return super.onOptionsItemSelected(item)
    }

}
