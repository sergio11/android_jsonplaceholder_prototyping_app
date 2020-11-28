package sanchez.sanchez.sergio.postsapp.di.modules.core

import android.content.Context
import dagger.Module
import dagger.Provides
import sanchez.sanchez.sergio.postsapp.AndroidPostsApp
import sanchez.sanchez.sergio.postsapp.di.scopes.PerApplication

/**
 * Application Module
 */
@Module
class ApplicationModule constructor(private val application: AndroidPostsApp) {

    /**
     * Provide Application Context
     * @return
     */
    @Provides
    @PerApplication
    fun provideApplicationContext(): Context = application
}