package com.example.lmorda

import android.app.Application
import com.example.lmorda.data.RepoRepository

class App : Application() {

    val repoRepository: RepoRepository
        get() = ServiceLocator.provideReposRepository()
}