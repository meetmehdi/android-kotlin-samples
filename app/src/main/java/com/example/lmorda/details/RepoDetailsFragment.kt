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
import com.example.lmorda.MainActivity
import com.example.lmorda.R
import com.example.lmorda.TAG_REPO_DETAILS_FRAGMENT
import com.example.lmorda.databinding.FragmentRepoDetailsBinding
import com.example.lmorda.utils.*
import com.google.android.material.snackbar.Snackbar

class RepoDetailsFragment : Fragment() {

    private val viewModel by viewModels<RepoDetailsViewModel> { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_repo_details, container, false)
        val binding = FragmentRepoDetailsBinding.bind(view)
        arguments?.getLong(DETAILS_ID_BUNDLE_KEY)?.let { id ->
            viewModel.fetchRepo(id)
        }
        with (binding) {
            viewModel.error.observe(viewLifecycleOwner, { showSnack(description, it) })
            viewModel.repo.observe(viewLifecycleOwner, { repo ->
                description.text = repo.description
                stars.text = getString(R.string.stargazer_label, repo.stargazers_count.thousandsToKs())
                forks.text = getString(R.string.fork_label, repo.forks.thousandsToKs())
                owner.text = repo.owner.login
                when {
                    !repo.html_url.isNullOrEmpty() ->
                        url.setUrlClickListener(repo.html_url)
                    else -> {
                        url.visibility = View.GONE
                        urlImage.visibility = View.GONE
                    }
                }
                when {
                    !repo.owner.avatar_url.isNullOrEmpty() ->
                        ownerImage.glideMe(requireActivity(), repo.owner.avatar_url)
                    else ->
                        ownerImage.visibility = View.GONE
                }
            })
        }
        return view
    }
}