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
import com.example.lmorda.details.RepoDetailsFragment
import com.example.lmorda.utils.Utils.visible
import com.example.lmorda.utils.getViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_repos.*

class ReposFragment : Fragment() {

    private val adapter = ReposAdapter()

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
        MainActivity.FRAGMENT_TAG = TAG_REPOS_FRAGMENT
        setupListAdapter()
        viewModel.fetchRepos()
        viewModel.repos.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        viewModel.error.observe(viewLifecycleOwner, {
            Snackbar.make(repos_list, it, Snackbar.LENGTH_LONG).show()
        })
        viewModel.loading.observe(viewLifecycleOwner, {
            repos_spinner.visible = it
        })
    }

    private fun setupListAdapter() {
        repos_list.adapter = adapter
        adapter.apply {
            setClickListener {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, RepoDetailsFragment.newInstance(it.id))
                    .commit()
            }
        }
    }
}