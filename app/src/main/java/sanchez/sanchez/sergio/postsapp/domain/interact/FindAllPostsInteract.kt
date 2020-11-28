package sanchez.sanchez.sergio.postsapp.domain.interact

import sanchez.sanchez.sergio.postsapp.domain.models.Post
import sanchez.sanchez.sergio.postsapp.persistence.api.posts.IPostsRepository

/**
 * Use case for get all posts
 * @param postRepository
 */
class FindAllPostsInteract(
    private val postRepository: IPostsRepository
) {

    /**
     * Execute
     * @param onSuccess
     * @param onError
     */
    suspend fun execute(
        onSuccess: (postList: List<Post>) -> Unit,
        onError: (ex: Exception) -> Unit) = try {
        onSuccess(postRepository.findAllPosts())
    } catch (ex: Exception) {
        onError(ex)
    }

}