package com.example.lmorda.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.lmorda.DETAILS_ID_BUNDLE_KEY
import com.example.lmorda.R
import com.example.lmorda.model.Repo
import com.example.lmorda.utils.Utils
import com.example.lmorda.utils.getViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_repo_details.*
import kotlinx.android.synthetic.main.fragment_repos.*

class RepoDetailsFragment : Fragment() {

    private val viewModel by viewModels<RepoDetailsViewModel> { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo_details, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.getLong(DETAILS_ID_BUNDLE_KEY)?.let { id ->
            viewModel.fetchRepo(id)
        }
        viewModel.repo.observe(viewLifecycleOwner, { repo ->
            displayRepo(repo)
        })
        viewModel.error.observe(viewLifecycleOwner, {
            Snackbar.make(refresh_layout, it, Snackbar.LENGTH_LONG).show()
        })
    }

    private fun displayRepo(repo: Repo?) {
        if (repo == null) return
        details_content.visibility = View.VISIBLE
        description.text = repo.description
        stars.text = getString(R.string.stargazer_label, Utils.thousandsToKs(repo.stargazers_count))
        forks.text = getString(R.string.fork_label, Utils.thousandsToKs(repo.forks))
        owner.text = repo.owner.login
        when {
            !repo.html_url.isNullOrEmpty() -> {
                url.text = repo.html_url
                url.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.html_url))
                    it.context.startActivity(intent)
                }
            }
            else -> url.visibility = View.GONE

        }
        when {
            !repo.owner.avatar_url.isNullOrEmpty() ->
                Glide.with(requireActivity())
                    .load(repo.owner.avatar_url)
                    .centerCrop()
                    .into(owner_image)
            else -> owner_image.visibility = View.GONE
        }
    }
}