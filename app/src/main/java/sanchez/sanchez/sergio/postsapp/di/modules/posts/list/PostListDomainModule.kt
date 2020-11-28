package sanchez.sanchez.sergio.postsapp.di.modules.posts.list

import dagger.Module
import dagger.Provides
import sanchez.sanchez.sergio.postsapp.di.modules.posts.core.PostRepositoryModule
import sanchez.sanchez.sergio.postsapp.di.scopes.PerFragment
import sanchez.sanchez.sergio.postsapp.domain.interact.FindAllPostsInteract
import sanchez.sanchez.sergio.postsapp.persistence.api.posts.IPostsRepository

@Module(includes = [ PostRepositoryModule::class ])
class PostListDomainModule {

    /**
     * Provide Find All Posts Interact
     * @param postRepository
     */
    @Provides
    @PerFragment
    fun provideFindAllPostsInteract(
        postRepository: IPostsRepository
    ): FindAllPostsInteract = FindAllPostsInteract(postRepository)
}