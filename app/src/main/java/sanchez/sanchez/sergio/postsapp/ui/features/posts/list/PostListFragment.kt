package sanchez.sanchez.sergio.postsapp.ui.features.posts.list

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.post_list_fragment_layout.*
import kotlinx.android.synthetic.main.posts_fragment_content_layout.view.*
import sanchez.sanchez.sergio.postsapp.R
import sanchez.sanchez.sergio.postsapp.di.component.post.PostListFragmentComponent
import sanchez.sanchez.sergio.postsapp.di.factory.DaggerComponentFactory
import sanchez.sanchez.sergio.postsapp.domain.models.Post
import sanchez.sanchez.sergio.postsapp.ui.core.SupportFragment
import sanchez.sanchez.sergio.postsapp.ui.core.ext.navigate
import timber.log.Timber

/**
 * Post List Fragment
 */
class PostListFragment: SupportFragment<PostListViewModel>(PostListViewModel::class.java),
    PostListAdapter.OnPostClickListener {

    private val fragmentComponent: PostListFragmentComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerComponentFactory.getPostListFragmentComponent(requireActivity() as AppCompatActivity)
    }

    lateinit var recyclerViewAdapter: PostListAdapter

    override fun layoutId(): Int = R.layout.post_list_fragment_layout

    override fun onInject() {
        fragmentComponent.inject(this)
    }

    override fun onObserverLiveData(viewModel: PostListViewModel) {
        super.onObserverLiveData(viewModel)
        // Observe operation result
        viewModel.postsListState.observe(this, {
            when(it) {
                is PostsListState.OnSuccess -> onPostListLoaded(it.postsList)
                is PostsListState.OnError -> onErrorOccurred(it.ex)
                is PostsListState.OnNotFound -> {

                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Recycler View Adapter
        recyclerViewAdapter = PostListAdapter(requireContext(), mutableListOf(), this)
        // Configure Recycler View
        contentView.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(object: RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = resources.getDimension(R.dimen.item_vertical_separation).toInt()
                }
            })
            isNestedScrollingEnabled = true
            adapter = recyclerViewAdapter
        }

        swipeRefreshLayout.setOnRefreshListener { loadPosts() }

        viewModel.postsListState.value?.let {
            if(it is PostsListState.OnSuccess)
                onPostListLoaded(it.postsList)
            else
                loadPosts()
        } ?: loadPosts()
    }

    /**
     * Private Methods
     */

    /**
     * On Post List Loaded
     * @param postList
     */
    private fun onPostListLoaded(postList: List<Post>) {
        Timber.d("onPostListLoaded CALLED, postList -> %d", postList.size)
        loadingView.visibility = View.GONE
        errorView.visibility = View.GONE
        contentView.visibility = View.VISIBLE
        swipeRefreshLayout.isRefreshing = false

        recyclerViewAdapter.replaceData(postList)
    }

    /**
     * On Error Occurred
     * @param ex
     */
    private fun onErrorOccurred(ex: Exception) {
        Timber.d("onErrorOccurred CALLED, message -> %s", ex.message)
        swipeRefreshLayout.isRefreshing = false
        loadingView.visibility = View.GONE
        errorView.visibility = View.VISIBLE
        contentView.visibility = View.GONE
        Snackbar.make(requireView(), getString(R.string.error_occurred_message), Snackbar.LENGTH_LONG).show()
    }

    /**
     * Load Posts
     */
    private fun loadPosts() {
        loadingView.visibility = View.VISIBLE
        errorView.visibility = View.GONE
        contentView.visibility= View.GONE
        recyclerViewAdapter.clearData()
        viewModel.load()
    }

    /**
     * On Post Clicked
     */
    override fun onPostClicked(post: Post) {
        Timber.d("onPostClicked CALLED, Post Id -> ${post.id}")
        navigate(PostListFragmentDirections.actionPostListFragmentToPostDetailFragment(post.id))
    }


}