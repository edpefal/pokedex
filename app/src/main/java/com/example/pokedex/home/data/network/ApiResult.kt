package com.example.pokedex.home.data.network

sealed class ApiResult<out T> {
    data class Error(val exception: Exception): ApiResult<Nothing>()
    data class Success<T>(val data: T): ApiResult<T>()
}