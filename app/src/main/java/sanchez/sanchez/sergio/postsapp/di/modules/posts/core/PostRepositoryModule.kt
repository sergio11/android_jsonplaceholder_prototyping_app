package sanchez.sanchez.sergio.postsapp.di.modules.posts.core

import dagger.Module
import dagger.Provides
import sanchez.sanchez.sergio.postsapp.di.scopes.PerFragment
import sanchez.sanchez.sergio.postsapp.persistence.api.posts.IPostsRepository
import sanchez.sanchez.sergio.postsapp.persistence.api.posts.PostsRepositoryImpl
import sanchez.sanchez.sergio.postsapp.persistence.db.repository.post.IPostDBRepository
import sanchez.sanchez.sergio.postsapp.persistence.network.repository.posts.IPostsNetworkRepository

/**
 * Post Repository Module
 */
@Module(includes = [ PostDBModule::class, PostNetworkModule::class ])
class PostRepositoryModule {

    /**
     * Provide Post Repository
     * @param postNetworkRepository
     * @param postDBRepository
     */
    @Provides
    @PerFragment
    fun providePostRepository(
        postNetworkRepository: IPostsNetworkRepository,
        postDBRepository: IPostDBRepository
    ): IPostsRepository =
        PostsRepositoryImpl(
            postNetworkRepository,
            postDBRepository
        )

}