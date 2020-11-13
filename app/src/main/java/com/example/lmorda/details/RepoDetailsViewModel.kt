package com.example.lmorda.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lmorda.data.RepoRepository
import com.example.lmorda.model.Repo
import kotlinx.coroutines.launch

class RepoDetailsViewModel(private val repoRepository: RepoRepository) : ViewModel() {

    private val _repo = MutableLiveData<Repo>()
    val repo: LiveData<Repo?> = _repo

    fun fetchRepo(id: Long) {
        viewModelScope.launch {
            _repo.postValue(repoRepository.getRepo(id))
        }
    }
}