package sanchez.sanchez.sergio.postsapp.persistence.db.repository.post

import sanchez.sanchez.sergio.postsapp.domain.models.PostDetail
import sanchez.sanchez.sergio.postsapp.persistence.api.exception.RepoErrorException
import sanchez.sanchez.sergio.postsapp.persistence.api.exception.RepoNoResultException

interface IPostDBRepository {

    /**
     * Find By Id
     * @param id
     */
    @Throws(RepoNoResultException::class, RepoErrorException::class)
    suspend fun findById(id: Long): PostDetail

    /**
     * Save Post
     * @param postDetail
     */
    @Throws(RepoNoResultException::class, RepoErrorException::class)
    suspend fun save(postDetail: PostDetail)

    /**
     * Delete All
     */
    @Throws(RepoErrorException::class)
    suspend fun deleteAll()
}