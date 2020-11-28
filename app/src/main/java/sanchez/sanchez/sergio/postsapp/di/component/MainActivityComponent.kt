package sanchez.sanchez.sergio.postsapp.di.component

import dagger.Subcomponent
import sanchez.sanchez.sergio.postsapp.di.component.post.PostDetailFragmentComponent
import sanchez.sanchez.sergio.postsapp.di.component.post.PostListFragmentComponent
import sanchez.sanchez.sergio.postsapp.di.component.core.ActivityComponent
import sanchez.sanchez.sergio.postsapp.di.modules.core.ActivityModule
import sanchez.sanchez.sergio.postsapp.di.scopes.PerActivity
import sanchez.sanchez.sergio.postsapp.ui.MainActivity


@PerActivity
@Subcomponent(modules = [
    ActivityModule::class
])
interface MainActivityComponent: ActivityComponent {

    fun inject(activity: MainActivity)

    fun postListFragmentComponent(): PostListFragmentComponent

    fun postDetailFragmentComponent(): PostDetailFragmentComponent

}