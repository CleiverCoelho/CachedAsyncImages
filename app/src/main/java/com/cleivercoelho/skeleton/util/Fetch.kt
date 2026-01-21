package com.cleivercoelho.skeleton.util

sealed class Fetch<out T> {
    data class Success<T>(val data: T): Fetch<T>()
    data class Error(val message: String, val exception: Throwable? = null): Fetch<Nothing>()
    object Loading: Fetch<Nothing>()
}