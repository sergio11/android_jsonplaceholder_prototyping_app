package sanchez.sanchez.sergio.postsapp.di.modules.posts.list

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import sanchez.sanchez.sergio.postsapp.di.modules.core.ViewModelModule
import sanchez.sanchez.sergio.postsapp.di.modules.core.viewmodel.ViewModelKey
import sanchez.sanchez.sergio.postsapp.di.scopes.PerFragment
import sanchez.sanchez.sergio.postsapp.ui.features.posts.list.PostListViewModel

@Module(includes = [ ViewModelModule::class ])
abstract class PostListUIModule {

    @PerFragment
    @Binds
    @IntoMap
    @ViewModelKey(PostListViewModel::class)
    abstract fun bindsPostListViewModel(postListViewModel: PostListViewModel): ViewModel
}