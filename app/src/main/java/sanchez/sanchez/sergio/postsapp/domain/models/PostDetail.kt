package sanchez.sanchez.sergio.postsapp.domain.models

/**
 * Post Detail Domain Model
 */

data class PostDetail (
    val id: Long,
    val title: String,
    val body: String,
    val author: User,
    val commentList: List<Comment>
)