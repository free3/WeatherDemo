package lt.viskasnemokamai.weatherapp.utils.toast

import android.content.Context
import android.widget.Toast

/**
 * Created by Greta Radisauskaite on 08/10/2020.
 */
class ToastWrapperImpl(var context: Context) :
    ToastWrapper {

    override fun showShort(restId : Int) {
        Toast.makeText(context, restId, Toast.LENGTH_SHORT).show()
    }

}