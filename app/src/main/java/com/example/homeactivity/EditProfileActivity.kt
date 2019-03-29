package com.example.homeactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class EditProfileActivity : AppCompatActivity() {

    private val TAG = "EditProfileActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        Log.d(TAG, "onCreate: ")
    }
}
