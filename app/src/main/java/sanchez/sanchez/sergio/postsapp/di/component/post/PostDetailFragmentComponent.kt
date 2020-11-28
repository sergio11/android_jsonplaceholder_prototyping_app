package sanchez.sanchez.sergio.postsapp.di.component.post

import dagger.Subcomponent
import sanchez.sanchez.sergio.postsapp.di.component.core.FragmentComponent
import sanchez.sanchez.sergio.postsapp.di.modules.posts.detail.PostDetailDomainModule
import sanchez.sanchez.sergio.postsapp.di.modules.posts.detail.PostDetailUIModule
import sanchez.sanchez.sergio.postsapp.di.scopes.PerFragment
import sanchez.sanchez.sergio.postsapp.ui.features.posts.detail.PostDetailFragment

/**
 * Post Detail
 */
@PerFragment
@Subcomponent(modules = [ PostDetailUIModule::class, PostDetailDomainModule::class ])
interface PostDetailFragmentComponent: FragmentComponent {

    /**
     * Inject into Post Detail Fragment
     */
    fun inject(postDetailFragment: PostDetailFragment)
}