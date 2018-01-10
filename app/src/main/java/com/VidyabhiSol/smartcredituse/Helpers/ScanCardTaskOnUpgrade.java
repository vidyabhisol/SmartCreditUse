package com.VidyabhiSol.smartcredituse.Helpers;

import com.VidyabhiSol.smartcredituse.MainActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

public class ScanCardTaskOnUpgrade extends AsyncTask<Void, Integer, Boolean>{

	Context _context;
	int _version ;
	private ProgressDialog _dialog;
	public ScanCardTaskOnUpgrade(Context _context2, int version){
		_context = _context2;
		_version = version;
		_dialog = new ProgressDialog(_context);
	}
	@Override
	  protected void onPreExecute() {
	        _dialog.setMessage("We are analyzing newly supported banks and messages. Please wait..");
	        _dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
	        _dialog.setCancelable(false);
	        _dialog.show();
	    }
	@Override
	protected Boolean doInBackground(Void... params) {
		Uri message = Uri.parse("content://sms/inbox");
		String[] reqCols = new String[] { "_id", "address", "body"};
		ContentResolver cr = _context.getContentResolver();
		String whereClause = "body like ? or body like ? or body like ? or body like ? or body like ? or body like ?";
		String[] values = new String[]{"%Credit%", "%Credit Card%", "%Card%", "%credit%", "%card%","%credit card%"};
		Cursor c = cr.query(message, reqCols, whereClause.toString() , values , "address");
		int numMessages = c.getCount(); 
		int i=1;
		if(c.getCount()>0){
			c.moveToFirst();
		
			do{
				 String address = c.getString(c.getColumnIndex("address"));
				 String body = c.getString(c.getColumnIndex("body"));
				 HelperUtility.matchMessageUsingRegex(address, body,null,_context,_version);
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
	   ((MainActivity)_context).fillCreditCardList();	
	}
	@Override
	protected void onProgressUpdate(Integer...params) {
		_dialog.setProgress(params[0]);
	}
	
		
}
	

