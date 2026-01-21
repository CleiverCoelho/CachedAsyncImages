package com.cleivercoelho.skeleton.data.remote.interceptor

import com.cleivercoelho.skeleton.data.local.datastore.AppDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStore: AppDataStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { dataStore.authToken.firstOrNull() }

        val request = chain.request().newBuilder().apply {
            token?.let {
                addHeader("Authorization", "Bearer $it")
            }
            addHeader("Content-Type", "application/json")
        }.build()

        return chain.proceed(request)
    }
}