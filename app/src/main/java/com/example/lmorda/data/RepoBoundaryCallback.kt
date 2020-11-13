package com.example.lmorda.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.example.lmorda.SORT_API_STARS
import com.example.lmorda.api.RepoApiService
import com.example.lmorda.data.RepoRepository.Companion.NETWORK_PAGE_SIZE
import com.example.lmorda.db.RepoRoomService
import com.example.lmorda.model.Repo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class RepoBoundaryCallback(
    private val apiService: RepoApiService,
    private val roomService: RepoRoomService,
    private val scope: CoroutineScope,
    private val sort: String = SORT_API_STARS
) : PagedList.BoundaryCallback<Repo>() {

    private var lastRequestedPage = 1

    private val _networkErrors = MutableLiveData<String>()
    val networkErrors: LiveData<String> = _networkErrors

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private var isRequestInProgress = false

    override fun onZeroItemsLoaded() = requestAndSaveData(sort)

    override fun onItemAtEndLoaded(itemAtEnd: Repo) = requestAndSaveData(sort)

    private fun requestAndSaveData(sort: String) {
        if (isRequestInProgress) return
        isRequestInProgress = true
        scope.launch {
            if (lastRequestedPage > 1) {
                _loading.postValue(true)
            }
            try {
                apiService.getRepos(sort, null, lastRequestedPage, NETWORK_PAGE_SIZE)?.let {
                    roomService.insert(it)
                    lastRequestedPage++
                    isRequestInProgress = false
                } ?: run {
                    _networkErrors.postValue("Response from server is null!")
                    isRequestInProgress = false
                }
            } catch (ex: Exception) {
                _networkErrors.postValue(ex.message)
                isRequestInProgress = false
            }
            _loading.postValue(false)
        }
    }
}