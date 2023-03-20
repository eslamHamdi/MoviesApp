package com.eslam.moviesapp.domain.models

sealed class Result <out T> {
    data class Success<out R>(val value: R): Result<R>()
    data class Failure(
        val message: String?,
        val statusCode: Int?
    ): Result<Nothing>()

    class Loading<T>: Result<T>()
}
