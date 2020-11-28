package sanchez.sanchez.sergio.postsapp.persistence.network.service

import retrofit2.http.GET
import retrofit2.http.Path
import sanchez.sanchez.sergio.postsapp.persistence.network.models.UserDTO

/**
 * Users API
 * ==================
 * /users	10 users
 *
 */

interface IUsersService {

    /**
     * Get User Detail
     * @param userId
     */
    @GET("users/{userId}")
    suspend fun getUserDetail(@Path("userId") userId: Long): UserDTO
}