package sanchez.sanchez.sergio.postsapp.ui.features.posts.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sanchez.sanchez.sergio.postsapp.domain.interact.FindAllPostsInteract
import sanchez.sanchez.sergio.postsapp.domain.models.Post
import sanchez.sanchez.sergio.postsapp.persistence.api.exception.RepoNoResultException
import javax.inject.Inject

/**
 * Posts View Model
 */
class PostListViewModel @Inject constructor(
    private val findAllPostsInteract: FindAllPostsInteract
): ViewModel() {

    /**
     * Live Data - Definitions
     */

    private val _postsListState by lazy {
        MutableLiveData<PostsListState>()
    }

    val postsListState: LiveData<PostsListState> = _postsListState


    /**
     * Load All posts
     */
    fun load() = viewModelScope.launch {
        findAllPostsInteract.execute(
            onSuccess = fun(posts) {
                _postsListState.postValue(
                    PostsListState.OnSuccess(posts)
                )
            },
            onError = fun(ex: Exception) {
                _postsListState.postValue(
                    if(ex is RepoNoResultException)
                        PostsListState.OnNotFound
                    else
                        PostsListState.OnError(ex)
                )
            }
        )
    }

}

sealed class PostsListState {

    /**
     * On Success
     * @param postsList
     */
    data class OnSuccess(val postsList: List<Post>): PostsListState()

    /**
     * On Error
     * @param ex
     */
    data class OnError(val ex: Exception): PostsListState()

    /**
     * On Not Found
     */
    object OnNotFound: PostsListState()

}