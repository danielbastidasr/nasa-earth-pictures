package daniel.bastidas.earthnasa

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import daniel.bastidas.domain.getlistusecase.GetNaturalListUseCase
import daniel.bastidas.earthnasa.common.aux.toPresentation
import daniel.bastidas.earthnasa.common.navigation.NavigationOptions
import daniel.bastidas.earthnasa.feature.naturaldetail.model.Coordinates
import daniel.bastidas.earthnasa.feature.naturaldetail.model.NaturalEarth
import daniel.bastidas.earthnasa.feature.naturallist.NaturalListViewModel
import daniel.bastidas.earthnasa.util.CoroutineRule
import daniel.bastidas.earthnasa.util.MockClasses
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NaturalListViewModelTest {

    private lateinit var cut: NaturalListViewModel
    private lateinit var uc: GetNaturalListUseCase

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutineRule()

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        // Default success
        uc = GetNaturalListUseCase(MockClasses.NaturalListSuccess())
        cut = NaturalListViewModel(uc)
    }

    @Test
    fun `verify state when GetListSuccess return List`() {
        // When
        cut.sendAction(NaturalListViewModel.UserAction.GetList)


        //Then
        runBlocking {
            cut.stateLiveData.value shouldEqual
                    NaturalListViewModel.ViewState(
                        list = MockClasses.NaturalListSuccess().getNaturalList().map { it.toPresentation() }
                    )
        }
    }

    @Test
    fun `verify state when GetListError return Error`() {
        // Given
        uc = GetNaturalListUseCase(MockClasses.NaturalListError())
        cut = NaturalListViewModel(uc)

        // When
        cut.sendAction(NaturalListViewModel.UserAction.GetList)

        //Then
        runBlocking {
            cut.stateLiveData.value shouldEqual
                    NaturalListViewModel.ViewState(
                        error = MockClasses.ERROR_MESSAGE
                    )
        }
    }

    @Test
    fun `verify state when SelectedItem then Navigation Triggered`() {
        // When
        val selected = NaturalEarth("id","date","caption","url", Coordinates(1.00,1.00))
        cut.sendAction(
            NaturalListViewModel.UserAction.ClickDetail(selected)
        )

        // Then
        val navigation = cut.navigation.value as NavigationOptions.NavigateToDetail
        navigation.naturalItem shouldEqual selected
    }
}