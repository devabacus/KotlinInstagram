package com.example.homeactivity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseActivity(val navNumber:Int) : AppCompatActivity() {
    private val TAG = "BaseActivity"
    fun setupBottomNavigation() {
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
        bottom_navigation_view.setOnNavigationItemSelectedListener {
            val nextActivity =
                when (it.itemId) {
                    R.id.nav_item_home -> HomeActivity::class.java
                    R.id.nav_item_likes -> LikesActivity::class.java
                    R.id.nav_item_profile -> ProfileActivity::class.java
                    R.id.nav_item_search -> SearchActivity::class.java
                    R.id.nav_item_share -> ShareActivity::class.java
                    else ->{
                        Log.e(TAG, "unknown item - $it")
                        null
                    }
                }
            if (nextActivity != null) {
                val intent = Intent(this, nextActivity)
                intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                startActivity(intent)
                overridePendingTransition(0,0)
                true
            } else {
                false
            }

        }
        bottom_navigation_view.menu.getItem(navNumber).isChecked = true

    }
}