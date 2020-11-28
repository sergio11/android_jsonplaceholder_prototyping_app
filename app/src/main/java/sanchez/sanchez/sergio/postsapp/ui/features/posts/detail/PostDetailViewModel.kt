package sanchez.sanchez.sergio.postsapp.ui.features.posts.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sanchez.sanchez.sergio.postsapp.domain.interact.FindPostDetailByIdInteract
import sanchez.sanchez.sergio.postsapp.domain.models.PostDetail
import javax.inject.Inject

/**
 * Post Detail View Model
 * @param findPostDetailByIdInteract
 */
class PostDetailViewModel @Inject constructor(
    private val findPostDetailByIdInteract: FindPostDetailByIdInteract
): ViewModel() {

    /**
     * Live Data - Definitions
     */

    private val _postDetailState by lazy {
        MutableLiveData<PostDetailState>()
    }

    val postDetailState: LiveData<PostDetailState> = _postDetailState

    /**
     * Public Methods
     */

    /**
     * Load By Id
     * @param postId
     */
    fun loadById(postId: Long) = viewModelScope.launch {
        findPostDetailByIdInteract.execute(
            params = FindPostDetailByIdInteract.Params(postId),
            onSuccess = fun(post) {
                _postDetailState.postValue(
                    PostDetailState.OnSuccess(post)
                )
            },
            onError = fun(ex: Exception) {
                _postDetailState.postValue(
                    PostDetailState.OnError(ex)
                )
            }
        )
    }

}

sealed class PostDetailState {

    /**
     * On Success
     * @param postDetail
     */
    data class OnSuccess(val postDetail: PostDetail): PostDetailState()

    /**
     * On Error
     * @param ex
     */
    data class OnError(val ex: Exception): PostDetailState()

}