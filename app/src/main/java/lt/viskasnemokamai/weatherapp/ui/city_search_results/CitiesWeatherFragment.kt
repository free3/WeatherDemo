package lt.viskasnemokamai.weatherapp.ui.city_search_results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import lt.viskasnemokamai.weatherapp.databinding.FragmentCitiesWeatherBinding
import lt.viskasnemokamai.weatherapp.domain.CityWeather
import lt.viskasnemokamai.weatherapp.ui.ParentFragment

private const val ARG_CITIES_WEATHER = "ARG_CITIES_WEATHER"

/**
 * Displays single or list of city-weather information.
 * Use the [CitiesWeatherFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CitiesWeatherFragment : ParentFragment() {

    //****************************************
    // Dependencies
    //****************************************

    private lateinit var citiesWeather: List<CityWeather>

    //****************************************
    // Interaction Listener
    //****************************************


    //****************************************
    // View Binding
    //****************************************

    internal var _binding: FragmentCitiesWeatherBinding? = null
    private val binding get() = _binding!!

    //****************************************
    // Lifecycle overrides
    //****************************************

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            citiesWeather = it.getSerializable(ARG_CITIES_WEATHER) as List<CityWeather>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCitiesWeatherBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.ibBack.setOnClickListener(View.OnClickListener { activity!!.onBackPressed() })
        binding.lvCitiesWeather.adapter =
            CitiesWeatherListAdapter(
                activity!!.baseContext,
                citiesWeather
            )
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //****************************************
    // Factory method
    //****************************************

    companion object {
        /**
         * Factory method
         *
         * @param citiesWeather list of cities to show weather
         * @return A new instance of fragment CitiesWeatherFragment.
         */
        @JvmStatic
        fun newInstance(citiesWeather: ArrayList<CityWeather>) =
            CitiesWeatherFragment()
                .apply {
                    arguments = Bundle().apply {
                        putSerializable(ARG_CITIES_WEATHER, citiesWeather)
                    }
                }
    }

    //****************************************
    // Local utils
    //****************************************
}
