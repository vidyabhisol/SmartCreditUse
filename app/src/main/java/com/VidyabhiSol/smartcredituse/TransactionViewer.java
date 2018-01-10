package com.VidyabhiSol.smartcredituse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import com.VidyabhiSol.smartcredituse.Data.BankCardDataBin;
import com.VidyabhiSol.smartcredituse.Data.CardPaymentDataBin;
import com.VidyabhiSol.smartcredituse.Data.CardTranDataBin;
import com.VidyabhiSol.smartcredituse.Data.SMSBankingDataBin;
import com.VidyabhiSol.smartcredituse.Helpers.BankCardPaymentAdapter;
import com.VidyabhiSol.smartcredituse.Helpers.BankCardTranAdapter;
import com.VidyabhiSol.smartcredituse.Helpers.DBHelper;
import com.VidyabhiSol.smartcredituse.Helpers.HelperUtility;
import com.VidyabhiSol.smartcredituse.Helpers.SMSBankingAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class TransactionViewer extends Activity implements
		ActionBar.TabListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v13.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	public int selectedCardId;
	public List<CardTranDataBin> lstTransactions;
	public DBHelper helper;
	public List<CardPaymentDataBin> lstPayments;
	public List<SMSBankingDataBin> lstSMSBanking;
	public Date stmtDate; 
	private AdView mAdView;
	public double curTrans;
	public double creditPeriod;
	public String selectedCardNumber; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_viewer);
		
		mAdView = (AdView) findViewById(R.id.TranView_adView);
		mAdView.loadAd(new AdRequest.Builder().build());
		
		selectedCardId = getIntent().getIntExtra(HelperUtility.SELECTEDCARD, 0);
		String bankName = getIntent().getStringExtra(HelperUtility.SELECTEDCARDBANK); 
		selectedCardNumber = getIntent().getStringExtra(HelperUtility.SELECTEDCARDNUMBER);
		int bankId = getIntent().getIntExtra(HelperUtility.SELECTEDBANKID, 0);
		helper = new DBHelper(this);
		setTitle(bankName + " - " + selectedCardNumber);
		BankCardDataBin bin =helper.getBankCardDetails(selectedCardId); 
		stmtDate = bin.get_stmtDate();
		creditPeriod = bin.get_creditPeriod();
		
		if(stmtDate!=null){
			
			lstTransactions = helper.getCardTransactionsWithCurCycleTran(selectedCardId,stmtDate);
			lstPayments = helper.getCardPayments(selectedCardId);
			curTrans = helper.getTranAmtSumInCurrentCycle(selectedCardId, stmtDate);
			lstSMSBanking = helper.getSMSBanking(bankId);
			// Set up the action bar.
			final ActionBar actionBar = getActionBar();
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			
			// Create the adapter that will return a fragment for each of the three
			// primary sections of the activity.
			mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			
			// When swiping between different sections, select the corresponding
			// tab. We can also use ActionBar.Tab#select() to do this if we have
			// a reference to the Tab.
			mViewPager
					.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
						@Override
						public void onPageSelected(int position) {
							actionBar.setSelectedNavigationItem(position);
						}
					});

			
			// For each of the sections in the app, add a tab to the action bar.
			for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
				// Create a tab with text corresponding to the page title defined by
				// the adapter. Also specify this Activity object, which implements
				// the TabListener interface, as the callback (listener) for when
				// this tab is selected.
				actionBar.addTab(actionBar.newTab()
						.setText(mSectionsPagerAdapter.getPageTitle(i))
						.setTabListener(this));
			}
			

		} else{
			Toast.makeText(this, "Please set the stmt. date by long pressing on the card on main screen and using edit option", Toast.LENGTH_LONG).show();
		}
		
		
		
				
	}
	
	
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}
	

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class
			// below).
			switch(position){
				case 0:
					return new SummaryFragment();
				case 1:
					return new TransactionFragment();	
				case 2:
					return new PaymentFragment();
				case 3:
					return new SMSBanking();	
			}
			 return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.tranviewer_title_sectionSummary).toUpperCase(l);
			case 1:
				return getString(R.string.tranviewer_title_sectionTrans).toUpperCase(l);
			case 2:
				return getString(R.string.tranviewer_title_sectionPayment).toUpperCase(l);	
			case 3:
				return getString(R.string.tranviewer_title_sectionSMSBanking).toUpperCase(l);	
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class SMSBanking extends Fragment{
		public SMSBanking(){
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_sms_banking, container, false);
			TransactionViewer viewer=(TransactionViewer)getActivity(); 
			ListView lstSMS = (ListView) rootView.findViewById(R.id.SMSBanking_listViewSMSBank);
			TextView txtWarning = (TextView) rootView.findViewById(R.id.SMSBanking_textViewDigitWarning);
			int requiredDigits=0;
			if(viewer.lstSMSBanking.size()>0){
				requiredDigits = viewer.lstSMSBanking.get(0).get_requiredDigits();
				if(requiredDigits==6)
					txtWarning.setVisibility(View.VISIBLE);
				else{
					txtWarning.setVisibility(View.INVISIBLE);
				}
			}else{
				txtWarning.setVisibility(View.INVISIBLE);
			}
			String selectedCardNumber = viewer.selectedCardNumber;
			if(requiredDigits==0)
				selectedCardNumber = "";
			SMSBankingAdapter adapter = new SMSBankingAdapter(getActivity()
					, viewer.lstSMSBanking,selectedCardNumber);
			lstSMS.setAdapter(adapter);
			return rootView;
		}
	}
	public static class PaymentFragment extends Fragment{
		public PaymentFragment(){
			
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			TransactionViewer viewer = (TransactionViewer)getActivity();
			View rootView = inflater.inflate(
					R.layout.fragment_card_payment_list, container, false);
			ListView lvTrans = (ListView) rootView.findViewById(R.id.CardTran_listViewCardPayments);
			
			BankCardPaymentAdapter adapter = new BankCardPaymentAdapter(getActivity()
						, viewer.lstPayments);
			lvTrans.setAdapter(adapter);

			return rootView;
		}
		
	}
	public static class SummaryFragment extends Fragment {
		private RelativeLayout byDatelayout;
		private GraphicalView mChart;
		int duration=1;
		TransactionViewer viewer;
		//private RelativeLayout byVendorlayout;
		
		public SummaryFragment() {
			
		}
		public void refreshChart(int inputDuration){
			duration = inputDuration;
			if(mChart!=null)
				byDatelayout.removeView(mChart);
			
			mChart = getChartViewDateWise();
			byDatelayout.addView(mChart);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			viewer = (TransactionViewer)getActivity();
			
			View rootView = inflater.inflate(
					R.layout.fragment_transaction_viewer, container, false);
			byDatelayout = (RelativeLayout) rootView.findViewById(R.id.TranView_relativeLayoutByDate);
			Button btnCurCycle = (Button) rootView.findViewById(R.id.Tranview_ButtonCurCycle);
			Button btnLast3Cycle = (Button) rootView.findViewById(R.id.Tranview_ButtonLast3Cycles);
			Button btnLast6Cycle = (Button) rootView.findViewById(R.id.Tranview_ButtonLast6Cycles);
			btnCurCycle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					refreshChart(1);
				}
			});
			btnLast3Cycle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					refreshChart(3);
				}
			});
			btnLast6Cycle.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					refreshChart(6);
				}
			});
			RelativeLayout vTransLayout = (RelativeLayout) rootView.findViewById(R.id.TranView_RelativeLayoutPayment);
			TextView vTrans = (TextView) vTransLayout.findViewById(R.id.Tranview_textViewPaymentDue);
			TextView vTransLabel = (TextView) vTransLayout.findViewById(R.id.Tranview_textViewPaymentDueLabel);
			TextView vPayDate = (TextView) vTransLayout.findViewById(R.id.Tranview_textViewPaymentDueDate);
			TextView vCurrency = (TextView) vTransLayout.findViewById(R.id.Tranview_textViewCurrency);
			
			vTrans.setText(HelperUtility.formatDouble(viewer.curTrans));
			vTrans.setTextColor(HelperUtility.CURCYCLECOLOR);
			vTransLabel.setTextColor(HelperUtility.CURCYCLECOLOR);
			vCurrency.setTextColor(HelperUtility.CURCYCLECOLOR);
			
			Date stDate = HelperUtility.getDateForRange(viewer.stmtDate,1)[0];
			Calendar c = Calendar.getInstance();
			c.setTime(stDate);
			c.add(Calendar.DAY_OF_MONTH, (int)viewer.creditPeriod);
			stDate = c.getTime();
			vPayDate.setText(HelperUtility.formatDateForDisplay(stDate));
			return rootView;
		}
		
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			mChart = getChartViewDateWise();
			byDatelayout.addView(mChart);
			
			
		}
		@Override
		public void onResume() {
			super.onResume();

		}
		private GraphicalView getChartViewDateWise()
		{
			GraphicalView gv = null;
			// Creating an  XYSeries for Income
	        XYSeries tranSeries = new XYSeries("Transactions");
	        // Creating an  XYSeries for Expense
	        XYSeries paySeries = new XYSeries("Payments");
	       List<CardTranDataBin> tranDataList =  viewer.helper.getDataForDateWiseTransactions(viewer.selectedCardId,viewer.stmtDate,duration);
	       List<CardPaymentDataBin> paymentDataList =  viewer.helper.getDataForDateWisePayments(viewer.selectedCardId,viewer.stmtDate,duration);
	       List<Date> xLabelDates = new ArrayList<Date>();
	        int i=0;
	        double yAxisMax = 0;
	        double tranAmt;
	                
	        for(CardTranDataBin tranBin : tranDataList){
	        	xLabelDates.add(tranBin.get_tranDate());
	        }
	        for(CardPaymentDataBin payBin:paymentDataList){
	        	Date payDate = payBin.get_paymentDate();
				if(!xLabelDates.contains(payDate))
					xLabelDates.add(payDate);
	        }
			Collections.sort(xLabelDates);	
	        
			for(CardTranDataBin tranBinTemp : tranDataList){
				tranAmt =Double.parseDouble( HelperUtility.formatDouble(tranBinTemp.get_tranAmount()));
				tranSeries.add(xLabelDates.indexOf(tranBinTemp.get_tranDate()),tranAmt);
				if(tranAmt>yAxisMax)
					yAxisMax = tranAmt;
			}
			//i=0;
			for(CardPaymentDataBin payBinTemp:paymentDataList){
				tranAmt =Double.parseDouble( HelperUtility.formatDouble(payBinTemp.get_amtPaid()));
				paySeries.add(xLabelDates.indexOf(payBinTemp.get_paymentDate()),tranAmt);
				if(tranAmt>yAxisMax)
					yAxisMax = tranAmt;
			}
			
			
			// Creating a dataset to hold each series
	        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
	        // Adding Income Series to the dataset
	        dataset.addSeries(tranSeries);
	        // Adding Expense Series to dataset
	        dataset.addSeries(paySeries);
	        
	     // Creating XYSeriesRenderer to customize incomeSeries
	        XYSeriesRenderer tranRenderer = new XYSeriesRenderer();
	        tranRenderer.setColor(Color.rgb(130, 130, 230));
	        tranRenderer.setFillPoints(true);
	        tranRenderer.setLineWidth(2);
	        tranRenderer.setDisplayChartValues(true);
	        tranRenderer.setChartValuesTextSize(17);
	        
	 
	        // Creating XYSeriesRenderer to customize expenseSeries
	        XYSeriesRenderer payRenderer = new XYSeriesRenderer();
	        payRenderer.setColor(Color.rgb(220, 80, 80));
	        payRenderer.setFillPoints(true);
	        payRenderer.setLineWidth(2);
	        payRenderer.setDisplayChartValues(true);
	        payRenderer.setChartValuesTextSize(17);
	        
	     // Creating a XYMultipleSeriesRenderer to customize the whole chart
	        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
	        multiRenderer.setXLabels(0);
	        multiRenderer.setChartTitle(getActivity().getString(R.string.Transactionviewer_GraphTitle));
	        multiRenderer.setChartTitleTextSize(20);
	        multiRenderer.setYTitle(getActivity().getString(R.string.Tranviewer_GraphYLabel));
	        multiRenderer.setMargins(new int[]{30, 65, 50, 30});
	        multiRenderer.setAxisTitleTextSize(20);
	        multiRenderer.setZoomEnabled(false, false);
	        multiRenderer.setPanEnabled(false);
	        multiRenderer.setYAxisMin(0);
	        multiRenderer.setYAxisMax(yAxisMax*1.5);
	        multiRenderer.setXAxisMin(-1);
	        multiRenderer.setXAxisMax(xLabelDates.size());
	        multiRenderer.setMarginsColor(Color.WHITE);
	        multiRenderer.setAxesColor(Color.BLACK);
	        multiRenderer.setYLabelsColor(0, Color.BLACK);
	        multiRenderer.setYLabelsAlign(Align.RIGHT);
	        multiRenderer.setXLabelsColor(Color.BLACK);
	        multiRenderer.setLabelsColor(Color.BLACK);
	        multiRenderer.setXLabelsAngle(45);
	        multiRenderer.setXLabelsAlign(Align.LEFT);
	        multiRenderer.setLabelsTextSize(12);
	        multiRenderer.setBarWidth(20);
	        multiRenderer.setBarSpacing(10);
	        
	        for(i=0; i< xLabelDates.size();i++){
	            multiRenderer.addXTextLabel(i, HelperUtility.formatDateForDisplay(xLabelDates.get(i)));
	        }
	        multiRenderer.addSeriesRenderer(tranRenderer);
	        multiRenderer.addSeriesRenderer(payRenderer);

	       if(duration==6 || duration==3)
	    	   gv = ChartFactory.getLineChartView(getActivity(), dataset, multiRenderer);
	       else
	    	   gv = ChartFactory.getBarChartView(getActivity(), dataset, multiRenderer, Type.DEFAULT);
	      
			return gv;
			
		}
			
		
	}
		
	public static class TransactionFragment extends Fragment {
		public TransactionFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			TransactionViewer viewer = (TransactionViewer)getActivity();
			View rootView = inflater.inflate(
					R.layout.fragment_card_transaction_list, container, false);
			
			ListView lvTrans = (ListView) rootView.findViewById(R.id.CardTran_listViewCardTrans);
			
			BankCardTranAdapter adapter = new BankCardTranAdapter(getActivity()
					, viewer.lstTransactions);
			
			lvTrans.setAdapter(adapter);
			return rootView;
		}
		
	}


}
