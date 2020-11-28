package sanchez.sanchez.sergio.postsapp.persistence.db.room.dao.post

import androidx.room.Dao
import sanchez.sanchez.sergio.postsapp.persistence.db.room.dao.core.SupportDAO
import sanchez.sanchez.sergio.postsapp.persistence.db.room.entity.PostEntity

/**
 * Post DAO Impl
 */
@Dao
abstract class PostDAOImpl: SupportDAO<PostEntity>(), IPostDAO