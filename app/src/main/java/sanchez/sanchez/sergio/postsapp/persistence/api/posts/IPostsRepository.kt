package sanchez.sanchez.sergio.postsapp.persistence.api.posts

import sanchez.sanchez.sergio.postsapp.domain.models.Post
import sanchez.sanchez.sergio.postsapp.domain.models.PostDetail
import sanchez.sanchez.sergio.postsapp.persistence.api.exception.RepoErrorException
import sanchez.sanchez.sergio.postsapp.persistence.api.exception.RepoNoResultException

/**
 * Posts Repository
 */
interface IPostsRepository {

    /**
     * Find All Posts
     */
    @Throws(RepoNoResultException::class, RepoErrorException::class)
    suspend fun findAllPosts(): List<Post>

    /**
     * Find By Id
     */
    @Throws(RepoNoResultException::class, RepoErrorException::class)
    suspend fun findById(id: Long): PostDetail

}