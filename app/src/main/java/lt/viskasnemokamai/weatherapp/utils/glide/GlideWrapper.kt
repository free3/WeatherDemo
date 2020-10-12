package lt.viskasnemokamai.weatherapp.utils.glide

import android.content.Context
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


/**
 * Created by Greta Radisauskaite on 08/10/2020.
 */
open class GlideWrapper {

    open fun load(context: Context, url: String, width : Int, height : Int,
             onResourceReadyCallback: (res: Drawable, transition: Transition<in Drawable>?) -> (Unit)
             ) {

        Glide.with(context)
            .asDrawable()
            .load(url)
            .apply(RequestOptions().override(width, height))
            .into(object : CustomTarget<Drawable>() {

                override fun onLoadCleared(drawable: Drawable?) {
                }

                override fun onResourceReady(res: Drawable, transition: Transition<in Drawable>?) {
                    onResourceReadyCallback(res, transition)
                }
            })

    }

}