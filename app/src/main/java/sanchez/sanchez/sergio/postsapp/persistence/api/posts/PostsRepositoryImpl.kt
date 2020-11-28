package sanchez.sanchez.sergio.postsapp.persistence.api.posts

import sanchez.sanchez.sergio.postsapp.domain.models.Post
import sanchez.sanchez.sergio.postsapp.domain.models.PostDetail
import sanchez.sanchez.sergio.postsapp.persistence.api.exception.RepoErrorException
import sanchez.sanchez.sergio.postsapp.persistence.api.exception.RepoException
import sanchez.sanchez.sergio.postsapp.persistence.api.exception.RepoNoResultException
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.exception.DBNoResultException
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.post.IPostDBRepository
import sanchez.sanchez.sergio.postsapp.persistence.network.exception.NetworkNoResultException
import sanchez.sanchez.sergio.postsapp.persistence.network.repository.posts.IPostsNetworkRepository

/**
 * Posts Repository Impl
 * @param postNetworkRepository
 * @param postDBRepository
 */
class PostsRepositoryImpl(
    private val postNetworkRepository: IPostsNetworkRepository,
    private val postDBRepository: IPostDBRepository
): IPostsRepository {

    /**
     * Find All Posts
     */
    override suspend fun findAllPosts(): List<Post> = try {
        postNetworkRepository.findAll()
    } catch(ex: NetworkNoResultException) {
        throw RepoNoResultException(ex)
    } catch (ex: Exception) {
        throw RepoErrorException(ex)
    }

    /**
     * Find By Id
     * @param id
     */
    override suspend fun findById(id: Long): PostDetail = try {
        // First, try to get the post from local persistence
        postDBRepository.findById(id)
    } catch(ex: DBNoResultException) {
        try {
            // Try to get the post from network source and save to local persistence
            postNetworkRepository.findById(id).also {
                postDBRepository.save(it)
            }
        } catch(ex: NetworkNoResultException) {
            throw RepoNoResultException(ex)
        }
    } catch (ex: RepoException) {
        throw ex
    } catch (ex: Exception) {
        throw RepoErrorException(ex)
    }
}