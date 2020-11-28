package sanchez.sanchez.sergio.postsapp.persistence.db.room.entity

import androidx.room.ColumnInfo

/**
 * Comment Entity
 */

data class CommentEntity (
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "body")
    val body: String
)
