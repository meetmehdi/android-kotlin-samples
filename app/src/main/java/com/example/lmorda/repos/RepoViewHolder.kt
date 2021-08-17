package com.example.lmorda.repos

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.lmorda.databinding.RepoItemBinding
import com.example.lmorda.model.Repo
import com.example.lmorda.utils.thousandsToKs

class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindData(repo: Repo, clickListener: (Repo) -> Unit) {
        val binding = RepoItemBinding.bind(itemView)
        with(binding) {
            itemView.setOnClickListener { clickListener(repo) }
            title.text = repo.name
            repo.description?.let {
                description.text = repo.description
            } ?: run { description.visibility = View.GONE }
            stars.text = repo.stargazers_count.thousandsToKs()
            forks.text = repo.forks.thousandsToKs()
        }
    }
}