package sanchez.sanchez.sergio.postsapp.persistence.network.repository.posts

import sanchez.sanchez.sergio.postsapp.domain.models.Post
import sanchez.sanchez.sergio.postsapp.domain.models.PostDetail
import sanchez.sanchez.sergio.postsapp.persistence.network.exception.NetworkNoResultException
import sanchez.sanchez.sergio.postsapp.persistence.network.mapper.PostNetworkMapper
import sanchez.sanchez.sergio.postsapp.persistence.network.models.CommentDTO
import sanchez.sanchez.sergio.postsapp.persistence.network.repository.core.SupportNetworkRepository
import sanchez.sanchez.sergio.postsapp.persistence.network.service.ICommentsService
import sanchez.sanchez.sergio.postsapp.persistence.network.service.IPostsService
import sanchez.sanchez.sergio.postsapp.persistence.network.service.IUsersService

/**
 * Post Network Repository Impl
 */
open class PostsNetworkRepositoryImpl(
    private val postsService: IPostsService,
    private val commentService: ICommentsService,
    private val userService: IUsersService,
    private val postNetworkMapper: PostNetworkMapper
): SupportNetworkRepository(), IPostsNetworkRepository {

    /**
     * Find All
     */
    override suspend fun findAll(): List<Post> = safeNetworkCall {
        val posts = postsService.getPosts()
        if(posts.isEmpty())
            throw NetworkNoResultException("No posts was found")
        postNetworkMapper.dtoToModel(posts)
    }

    /**
     * Find By Id
     * @param id
     */
    override suspend fun findById(id: Long): PostDetail = safeNetworkCall {
        // Get Post Detail
        val post = postsService.getPostById(id)
        // Get Comments for this post
        val comments = try {
            commentService.getCommentsForPost(id)
        } catch (ex: Exception) {
            // Post could not be a comments
            emptyList()
        }
        // Get the author of this post
        val author = userService.getUserDetail(post.userId)
        // transform to model
        postNetworkMapper.dtoToModel(
            post, comments, author
        )
    }
}