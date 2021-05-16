package org.wit.hogan_farm_machinery.activities.authentication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ie.wit.pcpartsireland.activities.Login
import ie.wit.pcpartsireland.databinding.ActivityWelcomeBinding
import ie.wit.pcpartsireland.main.MainApp
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    private var firebaseUser: FirebaseUser? = null
    lateinit var app: MainApp
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        app = application as MainApp



        //login button
        btnWelcomeLogin.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, Login::class.java)
            startActivity(intent)
            finish()
        }

    }
}