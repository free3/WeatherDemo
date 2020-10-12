package lt.viskasnemokamai.weatherapp.network


import io.reactivex.Single
import lt.viskasnemokamai.weatherapp.BuildConfig
import lt.viskasnemokamai.weatherapp.dao.AppDatabase
import lt.viskasnemokamai.weatherapp.network.dto.SearchByCityResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.adapter.rxjava2.Result

/**
 * Created by Greta Radisauskaite on 2020-10-07.
 */
interface WeatherApiService {

    // ToDo: We might want to move appid param somewhere else.

    @GET("weather?appid=${WeatherApiServiceConstants.API_REQUEST_APP_ID}")
    fun searchWeather(
        @Query(value = "q", encoded = true) city: String,
        @Query(value = "units", encoded = false) units: String = "metric"
    ): Single<Result<SearchByCityResponse>>

    companion object {

        private var INSTANCE: WeatherApiService? = null

        private fun createService(): WeatherApiService {

            val httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder.addNetworkInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                        .build()
                )
            }

            if (BuildConfig.DEBUG) {
                // Adding full http requests and responses logging
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                httpClientBuilder.addInterceptor(logging)
            }

            val builder = Retrofit.Builder()
                .baseUrl(WeatherApiServiceConstants.API_REQUEST_BASE_URL)

                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClientBuilder.build())

                .build()

            return builder.create(WeatherApiService::class.java)
        }

        fun getInstance(): WeatherApiService {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = createService()
                }
            }
            return INSTANCE!!
        }

    }

}