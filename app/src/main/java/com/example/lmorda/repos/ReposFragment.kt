package com.example.lmorda.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lmorda.MainActivity
import com.example.lmorda.R
import com.example.lmorda.TAG_REPOS_FRAGMENT
import com.example.lmorda.databinding.FragmentRepoDetailsBinding
import com.example.lmorda.databinding.FragmentReposBinding
import com.example.lmorda.details.RepoDetailsFragment
import com.example.lmorda.utils.getViewModelFactory
import com.google.android.material.snackbar.Snackbar

class ReposFragment : Fragment() {

    private val viewModel by viewModels<ReposViewModel> { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_repos, container, false)
        val binding = FragmentReposBinding.bind(view)
        MainActivity.FRAGMENT_TAG = TAG_REPOS_FRAGMENT
        with (binding) {
            reposList.adapter = ReposAdapter(
                clickListener = {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, RepoDetailsFragment.newInstance(it.id))
                        .commit()
                }
            )
            refreshLayout.setColorSchemeColors(
                ContextCompat.getColor(requireActivity(), R.color.colorPrimary),
                ContextCompat.getColor(requireActivity(), R.color.colorAccent),
                ContextCompat.getColor(requireActivity(), R.color.colorPrimaryDark)
            )
            refreshLayout.setOnRefreshListener {
                viewModel.fetchRepos(true)
            }
        }
        viewModel.fetchRepos(false)
        viewModel.repos.observe(viewLifecycleOwner, {
            with (binding) {
                (reposList.adapter as ReposAdapter).apply {
                    repos = it
                    notifyDataSetChanged()
                }
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, {
            with (binding) { refreshLayout.isRefreshing = it }
        })
        viewModel.error.observe(viewLifecycleOwner, {
            with (binding) { Snackbar.make(refreshLayout, it, Snackbar.LENGTH_LONG).show() }
        })
        return view
    }
}