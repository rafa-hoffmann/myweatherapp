package com.sonder.myweatherapp.weather_list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sonder.myweatherapp.common.image.loadImage
import com.sonder.myweatherapp.feature.weather_list.R
import com.sonder.myweatherapp.feature.weather_list.databinding.ItemWeatherInfoBinding
import com.sonder.myweatherapp.model.data.WeatherResource

private const val IMAGE_SIZE_AND_EXTENSION = "@4x.png"

class WeatherListAdapter :
    ListAdapter<WeatherResource, WeatherListAdapter.ViewHolder>(WeatherListAdapter) {

    inner class ViewHolder(private val binding: ItemWeatherInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weather: WeatherResource) {
            with(binding) {
                weatherTemperature.text = weather.main.temp.formatToShow()
                weatherTemperatureMax.text = root.context.getString(
                    R.string.weather_temperature_max,
                    weather.main.tempMax.formatToShow()
                )
                weatherTemperatureMin.text = root.context.getString(
                    R.string.weather_temperature_min,
                    weather.main.tempMin.formatToShow()
                )
                weatherDescription.text = weather.weather.description
                weatherFeelsLike.text =
                    root.context.getString(
                        R.string.weather_feels_like,
                        weather.main.feelsLike.formatToShow()
                    )
                weatherHumidity.text =
                    root.context.getString(
                        R.string.weather_humidity,
                        weather.main.humidity.formatToShow()
                    )
                weatherLocation.text = weather.name

                weatherImage.loadImage(weather.weather.icon + IMAGE_SIZE_AND_EXTENSION)

                weatherCurrentLocation.isVisible = weather.isCustomLocation
            }
        }

        private fun Int.formatToShow() = "${toString()}%"

        private fun Double.formatToShow() = "%.0fº".format(this)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding =
            ItemWeatherInfoBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(getItem(position))
    }

    private companion object : DiffUtil.ItemCallback<WeatherResource>() {
        override fun areItemsTheSame(oldItem: WeatherResource, newItem: WeatherResource) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(
            oldItem: WeatherResource,
            newItem: WeatherResource
        ) = oldItem.dt.epochSeconds == newItem.dt.epochSeconds
    }
}
