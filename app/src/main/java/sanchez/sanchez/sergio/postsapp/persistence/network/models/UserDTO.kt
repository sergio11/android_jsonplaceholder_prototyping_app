package sanchez.sanchez.sergio.postsapp.persistence.network.models

import com.google.gson.annotations.SerializedName


/**
 * User DTO definition
 */

data class UserDTO (
    @SerializedName(value = "id")
    val id: Long,
    @SerializedName(value = "name")
    val name: String,
    @SerializedName(value = "username")
    val username: String,
    @SerializedName(value = "email")
    val email: String,
    @SerializedName(value = "address")
    val addressDTO: AddressDTO,
    @SerializedName(value = "phone")
    val phone: String,
    @SerializedName(value = "website")
    val website: String,
    @SerializedName(value = "company")
    val companyDTO: CompanyDTO
)

data class AddressDTO (
    @SerializedName(value = "street")
    val street: String,
    @SerializedName(value = "suite")
    val suite: String,
    @SerializedName(value = "city")
    val city: String,
    @SerializedName(value = "zipcode")
    val zipcode: String,
    @SerializedName(value = "geo")
    val locationDTO: LocationDTO
)

data class LocationDTO (
    @SerializedName(value = "lat")
    val lat: Double,
    @SerializedName(value = "lng")
    val lng: Double
)

data class CompanyDTO (
    @SerializedName(value = "name")
    val name: String
)
