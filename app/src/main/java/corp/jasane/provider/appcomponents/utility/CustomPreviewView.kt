//package corp.jasane.provider.appcomponents.utility
//
//import android.content.Context
//import android.graphics.drawable.GradientDrawable
//import android.util.AttributeSet
//import android.view.LayoutInflater
//import android.widget.FrameLayout
//import androidx.core.content.ContextCompat
//import corp.jasane.provider.R
//import androidx.camera.view.PreviewView
//
//class CustomPreviewView @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0,
//) : FrameLayout(context, attrs, defStyleAttr) {
//
//    private lateinit var previewView: PreviewView
//
//    init {
//        LayoutInflater.from(context).inflate(R.layout.activity_verification_two, this, true)
//        previewView = findViewById(R.id.camera_preview)
//        setOverlay()
//    }
//
//    override fun onFinishInflate() {
//        super.onFinishInflate()
//        previewView = findViewById(R.id.camera_preview)
//        setOverlay()
//    }
//
//    private fun setOverlay() {
//        val shape = GradientDrawable()
//        shape.shape = GradientDrawable.RECTANGLE
//        shape.setColor(ContextCompat.getColor(context, R.color.gray_100))
//        shape.cornerRadius = resources.getDimension(R.dimen._12pxh)
//
//        background = shape
//    }
//
//    fun getPreviewView(): PreviewView {
//        return previewView
//    }
//}
