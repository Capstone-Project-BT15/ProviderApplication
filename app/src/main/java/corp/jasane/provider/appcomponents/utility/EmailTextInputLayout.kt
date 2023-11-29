package corp.jasane.provider.appcomponents.utility

import android.content.Context
import android.graphics.Canvas
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import corp.jasane.provider.R
import java.util.regex.Pattern

class EmailTextInputLayout : AppCompatEditText {

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
        hint = (resources.getString(R.string.masukkan_email_anda))
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    val emailPattern = Pattern.compile(
        "[a-zA-Z0-9+._%\\-]{1,256}" +
                "@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    private fun init() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        val email = s.toString().trim()

        if (email.isNotEmpty() && !emailPattern.matcher(email).matches()) {
            error = (resources.getString(R.string.format_email_salah))
        } else {
            error = null
        }
    }
}