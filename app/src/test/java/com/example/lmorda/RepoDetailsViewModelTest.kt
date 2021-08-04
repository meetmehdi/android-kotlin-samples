package com.example.lmorda

import android.util.Log
import com.example.lmorda.data.MockRepoRepository
import com.example.lmorda.details.RepoDetailsViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.lmorda.model.Repo
import com.example.lmorda.data.Result
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals

class RepoDetailsViewModelTest {

    private lateinit var repoDetailsViewModel: RepoDetailsViewModel
    private lateinit var repoRepository: MockRepoRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupViewModel() {
        repoRepository = MockRepoRepository()
        repoDetailsViewModel = RepoDetailsViewModel(repoRepository)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testSomething() {

        mainCoroutineRule.runBlockingTest {
            val result = repoRepository.getRepo(0L)
            assertEquals("string", when (result) {
                is Result.Success -> result.data.description
                else -> ""
            })
        }

    }
}