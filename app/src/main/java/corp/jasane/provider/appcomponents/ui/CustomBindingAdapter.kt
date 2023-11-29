package corp.jasane.provider.appcomponents.ui

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import corp.jasane.provider.R

const val IMAGE_BASE_URL = ""

@BindingAdapter(value = ["viewVisibility", "isInvisible"], requireAll = false)
fun setVisibility(view: View, isVisible: Boolean, isInvisible: Boolean) {
    view.visibility =
        if (isVisible) View.VISIBLE else if (isInvisible) View.INVISIBLE else View.GONE
}

@BindingAdapter(
    value = ["imageUrl", "placeHolder", "placeHolderError", "cornerRadius","circular"], requireAll = false
)
fun loadImageFromNetwork(
    imageView: ImageView, url: String?, placeHolder: Drawable?,
    placeHolderError: Drawable?,
    cornerRadius: Float,
    circular:Boolean
) {
    var imageURL = url

    if (imageURL != null && !imageURL.isEmpty() && !imageURL.equals(
            "",
            ignoreCase = true
        )
    ) {
        imageURL = imageURL.trim { it <= ' ' }
        if (imageURL.startsWith("/"))
            imageURL = IMAGE_BASE_URL + url

        val options = RequestOptions()
            .placeholder(placeHolder)
            .error(placeHolderError)

        if (circular)
            options.circleCrop()

        if (cornerRadius > 0)
            options.transform(CenterCrop(),RoundedCorners(cornerRadius.toInt()))

        Glide.with(imageView).load(imageURL).apply(options).into(imageView)
    } else {
        imageView.setImageDrawable(placeHolder)
    }
}

@BindingAdapter(
    value = ["imageRes", "cornerRadius", "circular"], requireAll = false
)
fun loadImageFromResource(
    imageView: ImageView, imageRes: Drawable?, cornerRadius: Float, circular: Boolean
) {
    if (imageRes == null) {
        imageView.setImageResource(R.drawable.image_not_found)
    } else {
        val options = RequestOptions()
        if (circular)
            options.circleCrop()
        if (cornerRadius > 0)
            options.transform(CenterCrop(),RoundedCorners(cornerRadius.toInt()))
        Glide.with(imageView).load(imageRes).apply(options).into(imageView)
    }
}

@BindingAdapter(value = ["itemSpace","includeEdge"], requireAll = false)
fun addSpaceBetweenRecyclerItem(
    recyclerView: RecyclerView,
    itemSpace: Double,
    includeEdge:Boolean
) {
    recyclerView.addItemDecoration(
       RecyclerItemDecoration(itemSpace.toInt(),includeEdge)
    )
}

@BindingAdapter(value = ["textUnderline"], requireAll = false)
fun setTextUnderline(
    textView: TextView,
    isTextUnderline: Boolean
) {
    if (isTextUnderline) {
        textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
}

