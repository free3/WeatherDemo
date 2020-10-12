package lt.viskasnemokamai.weatherapp.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import lt.viskasnemokamai.weatherapp.utils.toast.ToastWrapper
import lt.viskasnemokamai.weatherapp.utils.toast.ToastWrapperImpl

/**
 * Created by Greta Radisauskaite on 11/10/2020.
 */
abstract class ParentFragment: Fragment() {

    //****************************************
    // Dependencies
    //****************************************

    internal lateinit var toastWrapper: ToastWrapper

    //****************************************
    // Lifecycle overrides
    //****************************************

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        toastWrapper = ToastWrapperImpl(activity!!)
    }

}