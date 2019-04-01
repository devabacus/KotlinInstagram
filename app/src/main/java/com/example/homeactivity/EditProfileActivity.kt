package com.example.homeactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.homeactivity.Models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    private val TAG = "EditProfileActivity"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        Log.d(TAG, "onCreate: ")
        close_image.setOnClickListener {
            finish()
        }

        val auth = FirebaseAuth.getInstance()
        val curUser = auth.currentUser
        val database = FirebaseDatabase.getInstance().reference
        database.child("users").child(curUser!!.uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "onCancelled: ",error.toException())
                }

            override fun onDataChange(data: DataSnapshot) {
                val user = data.getValue(User::class.java)
                if (user != null) {
                    et_name_input.setText(user.name, TextView.BufferType.EDITABLE)
                    et_username_input.setText(user.username, TextView.BufferType.EDITABLE)
                    et_website_Input.setText(user.website, TextView.BufferType.EDITABLE)
                    et_bio_Input.setText(user.bio, TextView.BufferType.EDITABLE)
                    et_email_input.setText(user.email, TextView.BufferType.EDITABLE)
                    et_phone_input.setText(user.phone.toString(), TextView.BufferType.EDITABLE)

                } else {
                    Log.d(TAG, "onDataChange: user is null")
                }
            }


        })


    }



}
