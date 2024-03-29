package daniel.bastidas.earthnasa.util


import daniel.bastidas.domain.gateway.NaturalEarthGateway
import daniel.bastidas.domain.gateway.NaturalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class MockClasses {

    companion object {
        const val ERROR_MESSAGE = "Test error"
        val previousList = listOf(
            NaturalEntity(
                "1",
                "2019-06-27 01:09:09",
                "This is earth south",
                "epic_1b_20190627011358",
                10.222,
                9.0000
            ),
            NaturalEntity(
                "2",
                "2019-06-27 01:09:09",
                "This is earth north",
                "epic_1b_20190627011358",
                10.222,
                9.0000
            ),
            NaturalEntity(
                "3",
                "2019-06-27 01:09:09",
                "This is earth east",
                "epic_1b_20190627011358",
                10.222,
                9.0000
            ),
            NaturalEntity(
                "4",
                "2019-06-27 01:09:09",
                "This is earth west",
                "epic_1b_20190627011358",
                10.222,
                9.0000
            )
        )
    }

    internal class NaturalListSuccess: NaturalEarthGateway {
        override fun listenDataList(): Flow<List<NaturalEntity>> {
            return flowOf(previousList)
        }

        override suspend fun getNaturalList():Boolean
                = true
    }

    internal class NaturalListError: NaturalEarthGateway {
        override fun listenDataList(): Flow<List<NaturalEntity>> {
            return flowOf(previousList)
        }

        override suspend fun getNaturalList(): Boolean = throw Exception(ERROR_MESSAGE)
    }
}