package lt.viskasnemokamai.weatherapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.FragmentActivity

/**
 * Common Android helper tools
 */
class CommonUtils(var activity: FragmentActivity) {

    /**
     * Hides soft keyboard
     */
    fun hideKeyboard() {
        hideKeyboard(activity.currentFocus ?: View(activity), activity)
    }

    /**
     * Hides soft keyboard
     */
    fun hideKeyboard(view: View, context: Context) {
        val inputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

}