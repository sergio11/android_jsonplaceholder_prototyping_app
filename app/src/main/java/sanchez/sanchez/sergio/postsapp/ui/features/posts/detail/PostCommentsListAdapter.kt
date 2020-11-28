package sanchez.sanchez.sergio.postsapp.ui.features.posts.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import sanchez.sanchez.sergio.postsapp.R
import sanchez.sanchez.sergio.postsapp.domain.models.Comment
import sanchez.sanchez.sergio.postsapp.ui.core.component.MaterialListItem

/**
 * Post Comments List Adapter
 */
class PostCommentsListAdapter(
    private val context: Context,
    private val data: MutableList<Comment>
): RecyclerView.Adapter<PostCommentsListAdapter.CommentViewHolder>() {


    /**
     * Create View Holder
     * @param parent
     * @param viewType
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder =
        CommentViewHolder(inflateLayout(R.layout.comment_item_list_layout, parent))

    /**
     * On Bind Model to View Holder
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        data.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    override fun getItemCount(): Int = data.size

    /**
     * Replace Data
     * @param newData
     */
    fun replaceData(newData: List<Comment>) {
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


    inner class CommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        /**
         * Bind Model to view
         */
        fun bind(comment: Comment) {
            if(itemView is MaterialListItem)
                itemView.apply {
                    labelText = "${comment.name} (${comment.email})"
                    valueText = comment.body
                }

        }
    }

}