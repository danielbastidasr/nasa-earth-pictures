package daniel.bastidas.earthnasa.common.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import daniel.bastidas.earthnasa.common.navigation.ViewModelNavigation
import kotlin.properties.Delegates

abstract class BaseViewModel<ViewState : BaseViewState, ViewAction : BaseAction>(initialState: ViewState) :
    ViewModelNavigation() {

    private val stateMutableLiveData = MutableLiveData<ViewState>()
    val stateLiveData: LiveData<ViewState> = stateMutableLiveData

    protected fun sendViewAction(viewAction: ViewAction) {
        state = onReduceState(viewAction)
    }

    private var state by Delegates.observable(initialState) { _, old, new ->
        if (new != old) {
            stateMutableLiveData.value = new
        }
    }

    protected abstract fun onReduceState(viewAction: ViewAction): ViewState
}
interface BaseAction
interface BaseViewState
interface UserActions
