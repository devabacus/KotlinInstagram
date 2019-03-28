package com.example.homeactivity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottom_navigation_view.apply {
            setIconSize(29f,29f)
            setTextVisibility(false)
            enableItemShiftingMode(false)
            enableShiftingMode(false)
            enableAnimation(false)
        }

        for (i in 0 until bottom_navigation_view.menu.size()) {
            bottom_navigation_view.setIconTintList(i, null)
        }
    }
}
