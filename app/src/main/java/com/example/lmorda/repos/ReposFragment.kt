package com.example.lmorda.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lmorda.DETAILS_ID_BUNDLE_KEY
import com.example.lmorda.R
import com.example.lmorda.databinding.FragmentReposBinding
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
        with (binding) {
            refreshLayout.setSpinnerColors(requireActivity())
            refreshLayout.setOnRefreshListener { viewModel.fetchRepos(true) }
            reposList.adapter = ReposAdapter(
                clickListener = {
                findNavController().navigate(R.id.action_reposFragment_to_repoDetailsFragment,
                    bundleOf(DETAILS_ID_BUNDLE_KEY to it.id))
            })
            viewModel.fetchRepos(false)
            viewModel.viewState.observe(viewLifecycleOwner, {
                when (it) {
                    is ViewState.Loading -> {
                        refreshLayout.isRefreshing = it.isLoading
                    }
                    is ViewState.Success -> {
                        (reposList.adapter as ReposAdapter).display(it.repos)
                        refreshLayout.isRefreshing = false
                    }
                    is ViewState.Error -> {
                        showSnack(refreshLayout, it.resId)
                        refreshLayout.isRefreshing = false
                    }
                }
            })
        }
        return view
    }
}