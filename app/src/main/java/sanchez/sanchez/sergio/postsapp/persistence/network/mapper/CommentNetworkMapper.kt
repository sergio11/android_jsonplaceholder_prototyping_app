package sanchez.sanchez.sergio.postsapp.persistence.network.mapper

import sanchez.sanchez.sergio.postsapp.domain.models.Comment
import sanchez.sanchez.sergio.postsapp.persistence.network.models.CommentDTO

/**
 * Comment Network Mapper
 */
class CommentNetworkMapper {

    /**
     * dto to Model
     * @param commentDTO
     */
    fun dtoToModel(commentDTO: CommentDTO): Comment =
        Comment(
            id = commentDTO.id,
            name = commentDTO.name,
            email = commentDTO.email,
            body = commentDTO.body
        )

    /**
     * DTO to Model
     * @param commentDtoList
     */
    fun dtoToModel(commentDtoList: List<CommentDTO>): List<Comment> =
        commentDtoList.map {
            dtoToModel(it)
        }.toList()

}