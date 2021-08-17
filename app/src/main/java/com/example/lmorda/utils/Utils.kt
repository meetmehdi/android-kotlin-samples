package com.example.lmorda.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.lmorda.R
import com.example.lmorda.model.Repo
import com.example.lmorda.repos.ReposAdapter
import com.google.android.material.snackbar.Snackbar

//TODO: Add unit tests
fun Int?.thousandsToKs(): String {
    return if (this == null) "0k"
    else if (this > 0) (this / 1000).toString() + "." + ((this % 1000) / 100).toString() + "k"
    else this.toString()
}

fun SwipeRefreshLayout.setSpinnerColors(activity: Activity) {
    this.apply {
        ContextCompat.getColor(activity, R.color.colorPrimary)
        ContextCompat.getColor(activity, R.color.colorAccent)
        ContextCompat.getColor(activity, R.color.colorPrimaryDark)
    }
}

fun showSnack(view: View, resId: Int) =
    Snackbar.make(view, view.resources.getString(resId), Snackbar.LENGTH_LONG).show()

fun ReposAdapter.display(newRepos: List<Repo>) {
    this.apply {
        repos = newRepos
        notifyDataSetChanged()
    }
}

fun TextView.setUrlClickListener(url: String) {
    this.text = url
    this.setOnClickListener {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        it.context.startActivity(intent)
    }
}

//TODO: Add placeholder
fun ImageView.glideMe(activity: Activity, url: String) {
 Glide.with(activity)
     .load(url)
     .centerCrop()
     .into(this)
}