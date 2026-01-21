package com.cleivercoelho.skeleton.di

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.util.DebugLogger
import com.cleivercoelho.skeleton.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoilModule {

    @Provides
    @Singleton
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): ImageLoader {
        return ImageLoader.Builder(context)
            // Memory cache: 25% of app memory
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .build()
            }
            // Disk cache: 100MB
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizeBytes(100L * 1024 * 1024) // 100 MB
                    .build()
            }
            // Use same OkHttpClient (shares connection pool)
            .okHttpClient(okHttpClient)
            // Cache policies
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            // Respect cache headers
            .respectCacheHeaders(true)
            // Crossfade animation
            .crossfade(true)
            .crossfade(300)
            // Debug logging
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger())
                }
            }
            .build()
    }
}