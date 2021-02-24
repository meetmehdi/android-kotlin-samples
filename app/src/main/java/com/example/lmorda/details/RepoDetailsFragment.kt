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
import com.example.lmorda.utils.Utils
import com.example.lmorda.utils.getViewModelFactory
import com.google.android.material.snackbar.Snackbar

class RepoDetailsFragment : Fragment() {

    private val viewModel by viewModels<RepoDetailsViewModel> { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainActivity.FRAGMENT_TAG = TAG_REPO_DETAILS_FRAGMENT
        arguments?.getLong(DETAILS_ID_BUNDLE_KEY)?.let { id ->
            viewModel.fetchRepo(id)
        }
        val binding = FragmentRepoDetailsBinding.bind(view)
        viewModel.repo.observe(viewLifecycleOwner, { repo ->
            with (binding) {
                detailsContent.visibility = View.VISIBLE
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
                            .into(ownerImage)
                    else -> ownerImage.visibility = View.GONE
                }
            }

        })
        viewModel.error.observe(viewLifecycleOwner, {
            with (binding) {
                Snackbar.make(description, it, Snackbar.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        fun newInstance(id: Long): RepoDetailsFragment {
            val frag = RepoDetailsFragment()
            frag.arguments = Bundle().apply { putLong(DETAILS_ID_BUNDLE_KEY, id) }
            return frag
        }
    }
}