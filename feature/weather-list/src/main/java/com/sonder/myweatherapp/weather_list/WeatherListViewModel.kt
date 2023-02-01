package com.sonder.myweatherapp.weather_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sonder.myweatherapp.common.result.Result
import com.sonder.myweatherapp.common.result.asResult
import com.sonder.myweatherapp.data.repository.LocationRepository
import com.sonder.myweatherapp.data.repository.WeatherRepository
import com.sonder.myweatherapp.model.data.CityResource
import com.sonder.myweatherapp.model.data.WeatherRequest
import com.sonder.myweatherapp.model.data.WeatherResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherListViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository
) : ViewModel() {
    private val _weatherState: MutableStateFlow<WeatherUiState> =
        MutableStateFlow(WeatherUiState.Loading)

    val weatherState: StateFlow<WeatherUiState> = _weatherState

    private val _cityListState: MutableStateFlow<CityListUiState> =
        MutableStateFlow(CityListUiState.Loading)

    val cityListState: StateFlow<CityListUiState> = _cityListState

    private val _locationState: MutableStateFlow<LocationUiState> =
        MutableStateFlow(LocationUiState.Loading)

    val locationState: StateFlow<CityListUiState> = _cityListState

    private fun getWeather(request: WeatherRequest) {
        viewModelScope.launch {
            weatherRepository.getWeather(request).asResult().collect { weatherResult ->
                when (weatherResult) {
                    is Result.Success -> {
                        _weatherState.update {
                            WeatherUiState.Success(
                                weatherResult.data.values.toList().sortedBy {
                                    !it.isCustomLocation
                                }
                            )
                        }
                    }
                    is Result.Loading -> {
                        _weatherState.value = WeatherUiState.Loading
                    }
                    is Result.Error -> {
                        _weatherState.value = WeatherUiState.Error
                    }
                }
            }
        }
    }

    fun getCityList() {
        viewModelScope.launch {
            weatherRepository.getDefaultCities().asResult().collect { cityResult ->
                when (cityResult) {
                    is Result.Success -> cityResult.data.callWeatherByCityList()
                    is Result.Loading -> _cityListState.value = CityListUiState.Loading
                    is Result.Error -> _cityListState.value = CityListUiState.Error
                }
            }
        }
    }

    fun getLocation() {
        viewModelScope.launch {
            locationRepository.getCurrentLocation().asResult().collect { locationResult ->
                when (locationResult) {
                    is Result.Success -> getWeather(
                        WeatherRequest.Coordinates(
                            lon = locationResult.data.longitude.toString(),
                            lat = locationResult.data.latitude.toString()
                        )
                    )
                    is Result.Loading -> _locationState.value = LocationUiState.Loading
                    is Result.Error -> _locationState.value = LocationUiState.Error
                }
            }
        }
    }

    private fun List<CityResource>.callWeatherByCityList() = forEach {
        getWeather(WeatherRequest.City(cityName = it.name))
    }
}

sealed interface CityListUiState {
    object Loading : CityListUiState
    object Error : CityListUiState
}

sealed interface WeatherUiState {
    object Loading : WeatherUiState
    data class Success(val weather: List<WeatherResource>) : WeatherUiState
    object Error : WeatherUiState
}

sealed interface LocationUiState {
    object Loading : LocationUiState
    object Error : LocationUiState
}
