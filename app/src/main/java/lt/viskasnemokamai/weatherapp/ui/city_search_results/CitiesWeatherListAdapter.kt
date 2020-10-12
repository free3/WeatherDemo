package lt.viskasnemokamai.weatherapp.ui.city_search_results

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.request.transition.Transition
import lt.viskasnemokamai.weatherapp.R
import lt.viskasnemokamai.weatherapp.domain.CityWeather
import lt.viskasnemokamai.weatherapp.network.WeatherApiServiceConstants
import lt.viskasnemokamai.weatherapp.utils.glide.GlideWrapper
import org.apache.commons.lang3.StringUtils
import java.util.*


/**
 * Created by Greta Radisauskaite on 2020-10-08.
 */
class CitiesWeatherListAdapter : BaseAdapter {

    //****************************************
    // Dependencies
    //****************************************

    internal var context: Context
    internal var layoutInflater: LayoutInflater
    internal var citiesWeather: List<CityWeather>
    internal var glideWrapper: GlideWrapper

    //****************************************
    // Constructor
    //****************************************

    constructor(context: Context, citiesWeather: List<CityWeather>) : super() {
        this.context = context
        this.citiesWeather = citiesWeather
        layoutInflater = context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        glideWrapper = GlideWrapper()
    }

    //****************************************
    // Overrides
    //****************************************

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view: View? = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_city_weather, null)
            var holder: ViewHolder = createViewHolderFrom(view);
            view.setTag(holder);
        }
        var holder: ViewHolder = view?.getTag() as ViewHolder

        var city = getItem(position)

        // City Name
        if (city.name != null) {
            holder.tvCity.text = StringUtils.capitalize(city.name)
        }

        // Temperature
        if (city.temp != null) {
            holder.tvTemperature.text = city.temp.toString() + "\u00B0"
        }

        // Date
        if (city.date != null) {
            var calendar = Calendar.getInstance()
            calendar.time = city.date
            var dateString =
                calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault())
                    .toUpperCase()
            dateString += System.getProperty("line.separator")
            dateString += String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH))

            holder.tvDate.text = dateString
        }

        // Weather Description & Description icon
        if (city.weatherDescription != null) {
            var descriptorString = StringUtils.capitalize(city.weatherDescription)
            view.findViewById<TextView>(R.id.tvWeatherDesc).text = descriptorString
        }
        if (city.weatherIcon != null) {
            loadImg(city.weatherIcon, holder.tvWeatherDesc)
        }


        // Finally update view colors
        holder.updateAppearance(getAppearanceStrategy(city))

        return view
    }

    override fun getItem(position: Int): CityWeather {
        return citiesWeather.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return citiesWeather.size
    }

    //****************************************
    // Local utils
    //****************************************

    /**
     * ViewHolder to use in this ListView
     */
    internal open class ViewHolder (
        val tvCity: TextView,
        val tvTemperature: TextView,
        val tvDate: TextView,
        val tvWeatherDesc: TextView
    ) {
        /**
         * Update certain elements' style in this ViewHolder based on provided strategy
         *
         * @param appearanceStrategy styling
         */
        fun updateAppearance(appearanceStrategy: AppearanceStrategy?) {
            if (appearanceStrategy == null) {
                return
            }
            tvCity.setTextColor(appearanceStrategy.cityNameColor())
            tvTemperature.setTextColor(appearanceStrategy.temperatureColor())
            tvDate.setTextColor(appearanceStrategy.dateColor())
        }
    }

    /**
     * Creates and initializes ViewHolder from View
     */
    private fun createViewHolderFrom(view: View): ViewHolder {
        val tvCity = view.findViewById<TextView>(R.id.tvCity)
        val tvTemperature = view.findViewById<TextView>(R.id.tvTemperature)
        val tvDate = view.findViewById<TextView>(R.id.tvDate)
        val tvWeatherDesc = view.findViewById<TextView>(R.id.tvWeatherDesc)

        return ViewHolder(
            tvCity,
            tvTemperature,
            tvDate,
            tvWeatherDesc
        )
    }

    /**
     * Build AppearanceStrategy based on temperature
     */
    private fun getAppearanceStrategy(cityWeather: CityWeather): AppearanceStrategy? {
        if (cityWeather.temp == null) {
            return null
        }

        return when {
            cityWeather.temp <= 10 -> {
                ColdWeatherAppearanceStrategy(
                    context
                )
            }
            cityWeather.temp < 20 -> {
                IntermediateWeatherAppearanceStrategy(
                    context
                )
            }
            else -> {
                WarmWeatherAppearanceStrategy(
                    context
                )
            }
        }
    }

    /**
     * Load icon from network and display it as Compound Drawable
     */
    private fun loadImg(icon: String?, view: TextView) {

        if (icon == null) {
            return
        }

        glideWrapper.load(
            context, WeatherApiServiceConstants.DESCRIPTION_ICON_URL(icon),
        view.textSize.toInt() * 2, view.textSize.toInt() * 2
        ) { res: Drawable, _: Transition<in Drawable>? ->
            view.setCompoundDrawablesRelativeWithIntrinsicBounds(res, null, null, null)
        }

    }
}