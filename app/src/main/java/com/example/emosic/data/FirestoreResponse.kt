package com.example.emosic.data

sealed class FirestoreResponse<out T> {
    object Loading: FirestoreResponse<Nothing>()

    data class Success<out T>(
        val data: T
    ): FirestoreResponse<T>()

    data class Failure(
        val e: Exception?
    ): FirestoreResponse<Nothing>()
}