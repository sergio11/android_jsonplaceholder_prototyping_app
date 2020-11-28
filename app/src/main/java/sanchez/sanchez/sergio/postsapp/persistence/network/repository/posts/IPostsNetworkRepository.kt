package sanchez.sanchez.sergio.postsapp.persistence.network.repository.posts

import sanchez.sanchez.sergio.postsapp.domain.models.Post
import sanchez.sanchez.sergio.postsapp.domain.models.PostDetail
import sanchez.sanchez.sergio.postsapp.persistence.network.exception.NetworkException
import sanchez.sanchez.sergio.postsapp.persistence.network.exception.NetworkNoResultException

/**
 * Posts Network Repository
 */
interface IPostsNetworkRepository {

    /**
     * Find All
     */
    @Throws(NetworkException::class, NetworkNoResultException::class)
    suspend fun findAll(): List<Post>

    /**
     * Find By Id
     * @param id
     */
    @Throws(NetworkException::class, NetworkNoResultException::class)
    suspend fun findById(id: Long): PostDetail
}