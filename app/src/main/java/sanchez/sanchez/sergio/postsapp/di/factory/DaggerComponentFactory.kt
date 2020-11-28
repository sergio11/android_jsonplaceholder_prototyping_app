package sanchez.sanchez.sergio.postsapp.di.factory

import androidx.appcompat.app.AppCompatActivity
import sanchez.sanchez.sergio.postsapp.AndroidPostsApp
import sanchez.sanchez.sergio.postsapp.di.component.MainActivityComponent
import sanchez.sanchez.sergio.postsapp.di.component.ApplicationComponent
import sanchez.sanchez.sergio.postsapp.di.component.DaggerApplicationComponent
import sanchez.sanchez.sergio.postsapp.di.component.post.PostDetailFragmentComponent
import sanchez.sanchez.sergio.postsapp.di.component.post.PostListFragmentComponent
import sanchez.sanchez.sergio.postsapp.di.modules.core.ActivityModule
import sanchez.sanchez.sergio.postsapp.di.modules.core.ApplicationModule

/**
 * Dagger Component Factory
 */
object DaggerComponentFactory {

    private var appComponent: ApplicationComponent? = null
    private var mainActivityComponent: MainActivityComponent? = null

    fun getAppComponent(app: AndroidPostsApp): ApplicationComponent =
        appComponent ?: DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(app))
            .build().also {
                appComponent = it
            }

    fun getMainActivityComponent(activity: AppCompatActivity): MainActivityComponent =
        mainActivityComponent ?: getAppComponent(activity.application as AndroidPostsApp)
            .mainActivityComponent(ActivityModule(activity)).also {
                mainActivityComponent = it
            }

    fun getPostListFragmentComponent(activity: AppCompatActivity): PostListFragmentComponent =
        getMainActivityComponent(activity).postListFragmentComponent()

    fun getPostDetailFragmentComponent(activity: AppCompatActivity): PostDetailFragmentComponent =
        getMainActivityComponent(activity).postDetailFragmentComponent()

}