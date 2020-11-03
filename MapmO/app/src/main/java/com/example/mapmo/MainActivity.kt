package com.example.mapmo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.auth.api.signin.GoogleSignIn.*
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_to_gl.setOnClickListener {
            val socialIntent = Intent(this, SocialLoginActivity::class.java)
            startActivity(socialIntent)
        }
    }
    private lateinit var activity : MainActivity
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_main_setting, container, false)

        // Google Sign-In Methods
        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = getClient(view.context, gso)

        view.xml_frg_setting_logout.setOnClickListener { view ->
            FirebaseAuth.getInstance().signOut()

            //Google Login Initialize
            googleSignInClient.signOut().addOnCompleteListener {
                activity.finish()
            }
        }

        return view
    }
}