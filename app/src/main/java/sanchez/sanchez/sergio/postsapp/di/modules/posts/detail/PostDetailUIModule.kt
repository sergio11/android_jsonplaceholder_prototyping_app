package sanchez.sanchez.sergio.postsapp.di.modules.posts.detail

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import sanchez.sanchez.sergio.postsapp.di.modules.core.ViewModelModule
import sanchez.sanchez.sergio.postsapp.di.modules.core.viewmodel.ViewModelKey
import sanchez.sanchez.sergio.postsapp.di.scopes.PerFragment
import sanchez.sanchez.sergio.postsapp.ui.features.posts.detail.PostDetailViewModel

@Module(includes = [ ViewModelModule::class ])
abstract class PostDetailUIModule {

    @PerFragment
    @Binds
    @IntoMap
    @ViewModelKey(PostDetailViewModel::class)
    abstract fun bindsPostDetailViewModel(postDetailViewModel: PostDetailViewModel): ViewModel
}