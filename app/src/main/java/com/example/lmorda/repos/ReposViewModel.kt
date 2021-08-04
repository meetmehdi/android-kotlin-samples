package com.example.lmorda.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lmorda.R
import com.example.lmorda.SORT_STARS
import com.example.lmorda.data.RepoRepository
import com.example.lmorda.data.Result
import com.example.lmorda.model.Repo
import kotlinx.coroutines.launch

class ReposViewModel(private val repoRepository: RepoRepository) : ViewModel() {

    sealed class ViewState {
        data class Loading(val isLoading: Boolean): ViewState()
        data class Error(val resId: Int): ViewState()
        data class Success(val repos: List<Repo>): ViewState()
    }

    private val _viewState = MutableLiveData<ViewState>()
    val viewState: LiveData<ViewState> = _viewState

    fun fetchRepos(forceRefresh: Boolean) {
        viewModelScope.launch {
            _viewState.postValue(ViewState.Loading(true))
            val result = repoRepository.getRepos(
                forceRefresh = forceRefresh,
                sort = SORT_STARS
            )
            when (result) {
                is Result.Success -> _viewState.postValue(
                    result.data?.let { ViewState.Success(it) }
                        ?: ViewState.Error(R.string.repos_error)
                )
                is Result.Error -> _viewState.postValue(
                    ViewState.Error(R.string.repos_error))
            }
        }
    }
}