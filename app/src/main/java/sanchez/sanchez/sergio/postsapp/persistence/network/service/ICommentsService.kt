package sanchez.sanchez.sergio.postsapp.persistence.network.service

import retrofit2.http.GET
import retrofit2.http.Query
import sanchez.sanchez.sergio.postsapp.persistence.network.models.CommentDTO

/**
 * Comments API
 * ==================
 * /comments	500 comments
 *
 */

interface ICommentsService {

    /**
     * Get Comments for Post
     * @param postId
     */
    @GET("comments")
    suspend fun getCommentsForPost(@Query("postId") postId: Long): List<CommentDTO>
}