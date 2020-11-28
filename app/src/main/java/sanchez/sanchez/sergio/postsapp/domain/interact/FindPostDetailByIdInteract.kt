package sanchez.sanchez.sergio.postsapp.domain.interact

import sanchez.sanchez.sergio.postsapp.domain.models.PostDetail
import sanchez.sanchez.sergio.postsapp.persistence.api.posts.IPostsRepository

/**
 * Use case for get post detail by id
 * @param postRepository
 */
class FindPostDetailByIdInteract(
    private val postRepository: IPostsRepository
) {

    /**
     * Execute
     * @param onSuccess
     * @param onError
     */
    suspend fun execute(
        params: Params,
        onSuccess: (post: PostDetail) -> Unit,
        onError: (ex: Exception) -> Unit) = try {
        val postDetail = postRepository.findById(params.postId)
        onSuccess(postDetail)
    } catch (ex: Exception) {
        onError(ex)
    }

    /**
     * Params
     */
    data class Params(
        val postId: Long
    )
}