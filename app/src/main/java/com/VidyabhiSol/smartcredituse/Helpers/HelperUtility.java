package com.VidyabhiSol.smartcredituse.Helpers;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.VidyabhiSol.smartcredituse.Data.BankCardDataBin;
import com.VidyabhiSol.smartcredituse.Data.BankDataBin;
import com.VidyabhiSol.smartcredituse.Data.CardPaymentDataBin;
import com.VidyabhiSol.smartcredituse.Data.CardTranDataBin;
import com.VidyabhiSol.smartcredituse.Data.ScannedMessagesDataBin;

import android.content.Context;
import android.graphics.Color;
import android.net.ParseException;
import android.text.format.DateFormat;
//import android.util.Log;

public final class HelperUtility {
	public static final String FLDTXNAMT = "fldTxnAmt";
	public static final String FLDCCMASKEDNO = "fldCCMaskedNo";
	public static final String FLDVENDOR = "fldVendor";
	public static final String FLDDATE = "fldDate";
	public static final String FLDAVLCRLMT = "fldAvlCrLmt";
	public static final String FLDTOTCRLMT = "fldTotCrLmt";
	public static final String BANKCARDLIST = "BankList";
	public static final String PROVIDERLIST = "ProviderList";
	public static final String SELECTEDCARD = "SelectedCard";
	public static final String SELECTEDCARDBANK = "SelectedCardBank";
	public static final String SELECTEDCARDNUMBER = "SelectedCardNumber";
	public static final String SELECTEDBANKID = "SelectedBankId";
	public static final String FROMDATE = "Fromdate";
	public static final String TODATE = "ToDate";
	public static final int CURCYCLECOLOR = Color.rgb(226, 177, 99);
	private static final Map<String, String[]> bankDateFormatMap;
    static
    {
    	bankDateFormatMap = new HashMap<String, String[]>();
    	bankDateFormatMap.put(DBHelper.BANK_ICICI_ADDR, new String[]{"dd-MMM-yy"} );
    	bankDateFormatMap.put(DBHelper.BANK_HDFC_ADDR, new String[]{"yyyy-MM-dd:HH:mm:ss","dd/MM/yyyy"} );
    	bankDateFormatMap.put(DBHelper.BANK_SBI_ADDR, new String[]{"dd MMM yy","dd-MMM-yy"} );
    	bankDateFormatMap.put(DBHelper.BANK_CITYBANK_ADDR, new String[]{"dd/MM/yy","dd-MMM-yy"} );
    	bankDateFormatMap.put(DBHelper.BANK_HSBC_ADDR, new String[]{"dd/MM/yy","dd-MM-yy"} );
    	bankDateFormatMap.put(DBHelper.BANK_AXIS_ADDR, new String[]{"dd-MMM-yy"} );
    	bankDateFormatMap.put(DBHelper.BANK_STANCHART_ADDR, new String[]{"dd/MM/yy"} );
    }
    public static enum AnalyticsGroupByClause{
    	card,
    	vendor,
    	tranDate
    }
    public static Date[] getDateForRange(Date inputDate, int duration){
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
		Date[] returnDates = new Date[2];
		Date curDate = null;
		Date fromDate = null;
		Date toDate = null;
		Calendar stmtCalendar = Calendar.getInstance();
		Calendar curCalendar = Calendar.getInstance();
		stmtCalendar.setTime(inputDate);
		stmtCalendar.set(Calendar.YEAR, curCalendar.get(Calendar.YEAR));
		stmtCalendar.set(Calendar.MONTH, curCalendar.get(Calendar.MONTH));
		
		curDate = Calendar.getInstance().getTime();
		
		if(curDate.after(inputDate)){
			stmtCalendar.add(Calendar.MONTH, 1);
			stmtCalendar.add(Calendar.DAY_OF_MONTH, -1);
			toDate = stmtCalendar.getTime();
		} else{
			stmtCalendar.add(Calendar.DAY_OF_MONTH, -1);
			toDate = stmtCalendar.getTime();
		}
		stmtCalendar.setTime(toDate);
		stmtCalendar.add(Calendar.MONTH, -duration);
		stmtCalendar.add(Calendar.DAY_OF_MONTH, 1);
		fromDate = stmtCalendar.getTime();
			
		returnDates[0] = fromDate;
		returnDates[1] = toDate;
		return returnDates;
	}
	
