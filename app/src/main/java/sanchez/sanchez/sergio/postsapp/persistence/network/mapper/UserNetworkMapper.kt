package sanchez.sanchez.sergio.postsapp.persistence.network.mapper

import sanchez.sanchez.sergio.postsapp.domain.models.User
import sanchez.sanchez.sergio.postsapp.persistence.network.models.UserDTO
import java.lang.StringBuilder

/**
 * User Network Mapper
 */
class UserNetworkMapper {

    /**
     * dto to Model
     * @param userDTO
     */
    fun dtoToModel(userDTO: UserDTO): User =
        User(
            id = userDTO.id,
            name = userDTO.name,
            username = userDTO.username,
            email = userDTO.email,
            phone = userDTO.phone,
            website = userDTO.website,
            address = StringBuilder().append(userDTO.addressDTO.street)
                .append(" / ")
                .append(userDTO.addressDTO.city)
                .append(" / ")
                .append(userDTO.addressDTO.zipcode)
                .toString(),
            latitude = userDTO.addressDTO.locationDTO.lat,
            longitude = userDTO.addressDTO.locationDTO.lng,
            company = userDTO.companyDTO.name
        )
}