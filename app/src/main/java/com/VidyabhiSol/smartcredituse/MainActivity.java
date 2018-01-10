package com.VidyabhiSol.smartcredituse;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.VidyabhiSol.smartcredituse.Data.BankCardDataBin;
import com.VidyabhiSol.smartcredituse.Helpers.BankCardAdapter;
import com.VidyabhiSol.smartcredituse.Helpers.DBHelper;
import com.VidyabhiSol.smartcredituse.Helpers.HelperUtility;
import com.VidyabhiSol.smartcredituse.Helpers.ScanCardTask;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("InflateParams")
public class MainActivity extends Activity {
	final int REQUEST_CODE_ASK_PERMISSIONS = 123;
	SimpleCursorAdapter adapter;
	ListView lvMsg;
	DBHelper helper;
	TextView textViewMessage,textviewGoodCardMsg;
	ImageButton imagebuttonSetting;
	protected Object mActionMode;
	int selectedCardId=0;
	String selectedBankName;
	String selectedCardNumber;
	public static int MODIFYCARD = 1; 
	private AdView mAdView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mAdView = (AdView) findViewById(R.id.MainScreen_adView);
		//mAdView.loadAd(new AdRequest.Builder().build());
		
		
		helper = new DBHelper(this);
		lvMsg = (ListView) findViewById(R.id.MainActivity_listViewSMSList);
		textViewMessage = (TextView) findViewById(R.id.MainActivity_textViewMessage);
		textviewGoodCardMsg = (TextView) findViewById(R.id.MainActivity_textViewGoodCardMessage);
		imagebuttonSetting = (ImageButton) findViewById(R.id.MainActivity_imageButtonSetting);
		textViewMessage.setVisibility(View.INVISIBLE);
		textviewGoodCardMsg.setVisibility(View.INVISIBLE);
		imagebuttonSetting.setVisibility(View.INVISIBLE);
		lvMsg.setVisibility(View.INVISIBLE);
		boolean firstboot = getSharedPreferences("BOOT_PREF",MODE_PRIVATE)
			    .getBoolean("firstboot", true);
		boolean firstbootAfterUpgrade = getSharedPreferences("BOOT_PREF",MODE_PRIVATE)
			    .getBoolean("firstbootAfterUpgrade", true);
		try {
			if(firstbootAfterUpgrade && this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionCode==4){
				firstboot = true;
				getSharedPreferences("BOOT_PREF",MODE_PRIVATE).edit().
			    putBoolean("firstbootAfterUpgrade", false)
			                        .commit();
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		
		if(firstboot){
			 OnScanSMSClicked();                  
				getSharedPreferences("BOOT_PREF",MODE_PRIVATE).edit().
				    putBoolean("firstboot", false)
				                        .commit();
		 } else{
			 fillCreditCardList();
		 }
		
		 lvMsg.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BankCardDataBin selectedCard = (BankCardDataBin) lvMsg.getAdapter().getItem(position);
				if(selectedCard.get_stmtDate()!=null){
					Intent intCardList = new Intent(getApplicationContext(),TransactionViewer.class);
					intCardList.putExtra(HelperUtility.SELECTEDCARD,selectedCard.get_id());
					intCardList.putExtra(HelperUtility.SELECTEDCARDBANK,selectedCard.get_bankName());
					intCardList.putExtra(HelperUtility.SELECTEDBANKID,selectedCard.get_bankId());
					intCardList.putExtra(HelperUtility.SELECTEDCARDNUMBER, selectedCard.get_cardNumber());
					startActivity(intCardList);
				} else{
					Intent intEdit = new Intent(getBaseContext(),AddCardDetails.class);
			        intEdit.putExtra(HelperUtility.SELECTEDCARD, selectedCard.get_id());
			        intEdit.putExtra(HelperUtility.SELECTEDCARDBANK, selectedCard.get_bankName());
			        intEdit.putExtra(HelperUtility.SELECTEDCARDNUMBER, selectedCard.get_cardNumber());
			    	startActivityForResult(intEdit,MODIFYCARD);  
				}
					
			}
			 
		});
		
		 lvMsg.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (mActionMode != null) {
			          return false;
			        }
				BankCardDataBin selectedCard = (BankCardDataBin) lvMsg.getAdapter().getItem(position);
				selectedCardId = selectedCard.get_id();
				selectedBankName = selectedCard.get_bankName();
				selectedCardNumber = selectedCard.get_cardNumber();
				// start the CAB using the ActionMode.Callback defined above
		        mActionMode = MainActivity.this
		            .startActionMode(mActionModeCallback);
		        view.setSelected(true);
		        return true;
				
				
			}
			 
		});
		 
		 
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.main_activity, menu);
		return true;
	}
	
	@Override
	  public boolean onOptionsItemSelected(MenuItem item) {
	    Toast.makeText(this, "Just a test", Toast.LENGTH_SHORT).show();
	    return true;
	  }
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

	    // Called when the action mode is created; startActionMode() was called
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	      // inflate a menu resource providing context menu items
	      MenuInflater inflater = mode.getMenuInflater();
	      // assumes that you have "main.xml" menu resources
	      inflater.inflate(R.menu.main_activity, menu);
	      return true;
	    }

	    // called each time the action mode is shown. Always called after
	    // onCreateActionMode, but
	    // may be called multiple times if the mode is invalidated.
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	       	
	      return false; // Return false if nothing is done
	    }

	    // called when the user selects a contextual menu item
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	      switch (item.getItemId()) {
	      case R.id.mainactivity_ActionBarEdit:
	        Intent intEdit = new Intent(getBaseContext(),AddCardDetails.class);
	        intEdit.putExtra(HelperUtility.SELECTEDCARD, selectedCardId);
	        intEdit.putExtra(HelperUtility.SELECTEDCARDBANK, selectedBankName);
	        intEdit.putExtra(HelperUtility.SELECTEDCARDNUMBER, selectedCardNumber);
	    	startActivityForResult(intEdit,MODIFYCARD);  
	    	  
	        mode.finish(); // Action picked, so close the CAB
	        return true;
	      case R.id.mainactivity_ActionBarDelete:
	    	  AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				//builder.setMessage(R.string.MainActivity_DeleteCardMsg);
				builder.setView(getLayoutInflater().inflate(R.layout.view_dialog, null));
				builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

			        public void onClick(DialogInterface dialog, int which) {
			            // Do nothing but close the dialog
			        	dialog.dismiss();
			        	helper.deleteBankCardDetails(selectedCardId);
			        	fillCreditCardList();
			            
			        }

			    });

			    builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

			        @Override
			        public void onClick(DialogInterface dialog, int which) {
			            // Do nothing
			            dialog.dismiss();
			        }
			    });

			    AlertDialog alert = builder.create();
			    alert.show();
		        
	    	  
		        mode.finish(); // Action picked, so close the CAB
		        return true;
	      default:
	        return false;
	      }
	    }

	    // called when the user exits the action mode
	    public void onDestroyActionMode(ActionMode mode) {
	      mActionMode = null;
	    }
	  };
	
	
	private int getBestCardForToday(List<BankCardDataBin> lstBankCards){
		Calendar curCalendar =Calendar.getInstance(); 
		Date curDate = curCalendar.getTime();
		double max=0;
		int goodCardId = 0;
		long diff = 0;
		double maxCreditLimit=0;
		Date stmtDate;
		if(lstBankCards.size()==1){
			if(lstBankCards.get(0).get_stmtDate()!=null){
				double tranAmt = helper.getTranAmtSumInCurrentCycle(lstBankCards.get(0).get_id(), lstBankCards.get(0).get_stmtDate());
				lstBankCards.get(0).set_consumedCreditLimit(tranAmt);
			}
			return lstBankCards.get(0).get_id();
		}
		Calendar stmtCal = Calendar.getInstance();
		int cnt = 0;
 		for(BankCardDataBin curCard:lstBankCards){
			stmtDate = curCard.get_stmtDate();
			if(stmtDate !=null){
				stmtCal.setTime(stmtDate);
				stmtCal.set(Calendar.YEAR, curCalendar.get(Calendar.YEAR));
				stmtCal.set(Calendar.MONTH, curCalendar.get(Calendar.MONTH));
				stmtDate = stmtCal.getTime();
				if(stmtDate.after(curDate))
					stmtCal.add(Calendar.MONTH, -1);
				stmtCal.add(Calendar.DAY_OF_MONTH, curCard.get_creditPeriod());
				stmtDate = stmtCal.getTime();
				if(stmtDate.before(curDate))
					diff = curDate.getTime() - stmtDate.getTime();
				else
					diff = stmtDate.getTime() - curDate.getTime();
				
				double tranAmt = helper.getTranAmtSumInCurrentCycle(curCard.get_id(), curCard.get_stmtDate());
				double remainingCreditAmt = curCard.get_creditLimit() - tranAmt;
				lstBankCards.get(cnt).set_consumedCreditLimit(tranAmt);
				cnt++;
				if(diff>=max){
					if(remainingCreditAmt>=maxCreditLimit){
						maxCreditLimit = remainingCreditAmt; 
						goodCardId = curCard.get_id();
					}
					max = diff;
				}
			}
			
		}
		return goodCardId;
	}
	
	public void fillCreditCardList(){
		List<BankCardDataBin> lstBankCards = helper.getBankCardDetails();
		if(lstBankCards.size()>0){
			int goodCard = getBestCardForToday(lstBankCards);
			textViewMessage.setVisibility(View.VISIBLE);
			textviewGoodCardMsg.setVisibility(View.VISIBLE);
			lvMsg.setVisibility(View.VISIBLE);
			imagebuttonSetting.setVisibility(View.VISIBLE);
			BankCardAdapter adapter = new BankCardAdapter(this, lstBankCards,goodCard);
			lvMsg.setAdapter(adapter);
		} else{
			Toast.makeText(this, "We did not find any cards", Toast.LENGTH_SHORT).show();
		}
		
		
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == MODIFYCARD) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
            	fillCreditCardList();
             }
        }
    }
	public void OnScanSMSClicked(){
		if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
			ScanCardTask tsk = new ScanCardTask(this, helper);
			tsk.execute();
		} else{
			ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
		}
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		switch (requestCode) {
			case REQUEST_CODE_ASK_PERMISSIONS:
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					ScanCardTask tsk = new ScanCardTask(this, helper);
					tsk.execute();
				} else {
					Toast.makeText(MainActivity.this, "SMS Permission denied", Toast.LENGTH_SHORT).show();
				}
				break;
			default:
				super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}
	public void OnSettingClicked(View view){
		Intent intent = new Intent(getBaseContext(),AboutSmartCreditUse.class);
		startActivity(intent);
	}
	public void OnViewAnalyticsClick(View view){
		Intent intent = new Intent(getBaseContext(),CardAnalytics.class);
		startActivity(intent);
	}
	
}