	public static String ConvertDateFormat(String fromFormat, String toFormat,  String inputDate){
		
		String returnDate = null;
		try { 
			Calendar cal = Calendar.getInstance(); 
			try {
				cal.setTime(new SimpleDateFormat(fromFormat,Locale.getDefault()).parse(inputDate));
			} catch (java.text.ParseException e) {
				
				e.printStackTrace();
			} 
			SimpleDateFormat sdf = new SimpleDateFormat(toFormat,Locale.getDefault());
			returnDate = sdf.format(cal.getTime()); 
			} catch (ParseException e) { 
				e.printStackTrace(); 
			}
			return returnDate;	
	}
	public static String getMonth(int month){
		String returnVal = null;
		switch(month){
		case 1:
			returnVal = "Jan";
			break;
		case 2:
			returnVal = "Feb";
			break;
		case 3:
			returnVal = "Mar";
			break;
		case 4:
			returnVal = "Apr";
			break;
		case 5:
			returnVal = "May";
			break;
		case 6:
			returnVal = "Jun";
			break;
		case 7:
			returnVal = "Jul";
			break;
		case 8:
			returnVal = "Aug";
			break;
		case 9:
			returnVal = "Sep";
			break;
		case 10:
			returnVal = "Oct";
			break;
		case 11:
			returnVal = "Nov";
			break;
		case 12:
			returnVal = "Dec";
			break;
		}
		return returnVal;
	}
	public static String formatDouble(Double d){
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(d);
		
	}
	public static String formatDateForDisplay(Date d){
		if(d!=null)
			return DateFormat.format("dd-MMM-yyyy", d).toString().trim();
		else
			return "";
	}
	public static String formatDateForDB(Date d){
		if(d!=null)
			return DateFormat.format("yyyy-MM-dd", d).toString();
		else
			return "";
	}
	public static Date parseDate(String dateStr, String bankAddr){
		//Log.v("in PArseDate", dateStr + ":" + bankAddr);
		Date returnDate = null;
		SimpleDateFormat format = null; 
		
		String[] arrDateFormat = bankDateFormatMap.get(bankAddr);
		for(int i=0;i<arrDateFormat.length;i++){
			format = new SimpleDateFormat(arrDateFormat[i],Locale.getDefault());
			try {
				returnDate = format.parse(dateStr);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
			
		}
		
//		if(returnDate==null)
//			Log.v("ParseDate date return", "None");
//		else
//			Log.v("ParseDate date return", returnDate.toString());
		return returnDate;
	}
	
	public static void matchMessageUsingRegex(String address, String body,List<BankDataBin> _lstBankList,Context context,int version) {
		
		List<String> _cardList = new ArrayList<String>();
		BankCardDataBin _bankBin;
		final DBHelper _helper = new DBHelper(context);
		int addedCardID=0;
		CardTranDataBin _pBin;
		CardPaymentDataBin _paymentBin;
		//Add the message to be scanned in DB
		_helper.addScannedMessages(new ScannedMessagesDataBin(address, body));
		//Get the supported banks if not supplied
		if(_lstBankList==null){
			_lstBankList = _helper.getSupportedBanks(version);
		}
		_cardList = _helper.getBankCardNumberList();
		
		for(BankDataBin curBank : _lstBankList){
			if(address.contains(curBank.get_smsAddress())){
				if(Pattern.matches(curBank.get_configMsgSignatureRegex().toLowerCase(Locale.getDefault()), body.trim().toLowerCase(Locale.getDefault())))
				{
					Pattern p = Pattern.compile(curBank.get_configfieldExtractRegex(),Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
					Matcher m = p.matcher(body.trim());
					HashMap<String, String> map = new HashMap<String,String>();
					String[] arrField = curBank.get_configFieldsToExtract().split(",");
					String cardID="";
					while(m.find()){
						for(int i=0;i<arrField.length;i++){
							map.put(arrField[i], m.group(i+1));
						}
					}
						if(map.size()>0){
							cardID = map.get(HelperUtility.FLDCCMASKEDNO).toLowerCase(Locale.getDefault());
							cardID = cardID.substring(cardID.length()-4);
							
							
							if(!_cardList.contains(cardID)){
								_bankBin = new BankCardDataBin(curBank.get_id(), cardID, "", null, 50, 0, curBank.get_bankName());
								_helper.addBankCardData(_bankBin);
								_cardList.add(cardID);
							}
							addedCardID = _helper.getBankCardID(cardID);
							
							if(curBank.get_configMsgType().toLowerCase(Locale.getDefault()).contains("transaction")){
								String avlCrdlmt = map.get(HelperUtility.FLDAVLCRLMT);
								if(avlCrdlmt!=null)
									avlCrdlmt = avlCrdlmt.replace(",", "");
								else
									avlCrdlmt = "0.0";
								
								String totCrdlmt = map.get(HelperUtility.FLDTOTCRLMT);
								if(totCrdlmt!=null)
									totCrdlmt = totCrdlmt.replace(",", "");
								else
									totCrdlmt = "0.0";
								
								
								String tranDt = map.get(HelperUtility.FLDDATE);
								
								/*if(tranDt.contains(":")){
									tranDt = tranDt.substring(0,tranDt.indexOf(":"));
									tranDt = HelperUtility.ConvertDateFormat("yyyy-MM-dd","dd-MMM-yy",tranDt );
								}*/
								Date tranDtforDB = parseDate(tranDt,curBank.get_smsAddress()); 
								
								String tranAmt = map.get(HelperUtility.FLDTXNAMT);
								if(tranAmt!=null)
									tranAmt = tranAmt.replace(",", "");
								else
									tranAmt = "0.0";
								
								String tranVender = map.get(HelperUtility.FLDVENDOR);
								
								
								_pBin = new CardTranDataBin(curBank.get_smsAddress(), cardID, Double.parseDouble(tranAmt)
										, tranVender, tranDtforDB, Double.parseDouble(avlCrdlmt), Double.parseDouble(totCrdlmt),addedCardID);
								
								_helper.addCardTransaction(_pBin);
							}
						 else if(curBank.get_configMsgType().toLowerCase(Locale.getDefault()).contains("payment")){
							
								String tranAmt = map.get(HelperUtility.FLDTXNAMT);
								if(tranAmt!=null)
									tranAmt = tranAmt.replace(",", "");
								else
									tranAmt = "0.0";
								
								String tranDt = map.get(HelperUtility.FLDDATE);
								/*
								if(tranDt.contains(":")){
									tranDt = tranDt.substring(0,tranDt.indexOf(":"));
									tranDt = HelperUtility.ConvertDateFormat("yyyy-dd-mm","dd-MMM-yy",tranDt );
								}*/
								Date tranDtforDB = parseDate(tranDt,curBank.get_smsAddress());
								
								_paymentBin = new CardPaymentDataBin(addedCardID, tranDtforDB, cardID, Double.parseDouble(tranAmt), "");
								_helper.addCardPayment(_paymentBin);
								
						 }
					}
					break;
				}
			}
		
	}
		
}
	

}
