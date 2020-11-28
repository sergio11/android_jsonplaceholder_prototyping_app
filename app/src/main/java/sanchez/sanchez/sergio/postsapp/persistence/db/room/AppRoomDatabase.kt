package sanchez.sanchez.sergio.postsapp.persistence.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import sanchez.sanchez.sergio.postsapp.persistence.db.room.converters.Converters
import sanchez.sanchez.sergio.postsapp.persistence.db.room.dao.post.PostDAOImpl
import sanchez.sanchez.sergio.postsapp.persistence.db.room.entity.PostEntity

@Database(entities = [PostEntity::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class)
abstract class AppRoomDatabase : RoomDatabase() {

    /**
     * DAOs declarations
     */
    abstract fun postDAO(): PostDAOImpl

    companion object {

        const val DATABASE_NAME = "APP_DATABASE"
    }
}