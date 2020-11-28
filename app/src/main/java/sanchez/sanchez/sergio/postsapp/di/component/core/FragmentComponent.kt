package sanchez.sanchez.sergio.postsapp.di.component.core

import dagger.Component
import sanchez.sanchez.sergio.postsapp.di.modules.core.ViewModelModule
import sanchez.sanchez.sergio.postsapp.di.scopes.PerFragment

@PerFragment
@Component(
    dependencies = [ ActivityComponent::class ],
    modules = [ViewModelModule::class])
interface FragmentComponent {

}