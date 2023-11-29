package corp.jasane.provider.appcomponents.utility

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import corp.jasane.provider.R
import java.text.NumberFormat

class OfferTextInputLayout: AppCompatEditText {

    private val numberFormat: NumberFormat = NumberFormat.getNumberInstance()

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        hint = (resources.getString(R.string.masukkan_nominalnya))
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                removeTextChangedListener(this)

                try {
                    val originalString = s.toString().replace("\\D".toRegex(), "")
                    val longVal = originalString.toLong()

                    val formattedString = numberFormat.format(longVal).replace(",", ".")
                    setText(formattedString)
                    setSelection(text?.length ?: 0)
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }

                addTextChangedListener(this)
            }
        })
    }
}