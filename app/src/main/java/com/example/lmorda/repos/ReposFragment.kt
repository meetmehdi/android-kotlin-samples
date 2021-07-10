package com.example.lmorda.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.lmorda.MainActivity
import com.example.lmorda.R
import com.example.lmorda.TAG_REPOS_FRAGMENT
import com.example.lmorda.databinding.FragmentReposBinding
import com.example.lmorda.details.RepoDetailsFragment
import com.example.lmorda.repos.ReposViewModel.*
import com.example.lmorda.utils.display
import com.example.lmorda.utils.getViewModelFactory
import com.example.lmorda.utils.setSpinnerColors
import com.example.lmorda.utils.showSnack

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
            refreshLayout.setSpinnerColors(requireActivity())
            refreshLayout.setOnRefreshListener { viewModel.fetchRepos(true) }
            viewModel.fetchRepos(false)
            viewModel.viewState.observe(viewLifecycleOwner, {
                when (it) {
                    is ViewState.Loading -> refreshLayout.isRefreshing = it.isLoading
                    is ViewState.Success -> (reposList.adapter as ReposAdapter).display(it.repos)
                    is ViewState.Error -> showSnack(refreshLayout, it.resId)
                }
            })
        }
        return view
    }
}