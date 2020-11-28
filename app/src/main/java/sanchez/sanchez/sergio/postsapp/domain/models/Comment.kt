package sanchez.sanchez.sergio.postsapp.domain.models

/**
 * Comment Domain Model
 */

data class Comment (
    val id: Long,
    val name: String,
    val email: String,
    val body: String
)
