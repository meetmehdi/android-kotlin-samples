package com.example.lmorda.data

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorResId: Int) : Result<Nothing>()

}