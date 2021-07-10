package com.example.lmorda.utils

import android.app.Activity
import android.view.View
import android.widget.Adapter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lmorda.R
import com.example.lmorda.model.Repo
import com.example.lmorda.repos.ReposAdapter
import com.google.android.material.snackbar.Snackbar

//TODO: Add unit tests
fun thousandsToKs(number: Int): String {
    return if (number > 0) (number / 1000).toString() + "." + ((number % 1000) / 100).toString() + "k"
    else number.toString()
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