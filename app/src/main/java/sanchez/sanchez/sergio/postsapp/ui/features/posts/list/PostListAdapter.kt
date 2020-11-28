package sanchez.sanchez.sergio.postsapp.ui.features.posts.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import sanchez.sanchez.sergio.postsapp.R
import sanchez.sanchez.sergio.postsapp.domain.models.Post

/**
 * Post List Adapter
 */
class PostListAdapter(
    private val context: Context,
    private val data: MutableList<Post>,
    private val postItemListener: OnPostClickListener
): RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {


    /**
     * Create View Holder
     * @param parent
     * @param viewType
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder(inflateLayout(R.layout.post_item_list_layout, parent))

    /**
     * On Bind Model to View Holder
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        data.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    /**
     * Clear Data
     */
    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    /**
     * Replace Data
     * @param newData
     */
    fun replaceData(newData: List<Post>) {
        data.apply {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()
    }

    /**
     * Private Methods
     */

    /**
     * Inflate Layout
     */
    private fun inflateLayout(@LayoutRes layoutRest: Int, parent: ViewGroup): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return inflater.inflate(layoutRest, parent, false)
    }

    interface OnPostClickListener {

        /**
         * On Post Clicked
         * @param post
         */
        fun onPostClicked(post: Post)
    }

    /**
     * Post View Holder
     * @param itemView
     */
    inner class PostViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        /**
         * Bind Model to view
         */
        fun bind(post: Post) {
            itemView.apply {
                findViewById<TextView>(R.id.postTitleTextView)?.text = post.title
                findViewById<TextView>(R.id.postBodyTextView)?.text = post.body
                setOnClickListener {
                    postItemListener.onPostClicked(post)
                }
            }
        }
    }

}