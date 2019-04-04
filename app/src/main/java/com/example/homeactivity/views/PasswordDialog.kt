package com.example.homeactivity.views

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.example.homeactivity.R
import kotlinx.android.synthetic.main.dialog_password.view.*

class PasswordDialog : DialogFragment() {

    interface Listener{
        fun onPasswordConfirm(password:String)
    }

    private lateinit var mListener: Listener

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mListener = context as Listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = activity!!.layoutInflater.inflate(R.layout.dialog_password, null)
        return AlertDialog.Builder(context!!)
            .setPositiveButton(android.R.string.ok, { _, _ ->
                //send password to activity
                mListener.onPasswordConfirm(view.password_input.text.toString())
            })
            .setNegativeButton(android.R.string.cancel, { _, _ ->
                //send cancel
            })
            .setTitle(R.string.please_enter_password)
            .setView(view).create()
    }
}