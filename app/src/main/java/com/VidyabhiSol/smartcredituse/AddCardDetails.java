package com.VidyabhiSol.smartcredituse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.VidyabhiSol.smartcredituse.Data.BankCardDataBin;
import com.VidyabhiSol.smartcredituse.Helpers.DBHelper;
import com.VidyabhiSol.smartcredituse.Helpers.DatePickerFragment;
import com.VidyabhiSol.smartcredituse.Helpers.HelperUtility;
import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddCardDetails extends Activity {
	int selectedCardId; 
	String bankName;
	String cardNumber;
	EditText aDate;
	EditText aCrLimit;
	EditText aCrPeriod;
	DBHelper helper;
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_card_details);
		helper = new DBHelper(this);
		
		selectedCardId = getIntent().getIntExtra(HelperUtility.SELECTEDCARD, 0);
		bankName = getIntent().getStringExtra(HelperUtility.SELECTEDCARDBANK);
		cardNumber = getIntent().getStringExtra(HelperUtility.SELECTEDCARDNUMBER);
		
		
		TextView vLabel = (TextView) findViewById(R.id.AddCardDetails_textViewLabelCreditCardDetails);
		vLabel.setText(bankName + " - " + cardNumber);
		BankCardDataBin bin =  helper.getBankCardDetails(selectedCardId);
		aCrLimit = (EditText) findViewById(R.id.AddCardDetails_editTextCreditLimit);
		aCrPeriod = (EditText) findViewById(R.id.AddCardDetails_editTextCreditPeriod);
		aDate = (EditText) findViewById(R.id.AddCardDetails_editTextStmtDate);
		if(bin!=null){
			aCrLimit.setText(String.valueOf(bin.get_creditLimit()));
			aCrPeriod.setText(String.valueOf(bin.get_creditPeriod()));
			Calendar c = Calendar.getInstance();
			if(bin.get_stmtDate()!=null){
				c.setTime(bin.get_stmtDate());
				String mYear = Integer.toString(c.get(Calendar.YEAR));
				String mMonth =Integer.toString(c.get(Calendar.MONTH)+1);
				String mDay =Integer.toString(c.get(Calendar.DAY_OF_MONTH));
				aDate.setText(mYear + "-" + mMonth + "-" + mDay);
			} else{
				//Setting the current date
				
				String mYear = Integer.toString(c.get(Calendar.YEAR));
				String mMonth =Integer.toString(c.get(Calendar.MONTH)+1);
				String mDay =Integer.toString(c.get(Calendar.DAY_OF_MONTH));
				aDate.setText(mYear + "-" + mMonth + "-" + mDay);
			}
			
		}
		
		
		aDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new DatePickerFragment();
				
				newFragment.show(getFragmentManager(), "datePicker");
				
			}
		});
		
		Button btnAddDetails = (Button) findViewById(R.id.AddCardDetails_buttonSaveDetails);
		
		btnAddDetails.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(ValidateData()){
				 Boolean result = null;
				try {
					
					result = helper.updateBankCardDetails(selectedCardId, Double.parseDouble(aCrLimit.getText().toString()),
							 format.parse(aDate.getText().toString()), Integer.parseInt(aCrPeriod.getText().toString()));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
				 if(result){
					 Intent data =new Intent();
					 setResult(RESULT_OK, data); 
					finish();
				 }
				 else
					 Toast.makeText(getBaseContext(), getString(R.string.AddCardDetails_updateFailed), Toast.LENGTH_SHORT).show();
					
				}
				
			}
		});

	}

	
	private Boolean ValidateData(){
		Boolean returnVal = true;
		if(aDate.getText().toString().length()==0){
			aDate.setError(getString(R.string.AddCardDetails_ValidateDateRequired));
			returnVal=false;
		}
		
		if(aCrLimit.getText().toString().length()==0){
				aCrLimit.setError(getString(R.string.AddCardDetails_ValidateCrLimitRequired));
				returnVal=false;
			
		} else{
			if(Double.parseDouble(aCrLimit.getText().toString())<=0.0){
				aCrLimit.setError(getString(R.string.AddCardDetails_ValidateCrLimitRequired));
				returnVal=false;
			}
		}
			
		if(aCrPeriod.getText().toString().length()==0){
			aCrPeriod.setError(getString(R.string.AddCardDetails_ValidateCrPeriodRequired));
			returnVal=false;
			
		} else{
			if(Integer.parseInt(aCrPeriod.getText().toString())<=0){
				aCrPeriod.setError(getString(R.string.AddCardDetails_ValidateCrPeriodRequired));
				returnVal=false;
			}
		}
		return returnVal;
	}
	
}
