package com.example.lmorda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lmorda.repos.ReposFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, ReposFragment())
            .commit()
    }

    override fun onBackPressed() {
        when (FRAGMENT_TAG) {
            TAG_REPOS_FRAGMENT -> super.onBackPressed()
            TAG_REPO_DETAILS_FRAGMENT ->
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, ReposFragment()).commit()
        }
    }

    companion object {
        var FRAGMENT_TAG = TAG_REPOS_FRAGMENT
    }
}