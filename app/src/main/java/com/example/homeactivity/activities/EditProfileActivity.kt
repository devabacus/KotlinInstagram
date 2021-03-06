package com.example.homeactivity.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.widget.TextView
import com.example.homeactivity.R
import com.example.homeactivity.models.User
import com.example.homeactivity.views.PasswordDialog
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit_profile.*

class EditProfileActivity : AppCompatActivity(), PasswordDialog.Listener {
    private val TAG = "EditProfileActivity"
    private lateinit var mPendingUser: User
    private lateinit var mUser: User
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mNewVar: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        Log.d(TAG, "onCreate: ")
        close_image.setOnClickListener { finish() }

        save_image.setOnClickListener { updateProfile() }


        mAuth = FirebaseAuth.getInstance()
        mDatabase = FirebaseDatabase.getInstance().reference
        mDatabase.child("users").child(mAuth.currentUser!!.uid)
            .addListenerForSingleValueEvent(ValueEventListenerAdapter {
                mUser = it.getValue(User::class.java)!!
                et_name_input.setText(mUser.name, TextView.BufferType.EDITABLE)
                et_username_input.setText(mUser.username, TextView.BufferType.EDITABLE)
                et_website_input.setText(mUser.website, TextView.BufferType.EDITABLE)
                et_bio_input.setText(mUser.bio, TextView.BufferType.EDITABLE)
                et_email_input.setText(mUser.email, TextView.BufferType.EDITABLE)
                et_phone_input.setText(mUser.phone.toString(), TextView.BufferType.EDITABLE)
            })
    }

    private fun updateProfile() {
        mPendingUser = readInputs()
        val error = validate(mPendingUser)
        if (error == null) {
            if (mPendingUser.email == mUser.email) {
                updateUser(mPendingUser)
            } else {
                //show dialog
                PasswordDialog().show(supportFragmentManager, "password_dialog")

            }
        } else {
            showToast(error)
        }
    }

    private fun readInputs(): User {
        val phoneStr = et_phone_input.text.toString()
        return User(
            name = et_name_input.text.toString(),
            username = et_username_input.text.toString(),
            website = et_website_input.text.toString(),
            bio = et_bio_input.text.toString(),
            email = et_email_input.text.toString(),
            phone = if(phoneStr.isEmpty()) 123 else phoneStr.toLong()
        )
    }

    override fun onPasswordConfirm(password: String) {
        if (password.isNotEmpty()) {
            val credential = EmailAuthProvider.getCredential(mUser.email, password)
            mAuth.currentUser!!.reauthenticate(credential) {
                    mAuth.currentUser!!.updateEmail(mPendingUser.email){
                            updateUser(mPendingUser)
                    }
                }

        } else {
            showToast("You should type password")
        }
    }


    private fun updateUser(user: User) {
        val updatesMap = mutableMapOf<String, Any>()
        if (user.name != mUser.name) updatesMap["name"] = user.name
        if (user.username != mUser.username) updatesMap["username"] = user.username
        if (user.website != mUser.website) updatesMap["website"] = user.website
        if (user.bio != mUser.bio) updatesMap["bio"] = user.bio
        if (user.email != mUser.email) updatesMap["email"] = user.email
        if (user.phone != mUser.phone) updatesMap["phone"] = user.phone

        mDatabase.updateUser(mAuth.currentUser!!.uid, updatesMap) {
                showToast("profile saved")
                finish()
        }
    }
    private fun validate(user: User): String? =
        when {
            user.name.isEmpty() -> "Please enter name"
            user.username.isEmpty() -> "Please enter username"
            user.email.isEmpty() -> "Please enter username"
            else -> null
        }

    private fun DatabaseReference.updateUser(uid: String, updates: Map<String, Any>, onSuccess: () -> Unit) {
        child("users").child(mAuth.currentUser!!.uid).updateChildren(updates).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess()
            } else {
                showToast(it.exception!!.message!!)
            }
        }
    }

    private fun FirebaseUser.updateEmail(email: String, onSuccess:()->Unit) {
        updateEmail(email).addOnCompleteListener {
            if (it.isSuccessful) onSuccess()
            else showToast(it.exception!!.message!!)
        }
    }
    private fun FirebaseUser.reauthenticate(credential: AuthCredential, onSuccess:()->Unit) {
        reauthenticate(credential).addOnCompleteListener {
            if (it.isSuccessful) onSuccess()
            else showToast(it.exception!!.message!!)
        }
    }

}
