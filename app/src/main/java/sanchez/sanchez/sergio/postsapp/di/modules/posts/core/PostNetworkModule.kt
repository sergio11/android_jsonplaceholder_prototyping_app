package sanchez.sanchez.sergio.postsapp.di.modules.posts.core

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import sanchez.sanchez.sergio.postsapp.di.scopes.PerFragment
import sanchez.sanchez.sergio.postsapp.persistence.network.mapper.CommentNetworkMapper
import sanchez.sanchez.sergio.postsapp.persistence.network.mapper.PostNetworkMapper
import sanchez.sanchez.sergio.postsapp.persistence.network.mapper.UserNetworkMapper
import sanchez.sanchez.sergio.postsapp.persistence.network.repository.posts.IPostsNetworkRepository
import sanchez.sanchez.sergio.postsapp.persistence.network.repository.posts.PostsNetworkRepositoryImpl
import sanchez.sanchez.sergio.postsapp.persistence.network.service.ICommentsService
import sanchez.sanchez.sergio.postsapp.persistence.network.service.IPostsService
import sanchez.sanchez.sergio.postsapp.persistence.network.service.IUsersService

@Module
class PostNetworkModule {

    /**
     * Provide Posts Service
     * @param retrofit
     */
    @Provides
    @PerFragment
    fun providePostsService(retrofit: Retrofit): IPostsService =
        retrofit.create(IPostsService::class.java)

    /**
     * Provide Post Comments Service
     * @param retrofit
     */
    @Provides
    @PerFragment
    fun providePostCommentsService(retrofit: Retrofit): ICommentsService =
        retrofit.create(ICommentsService::class.java)

    /**
     * Provide Post users service
     * @param retrofit
     */
    @Provides
    @PerFragment
    fun providePostUsersService(retrofit: Retrofit): IUsersService =
        retrofit.create(IUsersService::class.java)

    /**
     * Provide Comment Network Mapper
     */
    @Provides
    @PerFragment
    fun provideCommentNetworkMapper() =
        CommentNetworkMapper()

    /**
     * Provide User Network Mapper
     */
    @Provides
    @PerFragment
    fun provideUserNetworkMapper() =
        UserNetworkMapper()

    /**
     * Provide Post Network Mapper
     * @param commentNetworkMapper
     * @param userNetworkMapper
     */
    @Provides
    @PerFragment
    fun providePostNetworkMapper(
        commentNetworkMapper: CommentNetworkMapper,
        userNetworkMapper: UserNetworkMapper
    ): PostNetworkMapper =
        PostNetworkMapper(commentNetworkMapper, userNetworkMapper)

    /**
     * Provide Post Network Repository
     * @param postService
     * @param commentsService
     * @param userService
     * @param postNetworkMapper
     */
    @Provides
    @PerFragment
    fun providePostNetworkRepository(
        postService: IPostsService,
        commentsService: ICommentsService,
        userService: IUsersService,
        postNetworkMapper: PostNetworkMapper
    ): IPostsNetworkRepository =
        PostsNetworkRepositoryImpl(
            postService, commentsService,
            userService, postNetworkMapper
        )

}