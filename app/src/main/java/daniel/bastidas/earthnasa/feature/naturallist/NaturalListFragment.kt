package daniel.bastidas.earthnasa.feature.naturallist


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_natural_list.*
import kotlinx.android.synthetic.main.fragment_natural_list.view.*

import daniel.bastidas.earthnasa.common.ui.BaseFragment
import daniel.bastidas.earthnasa.feature.naturallist.NaturalListViewModel.UserAction
import daniel.bastidas.earthnasa.feature.naturallist.NaturalListViewModel.ViewState
import daniel.bastidas.earthnasa.common.aux.observe
import daniel.bastidas.earthnasa.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class NaturalListFragment : BaseFragment() {

    override val viewModel: NaturalListViewModel by viewModel()

    private val naturalListAdapter = NaturalEarthListAdapter(clickCallback = {
        viewModel.sendAction(UserAction.ClickDetail(it))
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View  =
        inflater.inflate(R.layout.fragment_natural_list, container, false).apply {
            initialiseView(this)
            observe(viewModel.stateLiveData,::onStateChange)
        }

    private fun initialiseView(viewFragment: View){
        viewFragment.naturalList.apply {
            adapter = naturalListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            layoutAnimation
        }
        viewFragment.swipe_refresh.setOnRefreshListener { viewModel.sendAction(UserAction.GetList) }
    }

    private fun onStateChange(state: ViewState) {
        state.list?.let { list -> naturalListAdapter.updateData(list) }
        swipe_refresh.isRefreshing = state.loading
        state.error?.let {
            errorMessage.text = it
            errorMessage.visibility = View.VISIBLE
        }
        if(state.error == null){
            errorMessage.visibility = View.GONE
        }
    }
}
