package sanchez.sanchez.sergio.postsapp.domain.models

/**
 * User Domain Model
 */

data class User (
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val phone: String,
    val website: String,
    val company: String
)