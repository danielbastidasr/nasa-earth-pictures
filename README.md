# Earth Natural Pictures

<img src="screenshots/earthnasa.gif" width="336" align="right" hspace="20">

The EPIC API provides information on the daily imagery collected by DSCOVR's Earth Polychromatic Imaging Camera (EPIC) instrument. Uniquely positioned at the Earth-Sun Lagrange point, EPIC provides full disc imagery of the Earth and captures unique perspectives of certain astronomical events such as lunar transits using a 2048x2048 pixel CCD (Charge Coupled Device) detector coupled to a 30-cm aperture Cassegrain telescope.


# 🔌 Data Source
In this case the data provider will be the *Nasa API*.

### The problem:
We will need to connect to Network to refresh the data, in case we don't have connection. It should keep the data persisted in the Database.

### Solution: 

We need to get the data from Network:

In case we succeed: We should refresh the data in the Database

```kotlin
val response = jsonPlaceHolderService.getNaturalList()
if (response.isSuccessful) {
    response.body()?.let {naturalList ->
        naturalDao.insert(
            naturalList.map {
                NaturalModelDB(
                    id = it.id,
                    date = it.date,
                    caption = it.caption,
                    image = it.image,
                    lat = it.coordinates.lat,
                    lon = it.coordinates.lon
                )
            }
        )
    }
} 
```

On the other side, We will make sure we have access to the new data using *Flow* from coroutines.
Every time the data is updated, it will emite new value on the Flow object. Very similar to the Flowable on *RxJava*.

This will help us to keep clean, and we don't need to return LiveData from the *data* layer, which should be just used on the *presentation* layer.

```kotlin
interface NaturalDao {

    @Query("SELECT * from natural_table ORDER BY date ")
    fun getNaturalPictures(): Flow<List<NaturalModelDB>>

    /*
     For this example We assume that modifications will be done in the backend
     Other Option if We know that new data just will be added, then We can use
     the strategy: OnConflictStrategy.IGNORE
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(naturalEarth: List<NaturalModelDB>)
}
```

# Domain
In the domain layer I just created the repositires, here there will be more transformations if needed later on adding different data sources.

We need to make sure, we provide the Use Cases that will be needed and with their corresponding datasources.

We will have: 

### GetNaturalListUseCase
This usecase will be in charge of get data from Network and if he finds data, refresh the database.

### ListenRefreshDataUseCase
This usecase will be in charge of listening to new data, every time the data is refreshed on the database, we will receive a new emited value.


# Presentation


## The View:
used the Main Activity to hold the view.
And then Created All the fragments that will help us to navigate to a different sections of the app.


### Natural List

The user Actions will be

```kotlin
sealed class UserAction: UserActions {
    object GetList:UserAction()
    class ClickDetail(val naturalItem: NaturalEarth):UserAction()
}
```

The internal acctions are responses/internal states, that the app needs to go through, in order to satisfy user's Actions.

### Natural Detail
It will display more detailed information of the picture selected.

## The Navigation:

In order to create the Fragment Navigation we used: *Navigation Component*
Very simple way to navigate among fragments.

In this case we just need the following Options:
```kotlin
sealed class NavigationOptions {
    object Back : NavigationOptions()
    class NavigateToDetail(val naturalItem: NaturalEarth):NavigationOptions()
}
```
The only argument passed will be *naturalItem* which will be the selected Object from the List.

# Future Improvements:

- UI/UX design
- Improve test cases
- UI tests
- Add Map to visualize the coordinates and have better refrence
- Add Coordinates of the moon and sun to make sense the time 
