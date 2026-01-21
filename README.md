# 🖼️ CachedAsyncImage

A lightweight Jetpack Compose component for async image loading with built-in memory and disk caching.

## ✨ Features

- ⚡ Async image loading with Coil
- 💾 Memory cache (25% of app memory)
- 📁 Disk cache (100MB)
- 🎬 Crossfade animation
- 🔄 Loading & error states
- 🎨 Color filter support
- 🔗 Shared OkHttp connection pool

## 📦 Installation

Add to your `build.gradle.kts`:

```kotlin
dependencies {
implementation("io.coil-kt:coil-compose:2.7.0")
}

🚀 Usage
Basic
kotlin
CachedImage(
imageUrl = "https://example.com/image.jpg",
contentDescription = "Profile picture"
)

With Custom Options
kotlin
CachedImage(
imageUrl = "https://example.com/image.jpg",
contentDescription = "Product image",
modifier = Modifier
    .size(200.dp)
    .clip(RoundedCornerShape(16.dp)),
contentScale = ContentScale.Crop,
placeholder = painterResource(R.drawable.placeholder),
error = painterResource(R.drawable.error_image),
colorFilter = ColorFilter.tint(Color.Gray)
)

⚙️ Cache Configuration (Hilt)
kotlin
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
        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)  // 25% of app memory
                .build()
        }
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizeBytes(100L * 1024 * 1024)  // 100MB
                .build()
        }
        .okHttpClient(okHttpClient)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .crossfade(300)
        .build()
}
}

📖 API Reference
Parameter	Type	Default	Description
imageUrl	String?	required	Image URL to load
contentDescription	String?	required	Accessibility description
modifier	Modifier	Modifier	Compose modifier
placeholder	Painter?	null	Shown while loading
error	Painter?	null	Shown on error
contentScale	ContentScale	Crop	Image scaling
colorFilter	ColorFilter?	null	Color filter overlay
🏗️ Architecture
┌─────────────────┐
│  CachedImage    │  ← Composable
└────────┬────────┘
     │
┌────────▼────────┐
│ SubcomposeAsync │  ← Coil
│     Image       │
└────────┬────────┘
     │
┌────────▼────────┐
│  ImageLoader    │  ← Hilt Singleton
├─────────────────┤
│ • MemoryCache   │
│ • DiskCache     │
│ • OkHttpClient  │
└─────────────────┘

👤 Author

Cleiver Coelho - @CleiverCoelho

📄 License

MIT License
