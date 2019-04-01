package com.example.homeactivity.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.homeactivity.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(0) {

    private val TAG = "HomeActivity"
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()
        Log.d(TAG, "onCreate: ")

        mAuth = FirebaseAuth.getInstance()

        sign_out.setOnClickListener{
            mAuth.signOut()
        }

        mAuth.addAuthStateListener {signIn()}

//        mAuth.signInWithEmailAndPassword("scale-driver@yandex.ru", "123qwe")
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    Log.d(TAG, "signIn: success")
//                } else {
//                    Log.e(TAG, "signIn: failure", it.exception)
//                }
//
//            }
    }

    private fun signIn() {
        if (mAuth.currentUser == null) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        signIn()
    }


}
