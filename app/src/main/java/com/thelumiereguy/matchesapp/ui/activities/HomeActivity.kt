package com.thelumiereguy.matchesapp.ui.activities

import android.os.Bundle
import com.thelumiereguy.matchesapp.R
import com.thelumiereguy.matchesapp.ui.base.BaseActivity

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
