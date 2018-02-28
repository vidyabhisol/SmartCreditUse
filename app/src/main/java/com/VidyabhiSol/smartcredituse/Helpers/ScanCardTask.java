package com.VidyabhiSol.smartcredituse.Helpers;

import java.util.List;
import com.VidyabhiSol.smartcredituse.MainActivity;
import com.VidyabhiSol.smartcredituse.Data.BankDataBin;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

public class ScanCardTask extends AsyncTask<Void, Integer, Boolean>{

	Context _context;
	List<BankDataBin> _lstBankList;
	private ProgressDialog _dialog;
	DBHelper _helper;
	
	public ScanCardTask(Activity context,DBHelper helper){
		_context = context;
		_helper = helper;
		_lstBankList = helper.getSupportedBanks(-1);
		_dialog = new ProgressDialog(_context);
	}
	 @Override
	  protected void onPreExecute() {
	        _dialog.setMessage("We are analyzing the messages. Please wait..");
	        _dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	        _dialog.setCancelable(false);
	        _dialog.show();
	    }
	@Override
	protected Boolean doInBackground(Void... params) {
		Uri message = Uri.parse("content://sms/inbox");
		String[] reqCols = new String[] { "_id", "address", "body"};
		ContentResolver cr = _context.getContentResolver();
		
		StringBuilder whereClause = new StringBuilder();
		String[] values = new String[_lstBankList.size()];
		for (int i = 0;i<_lstBankList.size();i++){
			whereClause.append(" address like ? ");
			values[i] = ("%" + _lstBankList.get(i).get_smsAddress() + "%");
			if(i<_lstBankList.size()-1){
				whereClause.append(" or ");
			}
		}
		Cursor c = cr.query(message, reqCols, "" , null , "address");

		int numMessages = c.getCount(); 
		int i=1;
		if(c.getCount()>0){
			c.moveToFirst();
		
			do{
				 String address = c.getString(c.getColumnIndex("address"));
				 String body = c.getString(c.getColumnIndex("body"));
				 HelperUtility.matchMessageUsingRegex(address, body,_lstBankList,_context,-1);
				 publishProgress((int) ((i / (float) numMessages) * 100));
				 i++; 
			} while(c.moveToNext());
			
		} 
		return true;
	}
	@Override
	 protected void onPostExecute(Boolean result) {
		if (_dialog.isShowing()) {
            _dialog.dismiss();
        }
		((MainActivity) _context).fillCreditCardList();
	 }
	@Override
	protected void onProgressUpdate(Integer...params) {
		_dialog.setProgress(params[0]);
	}
		
}
	

