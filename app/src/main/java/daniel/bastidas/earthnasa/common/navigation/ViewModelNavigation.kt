package daniel.bastidas.earthnasa.common.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

abstract class ViewModelNavigation: ViewModel() {
    protected val mutableNavigation = SingleLiveEvent<NavigationOptions>()
    val navigation: LiveData<NavigationOptions> = mutableNavigation
}