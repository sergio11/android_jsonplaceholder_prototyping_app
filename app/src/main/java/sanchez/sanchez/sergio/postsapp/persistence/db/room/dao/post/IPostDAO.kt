package sanchez.sanchez.sergio.postsapp.persistence.db.room.dao.post

import androidx.room.Query
import sanchez.sanchez.sergio.postsapp.persistence.db.room.dao.core.ISupportDAO
import sanchez.sanchez.sergio.postsapp.persistence.db.room.entity.PostEntity

/**
 * Post DAO
 */
interface IPostDAO: ISupportDAO<PostEntity> {

    @Query("SELECT * FROM posts WHERE :id = id")
    fun findOneById(id: Long): PostEntity?

    @Query("DELETE FROM posts")
    fun deleteAll()
}