package com.example.lmorda.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lmorda.R
import com.example.lmorda.model.Repo

class ReposAdapter(
    private val clickListener: (Repo) -> Unit
) : RecyclerView.Adapter<RepoViewHolder>() {

    var repos: List<Repo> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.repo_item,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repos.get(position)
        holder.bindData(repo, clickListener)
    }

    override fun getItemCount() = repos.size

}