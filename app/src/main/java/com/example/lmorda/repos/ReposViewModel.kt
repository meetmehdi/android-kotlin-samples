package com.example.lmorda.repos

import androidx.lifecycle.*
import androidx.paging.PagedList
import com.example.lmorda.data.RepoRepository
import com.example.lmorda.data.Result
import com.example.lmorda.model.Repo

class ReposViewModel(private val repoRepository: RepoRepository) : ViewModel() {

    private val _repoResult = MutableLiveData<Result>()
    private val repoResult: LiveData<Result> = _repoResult

    val repos: LiveData<PagedList<Repo>> = Transformations.switchMap(repoResult) { it.data }
    val error: LiveData<String> = Transformations.switchMap(repoResult) { it.networkErrors }
    val loading: LiveData<Boolean> = Transformations.switchMap(repoResult) { it.loading }

    fun fetchRepos() {
        _repoResult.postValue(repoRepository.getRepos(viewModelScope))
    }

}