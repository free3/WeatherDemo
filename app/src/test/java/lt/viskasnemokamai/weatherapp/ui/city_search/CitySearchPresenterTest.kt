package lt.viskasnemokamai.weatherapp.ui.city_search


import lt.viskasnemokamai.weatherapp.dao.AppDatabase
import lt.viskasnemokamai.weatherapp.dao.CityWeatherHistoryDao
import lt.viskasnemokamai.weatherapp.domain.CitySearchHistoryItem
import lt.viskasnemokamai.weatherapp.domain.CityWeather
import lt.viskasnemokamai.weatherapp.utils.DateTime
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.Date

/**
 * Created by Greta Radisauskaite on 11/10/2020.
 */
class CitySearchPresenterTest {

    lateinit var instance: CitySearchPresenter

    @Mock
    lateinit var citySearchView: CitySearchView
    @Mock
    lateinit var appDatabase: AppDatabase
    @Mock
    lateinit var cityWeatherHistoryDao: CityWeatherHistoryDao
    @Mock
    lateinit var citySearchInteractor: CitySearchInteractor
    @Mock
    lateinit var dateTime: DateTime

    var date = Date(123)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(appDatabase.cityWeatherHistoryDao()).thenReturn(cityWeatherHistoryDao)
        `when`(dateTime.getDateTime()).thenReturn(date)

        instance = CitySearchPresenter(citySearchView, appDatabase, citySearchInteractor)
        instance.dateTime = dateTime
    }

    @Test
    fun searchCity_should_abort_if_city_is_null() {

        // Arrange

        // Act
        var success = instance.searchCity(null)

        // Assert
        assertFalse(success)
        verifyNoMoreInteractions(citySearchInteractor)
    }

    @Test
    fun searchCity_should_abort_if_city_empty() {

        // Arrange

        // Act
        var success = instance.searchCity("")

        // Assert
        assertFalse(success)
        verifyNoMoreInteractions(citySearchInteractor)
    }

    @Test
    fun searchCity_should_do_lookup_when_city_provided() {

        // Arrange

        // Act
        var success = instance.searchCity("vilnius")

        // Assert
        assertTrue(success)
        verify(citySearchInteractor).lookup(instance, true, "vilnius")
    }

    @Test
    fun searchHistory_should_abort_when_history_empty() {

        // Arrange
        var history = ArrayList<CitySearchHistoryItem>()
        `when`(cityWeatherHistoryDao.getHistory()).thenReturn(history)

        // Act
        var success = instance.searchHistory()

        // Assert
        assertFalse(success)
        verifyNoMoreInteractions(citySearchInteractor)
    }

    @Test
    fun searchHistory_should_do_lookup_when_history_exists() {

        // Arrange
        var history = ArrayList<CitySearchHistoryItem>()
        history.add(CitySearchHistoryItem("Palanga", 123))
        `when`(cityWeatherHistoryDao.getHistory()).thenReturn(history)

        // Act
        var success = instance.searchHistory()

        // Assert
        assertTrue(success)
        verify(citySearchInteractor).lookup(instance, false, "Palanga")
    }

    @Test
    fun onDestroy_should_release_resources() {

        // Arrange

        // Act
        instance.onDestroy()

        // Assert
        assertNull(instance.citySearchView)
        assertNull(instance.appDatabase)

    }

    @Test
    fun onStartSearch_should_show_progress() {

        // Arrange

        // Act
        instance.onStartSearch()

        // Assert
        verify(citySearchView).showProgress()

    }

    @Test
    fun onNotFound_should_hide_progress_and_set_message() {

        // Arrange

        // Act
        instance.onNotFound()

        // Assert
        verify(citySearchView).hideProgress()
        verify(citySearchView).setNotFound()

    }

    @Test
    fun onFound_should_hide_progress_and_navigate_to_results_when_not_preserve_history() {

        // Arrange
        var citiesWeather = ArrayList<CityWeather>()
        citiesWeather.add(CityWeather())

        // Act
        instance.onFound(citiesWeather, false)

        // Assert
        verify(citySearchView).hideProgress()
        verify(citySearchView).navigateToResults(citiesWeather)
        verifyNoMoreInteractions(cityWeatherHistoryDao)
    }

    @Test
    fun onFound_should_hide_progress_and_navigate_to_results_and_save_to_history_when_preserve_history() {

        // Arrange
        var citiesWeather = ArrayList<CityWeather>()
        citiesWeather.add(CityWeather(name = "first"))
        citiesWeather.add(CityWeather(name = "second"))

        // Act
        instance.onFound(citiesWeather, true)

        // Assert
        verify(citySearchView).hideProgress()
        verify(citySearchView).navigateToResults(citiesWeather)
        verify(cityWeatherHistoryDao).addHistoryPreserveLimit(
            CitySearchHistoryItem("first", 123),
            5
        )

    }

    @Test
    fun onError_should_hide_progress_and_set_message() {

        // Arrange

        // Act
        instance.onError(null)

        // Assert
        verify(citySearchView).hideProgress()
        verify(citySearchView).setError()

    }
}