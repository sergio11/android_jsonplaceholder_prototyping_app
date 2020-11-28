package sanchez.sanchez.sergio.postsapp.persistence.db.room.mapper

import sanchez.sanchez.sergio.postsapp.domain.models.Comment
import sanchez.sanchez.sergio.postsapp.domain.models.PostDetail
import sanchez.sanchez.sergio.postsapp.domain.models.User
import sanchez.sanchez.sergio.postsapp.persistence.db.room.entity.CommentEntity
import sanchez.sanchez.sergio.postsapp.persistence.db.room.entity.PostEntity
import sanchez.sanchez.sergio.postsapp.persistence.db.room.entity.UserEntity

class PostDBMapper {

    /**
     * Entity To Model
     * @param postEntity
     */
    fun entityToModel(postEntity: PostEntity) =
        PostDetail(
            id = postEntity.id,
            title = postEntity.title,
            body = postEntity.body,
            author = entityToModel(postEntity.author),
            commentList = postEntity.commentList.map {
                entityToModel(it)
            }.toList()
        )

    /**
     * Model To Entity
     * @param postDetail
     */
    fun modelToEntity(postDetail: PostDetail) =
        PostEntity(
            id = postDetail.id,
            title = postDetail.title,
            body = postDetail.body,
            author = modelToEntity(postDetail.author),
            commentList = postDetail.commentList.map {
                modelToEntity(it)
            }.toMutableList()
        )

    /**
     * Private methods
     */

    /**
     * Model To Entity
     * @param user
     */
    private fun modelToEntity(user: User) =
        UserEntity(
            id = user.id,
            name = user.name,
            username = user.username,
            email = user.email,
            address = user.address,
            latitude = user.latitude,
            longitude = user.longitude,
            phone = user.phone,
            website = user.website,
            company = user.company
        )

    /**
     * Model To Entity
     * @param comment
     */
    private fun modelToEntity(comment: Comment) =
        CommentEntity(
            id = comment.id,
            name = comment.name,
            email = comment.email,
            body = comment.body
        )

    /**
     * Entity to Model
     * @param userEntity
     */
    private fun entityToModel(userEntity: UserEntity) =
        User(
            id = userEntity.id,
            name = userEntity.name,
            username = userEntity.username,
            email = userEntity.email,
            address = userEntity.address,
            latitude = userEntity.latitude,
            longitude = userEntity.longitude,
            phone = userEntity.phone,
            website = userEntity.website,
            company = userEntity.company
        )

    /**
     * Entity To Model
     * @param commentEntity
     */
    private fun entityToModel(commentEntity: CommentEntity) =
        Comment(
            id = commentEntity.id,
            name = commentEntity.name,
            email = commentEntity.email,
            body = commentEntity.body
        )
}