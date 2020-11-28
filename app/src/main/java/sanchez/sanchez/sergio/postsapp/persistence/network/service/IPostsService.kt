package sanchez.sanchez.sergio.postsapp.persistence.network.service

import retrofit2.http.GET
import retrofit2.http.Path
import sanchez.sanchez.sergio.postsapp.persistence.network.models.PostDTO

/**
 * Posts API
 * ==================
 * /posts	100 posts
 *
 */

interface IPostsService {

    /**
     * Get Posts
     */
    @GET("posts")
    suspend fun getPosts(): List<PostDTO>

    /**
     * Get Post By Id
     * @param postId
     */
    @GET("posts/{postId}")
    suspend fun getPostById(@Path("postId") postId: Long): PostDTO
}