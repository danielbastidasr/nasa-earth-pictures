package daniel.bastidas.earthnasa.feature.naturaldetail


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso

import daniel.bastidas.earthnasa.R
import daniel.bastidas.earthnasa.common.ui.BaseFragment
import daniel.bastidas.earthnasa.common.aux.ImageType
import daniel.bastidas.earthnasa.common.aux.observe
import daniel.bastidas.earthnasa.common.aux.getUrl
import daniel.bastidas.earthnasa.feature.naturaldetail.NaturalDetailViewModel.ViewState
import kotlinx.android.synthetic.main.fragment_natural_detail.*
import org.koin.core.parameter.parametersOf
import org.koin.androidx.viewmodel.ext.android.viewModel


class NaturalDetailFragment : BaseFragment() {

    private val args: NaturalDetailFragmentArgs by navArgs()
    override val viewModel: NaturalDetailViewModel by viewModel{ parametersOf(args) }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View
            = inflater.inflate(R.layout.fragment_natural_detail, container, false)
        .apply {
            observe(viewModel.stateLiveData,::onStateChange)
        }

    private fun onStateChange(state: ViewState) {
        state.naturalItem?.let {
            detail.text = it.caption
            coordinates.text = it.coordinates.toString()
            Picasso
                .get()
                .load(it.getUrl(ImageType.jpg))
                .error(R.drawable.ic_launcher_background)
                .into(earthPicture)
        }
    }
}
