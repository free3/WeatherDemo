package lt.viskasnemokamai.weatherapp.ui.city_search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import lt.viskasnemokamai.weatherapp.R
import lt.viskasnemokamai.weatherapp.databinding.FragmentCitySearchBinding
import lt.viskasnemokamai.weatherapp.domain.CityWeather
import lt.viskasnemokamai.weatherapp.network.WeatherApiService
import lt.viskasnemokamai.weatherapp.ui.ParentFragment
import lt.viskasnemokamai.weatherapp.WeatherApplication
import lt.viskasnemokamai.weatherapp.utils.CommonUtils
import kotlin.collections.ArrayList


/**
 * Allows to enter a city for weather lookup or check city-search history.
 * Activities that contain this fragment must implement the
 * [CitySearchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CitySearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CitySearchFragment : ParentFragment(), CitySearchView {

    private val TAG = "CitySearchFragment"

    //****************************************
    // Dependencies
    //****************************************

    internal lateinit var apiService: WeatherApiService

    internal lateinit var presenter: CitySearchPresenter

    internal lateinit var commonUtils: CommonUtils

    //****************************************
    // Interaction Listener
    //****************************************

    internal var listener: OnFragmentInteractionListener? = null

    //****************************************
    // View Binding
    //****************************************

    internal var _binding: FragmentCitySearchBinding? = null
    private val binding get() = _binding!!

    //****************************************
    // Lifecycle overrides
    //****************************************

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = WeatherApiService.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        presenter =
            CitySearchPresenter(
                this,
                (context!!.applicationContext as WeatherApplication).database,
                CitySearchInteractor()
            )

        _binding = FragmentCitySearchBinding.inflate(inflater, container, false)

        val view = binding.root

        binding.btnViewHistory.setOnClickListener(View.OnClickListener { searchCityViewHistory() })
        binding.ibSearch.setOnClickListener(View.OnClickListener {
            val city = binding.etCitySearch.text.toString()
            searchCity(city)
        }
        )

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
        commonUtils = CommonUtils(activity!!)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onDestroyView() {
        presenter.onDestroy()
        _binding = null
        super.onDestroyView()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {

        fun onCitySearchCompleted(cityWeather: ArrayList<CityWeather>)

    }

    //****************************************
    // Factory method
    //****************************************

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment CitySearchFragment.
         */
        @JvmStatic
        fun newInstance() =
            CitySearchFragment().apply {}
    }

    //****************************************
    // Local utils
    //****************************************

    /**
     * Tries to lookup single city
     *
     * @param city to lookup
     */
    private fun searchCity(city: String) {

        if (!presenter.searchCity(city)) {
            toastWrapper.showShort(R.string.error_city_blank)
            return
        }

    }

    /**
     * Retrieves city-search history and performs lookups
     */
    private fun searchCityViewHistory() {

        if (!presenter.searchHistory()) {
            toastWrapper.showShort(R.string.error_no_items_in_history_yet)
            return
        }

    }

    //****************************************
    // CitySearchView overrides
    //****************************************

    override fun showProgress() {
        binding.pbLoading.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.pbLoading.visibility = View.GONE
    }

    override fun navigateToResults(cityWeather: ArrayList<CityWeather>) {
        listener?.onCitySearchCompleted(cityWeather)
        commonUtils.hideKeyboard()
    }

    override fun setNotFound() {
        toastWrapper.showShort(R.string.error_no_results_for_this_city)
    }

    override fun setError() {
        toastWrapper.showShort(R.string.error_server_fault)
    }

}
