package corp.jasane.provider.appcomponents.utility

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import corp.jasane.provider.R
import java.util.Calendar

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    private var mListener: DialogDateListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val date = calendar.get(Calendar.DATE)
        return DatePickerDialog(requireContext(), R.style.DatePickerTheme, this, year, month, date)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        mListener?.onDialogDateSet(tag, year, month, dayOfMonth)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DialogDateListener) {
            mListener = context
        } else if (parentFragment is DialogDateListener) {
            mListener = parentFragment as DialogDateListener
        } else {
            throw ClassCastException("$context must implement DialogDateListener")
        }
    }


//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is DialogDateListener) {
//            mListener = context
//        } else {
//            throw ClassCastException("$context must implement DialogDateListener")
//        }
//    }

    override fun onDetach() {
        super.onDetach()
        if (mListener != null) {
            mListener = null
        }
    }

    interface DialogDateListener {
        fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int)
    }
}