package com.VidyabhiSol.smartcredituse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.VidyabhiSol.smartcredituse.Helpers.HelperUtility;
import com.VidyabhiSol.smartcredituse.Helpers.SMSExpandableListAdapter;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class ReportMissingCard extends Activity {
	ExpandableListView lstViewMsg;
	List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    SMSExpandableListAdapter adapter ;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_missing_card);
		lstViewMsg = (ExpandableListView)findViewById(R.id.reportmissingcard_expandableListViewmsg);
		lstViewMsg.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				CheckBox cb = (CheckBox)v.findViewById( R.id.reportmissingcard_checkBoxChoice);
				
		        if( cb != null ){
		        	cb.toggle();
		        }
				return false;
			}
		});
		
		setScanSMSAndSetList();
	}
	private void setScanSMSAndSetList(){
		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		Uri message = Uri.parse("content://sms/inbox");
		String[] reqCols = new String[] { "_id", "address", "body"};
		ContentResolver cr = getContentResolver();
		String whereClause = "body like ? or body like ? or body like ? or body like ? or body like ? or body like ?";
		String[] values = new String[]{"%Credit%", "%Credit Card%", "%Card%", "%credit%", "%card%","%credit card%"};
		Cursor c = cr.query(message, reqCols, whereClause , values , "address");
		if(c.getCount()>0){
			c.moveToFirst();
			do{
				 String address = c.getString(c.getColumnIndex("address"));
				 String body = c.getString(c.getColumnIndex("body"));
				 
					 if(!TextUtils.isDigitsOnly(address.substring(1))){
						 if(!listDataHeader.contains(address)){
							 listDataHeader.add(address);
						 }
						 if(!listDataChild.containsKey(address)){
							listDataChild.put(address, new ArrayList<String>());
						 }
						 listDataChild.get(address).add(body);
					 }
				 
			} while(c.moveToNext());
			
		} 
		adapter = new SMSExpandableListAdapter(this, listDataHeader, listDataChild);
		lstViewMsg.setAdapter(adapter);
	}
	public void OnReportClicked(View view){
		StringBuilder mailBody = new StringBuilder();
		for(String str:adapter.checkedMsg){
			mailBody.append(str + "\n\n");
		}
		Calendar c = Calendar.getInstance();
		//Toast.makeText(getBaseContext(), mailBody, Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setType("text/html");
		intent.setData(Uri.parse("mailto:" + getString(R.string.aboutscu_Vidyabhimail)));
		intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.reportmisingcard_CardMessages) + "-" + HelperUtility.formatDateForDisplay(c.getTime()));
		intent.putExtra(Intent.EXTRA_TEXT,mailBody.toString());
		startActivity(Intent.createChooser(intent, "Send Email"));
	}
}
