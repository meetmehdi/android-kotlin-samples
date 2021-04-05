package com.example.lmorda.repos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.lmorda.DETAILS_ID_BUNDLE_KEY
import com.example.lmorda.R
import com.example.lmorda.utils.getViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_repos.*

class ReposFragment : Fragment() {

    private val viewModel by viewModels<ReposViewModel> { getViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_repos, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListAdapter()
        setupRefreshLayout()
        viewModel.fetchRepos(false)
        viewModel.repos.observe(viewLifecycleOwner, {
            (repos_list.adapter as ReposAdapter).apply {
                repos = it
                notifyDataSetChanged()
            }
        })
        viewModel.loading.observe(viewLifecycleOwner, {
            refresh_layout.isRefreshing = it
        })
        viewModel.error.observe(viewLifecycleOwner, {
            Snackbar.make(refresh_layout, it, Snackbar.LENGTH_LONG).show()
        })
    }

    private fun setupListAdapter() {
        repos_list.adapter = ReposAdapter(
            clickListener = {
                findNavController().navigate(R.id.action_repoListFragment_to_repoDetailsFragment,
                    bundleOf(DETAILS_ID_BUNDLE_KEY to it.id))
            }
        )
    }

    private fun setupRefreshLayout() {
        refresh_layout.setColorSchemeColors(
            ContextCompat.getColor(requireActivity(), R.color.colorPrimary),
            ContextCompat.getColor(requireActivity(), R.color.colorAccent),
            ContextCompat.getColor(requireActivity(), R.color.colorPrimaryDark)
        )
        refresh_layout.setOnRefreshListener {
            viewModel.fetchRepos(true)
        }
    }
}