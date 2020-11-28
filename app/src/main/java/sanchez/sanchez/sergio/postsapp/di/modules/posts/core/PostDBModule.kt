package sanchez.sanchez.sergio.postsapp.di.modules.posts.core

import dagger.Module
import dagger.Provides
import sanchez.sanchez.sergio.postsapp.di.scopes.PerFragment
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.post.IPostDBRepository
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.post.PostDBRepositoryImpl
import sanchez.sanchez.sergio.postsapp.persistence.db.room.AppRoomDatabase
import sanchez.sanchez.sergio.postsapp.persistence.db.room.dao.post.IPostDAO
import sanchez.sanchez.sergio.postsapp.persistence.db.room.mapper.PostDBMapper

/**
 * Post DB Module
 */
@Module
class PostDBModule {

    /**
     * Provide Post DAO
     * @param database
     */
    @Provides
    @PerFragment
    fun providePostDao(database: AppRoomDatabase): IPostDAO =
        database.postDAO()

    /**
     * Provide Post DB Mapper
     */
    @Provides
    @PerFragment
    fun providePostDBMapper(): PostDBMapper =
        PostDBMapper()

    /**
     * Provide Post DB Repository
     * @param postDAO
     * @param postDBMapper
     */
    @Provides
    @PerFragment
    fun providePostDBRepository(
        postDAO: IPostDAO,
        postDBMapper: PostDBMapper
    ): IPostDBRepository = PostDBRepositoryImpl(postDAO, postDBMapper)
}