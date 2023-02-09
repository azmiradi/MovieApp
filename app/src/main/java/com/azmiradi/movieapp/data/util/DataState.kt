package com.azmiradi.movieapp.data.util


data class DataState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: String = "",
 )
