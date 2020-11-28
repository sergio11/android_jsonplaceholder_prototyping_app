package sanchez.sanchez.sergio.postsapp.persistence.db.room.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import sanchez.sanchez.sergio.postsapp.persistence.db.room.entity.CommentEntity
import sanchez.sanchez.sergio.postsapp.persistence.db.room.entity.UserEntity

/**
 * All Converters for save entities
 */
class Converters {

    @TypeConverter
    fun userEntityToJson(value: UserEntity): String = toJson(value)

    @TypeConverter
    fun jsonToUserEntity(value: String): UserEntity? = fromJson<UserEntity>(value)

    @TypeConverter
    fun commentEntityListToJson(value: List<CommentEntity>): String = toJson(value)

    @TypeConverter
    fun jsonToCommentEntityList(value: String): List<CommentEntity>? = fromJson<ArrayList<CommentEntity>>(value)

    /**
     * Private Methods
     */
    private inline fun <reified T> toJson(model: T): String =
        Gson().toJson(model)

    private inline fun <reified T> fromJson(value: String): T? =
        Gson().fromJson(value, object : TypeToken<T>() {}.type)
}