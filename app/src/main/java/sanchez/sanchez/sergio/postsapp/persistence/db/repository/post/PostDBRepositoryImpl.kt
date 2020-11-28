package sanchez.sanchez.sergio.postsapp.persistence.db.repository.post

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sanchez.sanchez.sergio.postsapp.domain.models.PostDetail
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.exception.DBErrorException
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.exception.DBException
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.exception.DBNoResultException
import sanchez.sanchez.sergio.postsapp.persistence.db.room.dao.post.IPostDAO
import sanchez.sanchez.sergio.postsapp.persistence.db.room.mapper.PostDBMapper

/**
 * Post DB Repository Impl
 */
class PostDBRepositoryImpl(
    private val postDAO: IPostDAO,
    private val postDbMapper: PostDBMapper
): IPostDBRepository{

    /**
     * Find By Id
     * @param id
     */
    override suspend fun findById(id: Long): PostDetail = withContext(Dispatchers.IO) {
        try {
            postDAO.findOneById(id)?.let {
                postDbMapper.entityToModel(it)
            } ?: throw DBNoResultException("Can not find post with id $id")
        } catch (ex: DBException) {
            throw ex
        } catch (ex: Exception) {
            throw DBErrorException("Error occurred when find post", ex)
        }
    }

    /**
     * Save
     * @param postDetail
     */
    override suspend fun save(postDetail: PostDetail): Unit = withContext(Dispatchers.IO) {
       try {
           postDAO.insert(postDbMapper.modelToEntity(postDetail))
       } catch (ex: Exception) {
           throw DBErrorException("Error occurred when save the post", ex)
       }
    }

    /**
     * Delete All
     */
    override suspend fun deleteAll() = withContext(Dispatchers.IO) {
        try {
            postDAO.deleteAll()
        } catch (ex: Exception) {
            throw DBErrorException("Error occurred when delete posts", ex)
        }
    }
}