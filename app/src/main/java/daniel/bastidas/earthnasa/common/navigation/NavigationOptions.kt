package daniel.bastidas.earthnasa.common.navigation

import daniel.bastidas.earthnasa.feature.naturaldetail.model.NaturalEarth


sealed class NavigationOptions {
    object Back : NavigationOptions()
    class NavigateToDetail(val naturalItem: NaturalEarth):NavigationOptions()
}