package daniel.bastidas.earthnasa.common.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import daniel.bastidas.earthnasa.feature.naturallist.NaturalListFragmentDirections
import daniel.bastidas.earthnasa.common.navigation.ViewModelNavigation
import daniel.bastidas.earthnasa.common.navigation.NavigationOptions


abstract class BaseFragment: Fragment() {

    internal abstract val viewModel: ViewModelNavigation

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.navigation.observe(viewLifecycleOwner, Observer { navigate(it) })
    }

    private fun navigate(it: NavigationOptions) {
        when (it) {
            is NavigationOptions.Back -> findNavController().popBackStack()

            is NavigationOptions.NavigateToDetail -> findNavController()
                .navigate(
                    NaturalListFragmentDirections.actionNaturalListFragmentToNaturalDetailFragment(it.naturalItem)
                )
        }
    }
}