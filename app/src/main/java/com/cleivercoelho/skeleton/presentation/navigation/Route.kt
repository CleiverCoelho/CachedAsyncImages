import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Home : Route

    @Serializable
    data class UserDetail(val userId: Int) : Route

    @Serializable
    data object Settings : Route
}