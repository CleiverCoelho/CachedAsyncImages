package com.cleivercoelho.skeleton.data.remote.api

import com.cleivercoelho.skeleton.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<UserDto>>

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: Int): Response<UserDto>

    @POST("users")
    suspend fun createUser(@Body user: UserDto): Response<UserDto>

    @DELETE("users/{id}")
    suspend fun deleteUser(@Path("id") id: Int): Response<Unit>
}