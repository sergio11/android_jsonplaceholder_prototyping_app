package sanchez.sanchez.sergio.postsapp.persistence.network.models

import com.google.gson.annotations.SerializedName

/**
 * Comment DTO Definition
 */

data class CommentDTO (
    @SerializedName(value = "postId")
    val postID: Long,
    @SerializedName(value = "id")
    val id: Long,
    @SerializedName(value = "name")
    val name: String,
    @SerializedName(value = "email")
    val email: String,
    @SerializedName(value = "body")
    val body: String
)
