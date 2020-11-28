package sanchez.sanchez.sergio.postsapp.di.modules.posts.detail

import dagger.Module
import dagger.Provides
import sanchez.sanchez.sergio.postsapp.di.modules.posts.core.PostRepositoryModule
import sanchez.sanchez.sergio.postsapp.di.scopes.PerFragment
import sanchez.sanchez.sergio.postsapp.domain.interact.FindPostDetailByIdInteract
import sanchez.sanchez.sergio.postsapp.persistence.api.posts.IPostsRepository

/**
 * Post Detail Domain Module
 */
@Module(includes = [ PostRepositoryModule::class ])
class PostDetailDomainModule {

    /**
     * Provide Find Post Detail by id interact
     * @param postsRepository
     */
    @Provides
    @PerFragment
    fun provideFindPostDetailByIdInteract(postsRepository: IPostsRepository): FindPostDetailByIdInteract = FindPostDetailByIdInteract(postsRepository)

}