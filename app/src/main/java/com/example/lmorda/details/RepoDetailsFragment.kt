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
import com.example.lmorda.utils.getViewModelFactory
import com.example.lmorda.utils.showSnack
import com.example.lmorda.utils.thousandsToKs
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
        //TODO: Add navigation
        MainActivity.FRAGMENT_TAG = TAG_REPO_DETAILS_FRAGMENT
        arguments?.getLong(DETAILS_ID_BUNDLE_KEY)?.let { id ->
            viewModel.fetchRepo(id)
        }
        with (binding) {
            viewModel.error.observe(viewLifecycleOwner, { showSnack(description, it) })
            viewModel.repo.observe(viewLifecycleOwner, { repo ->
                detailsContent.visibility = View.VISIBLE
                description.text = repo.description
                stars.text = getString(R.string.stargazer_label, thousandsToKs(repo.stargazers_count))
                forks.text = getString(R.string.fork_label, thousandsToKs(repo.forks))
                owner.text = repo.owner.login
                //TODO: Replace with placeholder
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
                //TODO: Replace with placeholder
                when {
                    !repo.owner.avatar_url.isNullOrEmpty() ->
                        Glide.with(requireActivity())
                            .load(repo.owner.avatar_url)
                            .centerCrop()
                            .into(ownerImage)
                    else -> ownerImage.visibility = View.GONE
                }
            })
        }
        return view
    }

    companion object {
        fun newInstance(id: Long): RepoDetailsFragment {
            val frag = RepoDetailsFragment()
            frag.arguments = Bundle().apply { putLong(DETAILS_ID_BUNDLE_KEY, id) }
            return frag
        }
    }
}