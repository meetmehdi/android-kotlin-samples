package com.example.lmorda.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lmorda.SORT_STARS
import com.example.lmorda.data.RepoRepository
import com.example.lmorda.data.Result
import com.example.lmorda.model.Repo
import kotlinx.coroutines.launch

class ReposViewModel(private val repoRepository: RepoRepository) : ViewModel() {

    val loading: LiveData<Boolean> = repoRepository.loading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _repos = MutableLiveData<List<Repo>>()
    val repos: LiveData<List<Repo>> = _repos

    fun fetchRepos(forceRefresh: Boolean) {
        viewModelScope.launch {
            val repos = repoRepository.getRepos(
                forceRefresh = forceRefresh,
                sort = SORT_STARS
            )
            when (repos) {
                is Result.Success -> _repos.postValue(repos.data)
                is Result.Error -> _error.postValue(repos.exception.message)
            }
        }
    }
}