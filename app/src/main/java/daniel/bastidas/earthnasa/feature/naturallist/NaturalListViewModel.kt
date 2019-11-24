package daniel.bastidas.earthnasa.feature.naturallist

import androidx.lifecycle.viewModelScope
import daniel.bastidas.domain.getlistusecase.GetListResult
import daniel.bastidas.domain.getlistusecase.GetNaturalListUseCase
import daniel.bastidas.domain.listenrefreshdatausecase.ListenRefreshDataUseCase
import daniel.bastidas.earthnasa.common.ui.BaseAction
import daniel.bastidas.earthnasa.common.ui.BaseViewModel
import daniel.bastidas.earthnasa.common.ui.BaseViewState
import daniel.bastidas.earthnasa.common.ui.UserActions
import daniel.bastidas.earthnasa.common.aux.toPresentation
import daniel.bastidas.earthnasa.common.navigation.NavigationOptions
import daniel.bastidas.earthnasa.feature.naturaldetail.model.NaturalEarth
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

internal class NaturalListViewModel(private val getNaturalListUseCase: GetNaturalListUseCase,
                                    private val listenRefreshDataUseCase: ListenRefreshDataUseCase) :
    BaseViewModel<NaturalListViewModel.ViewState, NaturalListViewModel.InternalActions>(ViewState()) {

    init {
        listenUpdatedList()
        getList()
    }

    override fun onReduceState(viewAction: InternalActions): ViewState =
        when(viewAction){
            is InternalActions.Loading ->{
                val prevListState = stateLiveData.value?.list
                ViewState(loading = true,list = prevListState)
            }
            is InternalActions.ListResultError -> {
                val prevListState = stateLiveData.value?.list
                ViewState(error = viewAction.errorMessage,list = prevListState)
            }

            is InternalActions.NetworkListResultSuccess->{
                val prevListState = stateLiveData.value?.list
                ViewState(list = prevListState)
            }

            is InternalActions.ShouldUpdateList ->
                ViewState(list = viewAction.list)
        }

    fun sendAction(userAction: UserActions){
        when(userAction){
            is UserAction.GetList ->
                getList()
            is UserAction.ClickDetail ->
                mutableNavigation.postValue(NavigationOptions.NavigateToDetail(userAction.naturalItem))
        }
    }

    private fun getList(){
        // Loading
        sendViewAction(InternalActions.Loading)

        // Send Request
        viewModelScope.launch {
            manageResponse (getNaturalListUseCase.invoke())
        }
    }

    private fun manageResponse(result: GetListResult){
        when(result){
            is GetListResult.GetListSuccess ->
                sendViewAction(InternalActions.NetworkListResultSuccess)

            is GetListResult.GetListError ->
                sendViewAction(InternalActions.ListResultError(
                    errorMessage = result.exception.toPresentation())
                )
        }
    }

    @UseExperimental(InternalCoroutinesApi::class)
    private fun listenUpdatedList() {
        viewModelScope.launch {
            listenRefreshDataUseCase.invoke().collect { updatedList ->
                sendViewAction(
                    InternalActions.ShouldUpdateList(
                        updatedList.map { it.toPresentation() }
                    )
                )
            }
        }
    }

    internal data
    class ViewState(
        val loading: Boolean = false,
        val list: List<NaturalEarth>? = null,
        val error: String? = null
    ) : BaseViewState

    sealed class InternalActions : BaseAction {
        internal object Loading:InternalActions()
        internal class ListResultError(val errorMessage:String) : InternalActions()
        internal object NetworkListResultSuccess: InternalActions()
        internal class ShouldUpdateList(val list: List<NaturalEarth>):InternalActions()
    }

    sealed class UserAction: UserActions {
        object GetList:UserAction()
        class ClickDetail(val naturalItem: NaturalEarth):UserAction()
    }
}


