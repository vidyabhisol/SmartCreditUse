package com.VidyabhiSol.smartcredituse.Helpers;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.DatePicker;
import android.widget.EditText;

import com.VidyabhiSol.smartcredituse.R;


public class DatePickerFragment extends DialogFragment
implements DatePickerDialog.OnDateSetListener{

	
	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar cal = Calendar.getInstance();
		cal.set(year, monthOfYear, dayOfMonth);
		CharSequence output = DateFormat.format("yyyy-MM-dd", cal);
		String selectedDate = output.toString();
		((EditText) getActivity().findViewById(R.id.AddCardDetails_editTextStmtDate)).setText(selectedDate);
	}
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
	
	
}
