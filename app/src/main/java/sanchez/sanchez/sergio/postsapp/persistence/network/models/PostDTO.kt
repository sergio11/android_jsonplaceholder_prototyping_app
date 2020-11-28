package sanchez.sanchez.sergio.postsapp.persistence.network.models

import com.google.gson.annotations.SerializedName

/**
 * Post DTO Model definition
 */
data class PostDTO (
    // Post Id
    @SerializedName(value = "id")
    val id: Long,
    // Post Title
    @SerializedName(value = "title")
    val title: String,
    // Post Body
    @SerializedName(value = "body")
    val body: String,
    // Post User Id
    @SerializedName(value = "userId")
    val userId: Long
)