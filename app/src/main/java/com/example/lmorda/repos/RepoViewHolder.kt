package com.example.lmorda.repos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lmorda.R
import com.example.lmorda.model.Repo
import com.example.lmorda.utils.Utils
import kotlinx.android.synthetic.main.repo_item.view.*

class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var repo: Repo

    fun bindData(repo: Repo, clickListener: (Repo) -> Unit) {
        this.repo = repo
        itemView.setOnClickListener { clickListener(repo) }
        itemView.title.text = repo.name
        repo.description?.let {
            itemView.description.text = repo.description
        } ?: run { itemView.description.visibility = View.GONE }
        itemView.stars.text = Utils.thousandsToKs(repo.stargazers_count)
        itemView.forks.text = Utils.thousandsToKs(repo.forks)
    }

    companion object {
        fun create(parent: ViewGroup): RepoViewHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false)
            return RepoViewHolder(view)
        }
    }
}