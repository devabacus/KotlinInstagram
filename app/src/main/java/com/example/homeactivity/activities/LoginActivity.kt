package com.example.homeactivity.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.homeactivity.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_register_namepass.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener

class LoginActivity : AppCompatActivity(), KeyboardVisibilityEventListener, TextWatcher, View.OnClickListener {
    private val TAG = "LoginActivity"
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d(TAG, "onCreate: ")
        btn_login.isEnabled = false
        et_email_login.addTextChangedListener(this)
        et_password_login.addTextChangedListener(this)
        KeyboardVisibilityEvent.setEventListener(this, this)
        btn_login.setOnClickListener(this)
        mAuth = FirebaseAuth.getInstance()

        tv_create_account.setOnClickListener(this)

    }

    override fun onClick(v: View) {

        when (v.id) {
            R.id.btn_login -> {

                val email = et_email_login.text.toString()
                val password = et_password_login.text.toString()
                if (checkCredentials()) {
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        } else {
                            showToast("Неправильные логин или пароль")
                        }
                    }
                } else {
                    showToast("Необходимо заполнить поля")
                }
            }
            R.id.tv_create_account -> startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onVisibilityChanged(isKeyboardOpen: Boolean) {
        if (isKeyboardOpen) {
            scrollview_login.scrollTo(0, scrollview_login.bottom)
            tv_create_account.visibility = View.GONE
        } else {
            scrollview_login.scrollTo(0, scrollview_login.top)
            tv_create_account.visibility = View.VISIBLE
        }
    }

    override fun afterTextChanged(s: Editable?) {
        btn_login.isEnabled = checkCredentials()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    fun checkCredentials() = et_email_login.text.toString().isNotEmpty() &&
            et_password_login.text.toString().isNotEmpty()

}
