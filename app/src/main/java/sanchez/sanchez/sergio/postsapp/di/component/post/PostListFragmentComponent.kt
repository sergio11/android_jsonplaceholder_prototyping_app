package sanchez.sanchez.sergio.postsapp.di.component.post

import dagger.Subcomponent
import sanchez.sanchez.sergio.postsapp.di.component.core.FragmentComponent
import sanchez.sanchez.sergio.postsapp.di.modules.posts.list.PostListDomainModule
import sanchez.sanchez.sergio.postsapp.di.modules.posts.list.PostListUIModule
import sanchez.sanchez.sergio.postsapp.di.scopes.PerFragment
import sanchez.sanchez.sergio.postsapp.ui.features.posts.list.PostListFragment

/**
 * Post List
 */
@PerFragment
@Subcomponent(modules = [ PostListUIModule::class, PostListDomainModule::class ])
interface PostListFragmentComponent: FragmentComponent {

    /**
     * Inject into Character Fragment
     */
    fun inject(postListFragment: PostListFragment)
}