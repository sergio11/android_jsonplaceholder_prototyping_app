package sanchez.sanchez.sergio.postsapp.di.modules.core

import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import sanchez.sanchez.sergio.postsapp.di.scopes.PerActivity

/**
 * A module to wrap all the dependencies of an activity
 */
@Module
class ActivityModule constructor(private val activity: AppCompatActivity) {

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    fun provideActivity(): AppCompatActivity {
        return this.activity
    }


}