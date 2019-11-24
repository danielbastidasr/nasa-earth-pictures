package daniel.bastidas.earthnasa.feature.naturaldetail

import daniel.bastidas.earthnasa.common.ui.BaseAction
import daniel.bastidas.earthnasa.common.ui.BaseViewModel
import daniel.bastidas.earthnasa.common.ui.BaseViewState
import daniel.bastidas.earthnasa.feature.naturaldetail.model.NaturalEarth


internal class NaturalDetailViewModel(
    private val naturalItem: NaturalEarth
):
    BaseViewModel<NaturalDetailViewModel.ViewState, NaturalDetailViewModel.InternalAction>(ViewState()) {

    init {
        sendViewAction(InternalAction.Initialise)
    }

    override fun onReduceState(viewAction: InternalAction): ViewState {
        return ViewState(naturalItem)
    }

    internal data
    class ViewState(
        val naturalItem: NaturalEarth? = null
    ) : BaseViewState

    sealed class InternalAction : BaseAction {
        object Initialise:InternalAction()
    }
}
