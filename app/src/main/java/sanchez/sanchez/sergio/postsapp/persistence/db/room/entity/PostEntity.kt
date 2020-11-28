package sanchez.sanchez.sergio.postsapp.persistence.db.room.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Post Entity Definition
 */
@Entity(tableName = "posts")
data class PostEntity (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,
    @NonNull
    @ColumnInfo(name = "title")
    val title: String,
    @NonNull
    @ColumnInfo(name = "body")
    val body: String,
    @ColumnInfo(name = "author")
    val author: UserEntity,
    @ColumnInfo(name = "comments")
    val commentList: List<CommentEntity>
)