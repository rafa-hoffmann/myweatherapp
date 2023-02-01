package com.sonder.myweatherapp.weather_list

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.sonder.myweatherapp.feature.weather_list.R
import com.sonder.myweatherapp.feature.weather_list.databinding.FragmentWeatherListBinding
import com.sonder.myweatherapp.weather_list.adapter.WeatherListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class WeatherListFragment : Fragment() {

    private val viewModel: WeatherListViewModel by viewModels()

    private var _binding: FragmentWeatherListBinding? = null
    private val binding get() = _binding!!

    private var _adapter: WeatherListAdapter? = null
    private val adapter get() = _adapter!!

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                viewModel.getLocation()
            }
            else -> {
                showErrorToast(getString(R.string.location_permission_error))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherListBinding.inflate(inflater, container, false)
        _adapter = WeatherListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerView()
        setupObservers()

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        viewModel.getCityList()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerView() {
        binding.weatherCityList.adapter = adapter
    }

    private fun setupObservers() {
        viewModel.cityListState.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach {
            when (it) {
                is CityListUiState.Loading -> {}
                is CityListUiState.Error -> showErrorToast(getString(R.string.cities_state_error))
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.weatherState.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach {
            when (it) {
                is WeatherUiState.Success -> adapter.submitList(it.weather)
                WeatherUiState.Error -> showErrorToast(getString(R.string.weather_state_error))
                WeatherUiState.Loading -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewModel.locationState.flowWithLifecycle(
            lifecycle,
            Lifecycle.State.STARTED
        ).onEach {
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun showErrorToast(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }
}
