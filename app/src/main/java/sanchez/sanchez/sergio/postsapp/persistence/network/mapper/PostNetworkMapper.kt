package sanchez.sanchez.sergio.postsapp.persistence.network.mapper

import sanchez.sanchez.sergio.postsapp.domain.models.Post
import sanchez.sanchez.sergio.postsapp.domain.models.PostDetail
import sanchez.sanchez.sergio.postsapp.persistence.network.models.CommentDTO
import sanchez.sanchez.sergio.postsapp.persistence.network.models.PostDTO
import sanchez.sanchez.sergio.postsapp.persistence.network.models.UserDTO

/**
 * Post Network Mapper
 */
class PostNetworkMapper (
    private val commentNetworkMapper: CommentNetworkMapper,
    private val userNetworkMapper: UserNetworkMapper
) {

    /**
     * DTO To Model
     * @param postDTO
     */
    fun dtoToModel(postDTO: PostDTO): Post =
        Post(
            id = postDTO.id,
            title = postDTO.title,
            body = postDTO.body
        )

    /**
     * Dto To Model
     * @param postDTO
     * @param commentDtoList
     * @param author
     */
    fun dtoToModel(postDTO: PostDTO, commentDtoList: List<CommentDTO>, author: UserDTO) =
        PostDetail(
            id = postDTO.id,
            title = postDTO.title,
            body = postDTO.body,
            author = userNetworkMapper.dtoToModel(author),
            commentList = commentNetworkMapper.dtoToModel(commentDtoList)
        )

    /**
     * DTO to Model
     * @param postDtoList
     */
    fun dtoToModel(postDtoList: List<PostDTO>) =
        postDtoList.map {
            dtoToModel(it)
        }.toList()
}