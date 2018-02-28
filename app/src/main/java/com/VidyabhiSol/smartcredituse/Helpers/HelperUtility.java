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
import android.util.Log;
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
		Date returnDate = new Date();
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
		try{
			List<String> _cardList;
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
			BankDataBin selectedBank=null;
			int maxBankId = 0;
			if(_lstBankList!=null){
				maxBankId = _lstBankList.get(0).get_id();
				for(BankDataBin curBank:_lstBankList){
					if(address.contains(curBank.get_smsAddress())){
						selectedBank = curBank;
					}
					if(curBank.get_id()>maxBankId){
						maxBankId = curBank.get_id();
					}
				}
			}
			//get Current card list
			_cardList = _helper.getBankCardNumberList();
			String cardID="",tranAmount="",tranVender="",avlCrdlmt="0.0",totCrdlmt="0.0",tranDate="",cardBank="";
			String msgType="";
			Pattern amtPat = Pattern.compile("(?i)(?:(?:RS|INR|MRP)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)",Pattern.CASE_INSENSITIVE);
			Pattern amtMerchant = Pattern.compile("(?i)(?:\\sat\\s|in\\*)([A-Za-z0-9]*\\s?-?\\s?[A-Za-z0-9]*\\s?-?\\.?)",Pattern.CASE_INSENSITIVE);
			//Pattern amtDate = Pattern.compile("(?i)(?:\\son\\s|in\\*)(\\d+[\\s-.][a-zA-Z]*[\\s-.]\\d+)|(\\d+-\\d+-\\d+:\\d+:\\d+:\\d+)|(\\d+\\/\\d+\\/\\d+)|(\\d+[.]\\d+[.]\\d+)");
			Pattern amtDate = Pattern.compile("(?i)(\\son\\s)(\\d+[./\\-\\s:][0-9A-Za-z]*[./\\-\\s:]\\d+)",Pattern.CASE_INSENSITIVE);
			Pattern amtCardBank = Pattern.compile("(?i)(?:\\smade on|ur|made a\\s|in\\*)([A-Za-z]*\\s?-?\\s[A-Za-z]*\\s?-?\\s[A-Za-z]*\\s?-?)",Pattern.CASE_INSENSITIVE);
			Pattern amtCard = Pattern.compile("([0-9]*[Xx\\*]*[0-9]*[Xx\\*]+[0-9]{3,})|((ending)[\\s-][-\\d+]*[\\s-])",Pattern.CASE_INSENSITIVE);
			Pattern paymentMsg = Pattern.compile("(payment)[\\w\\s]+(received|recieved)|((received|recieved)[\\w\\s]+(payment))",Pattern.CASE_INSENSITIVE);
			Matcher m = amtPat.matcher(body);
			if(m.find()){
				tranAmount = formatAmount(m.group(0));
			}
			m = amtMerchant.matcher(body);
			if(m.find()){
				tranVender = formatMerchant(m.group(0));
			}
			m = amtCard.matcher(body);
			if(m.find()){
				cardID = m.group(0);
			}
			m = amtDate.matcher(body);
			if(m.find()){
				tranDate = formatTranDate(m.group(0));
			}
			m = paymentMsg.matcher(body);
			if(m.find()){
				msgType = "payment";
			} else{
				msgType = "tran";
			}
			m = amtCardBank.matcher(body);
			if(m.find()){
				cardBank = m.group(0);
			}
			//Bank is not supported then add to supported banks

			if(selectedBank==null){
				selectedBank = new BankDataBin(maxBankId,cardBank,address,0);
				_helper.addSupportedBank(selectedBank);
			}

			if(!_cardList.contains(cardID)){
				_bankBin = new BankCardDataBin(selectedBank.get_id(), cardID, "", null, 50, 0, selectedBank.get_bankName());
				_helper.addBankCardData(_bankBin);
				_cardList.add(cardID);
			}
			addedCardID = _helper.getBankCardID(cardID);

			Date tranDtforDB = parseDate(tranDate,selectedBank.get_smsAddress());

			if(msgType.equalsIgnoreCase("tran")){

				_pBin = new CardTranDataBin(selectedBank.get_smsAddress(), cardID, Double.parseDouble(tranAmount)
						, tranVender, tranDtforDB, Double.parseDouble(avlCrdlmt), Double.parseDouble(totCrdlmt),addedCardID);

				_helper.addCardTransaction(_pBin);

			} else if(msgType.equalsIgnoreCase("payment")){
				_paymentBin = new CardPaymentDataBin(addedCardID, tranDtforDB, cardID, Double.parseDouble(tranAmount), "");
				_helper.addCardPayment(_paymentBin);
			}
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private static String formatMerchant(String merchant){
		String strMerchant="";
		strMerchant = merchant.toLowerCase();
		strMerchant = strMerchant.replaceFirst("(at)\\s","");
		strMerchant = strMerchant.replaceFirst("\\s(on)","");
		strMerchant = merchant.trim();
		return strMerchant;
	}
	private  static String formatTranDate(String tranDate){
		String formattedDate = "";
		formattedDate = tranDate.toLowerCase();
		formattedDate = formattedDate.replaceFirst("(on)\\s","");
		formattedDate = formattedDate.trim();
		return formattedDate;
	}
	private static String formatAmount(String amt){
		String formattedAmt = "";
		try{
			amt = amt.toLowerCase();
			formattedAmt = amt.replaceAll("[^\\d+(\\.\\d{1,2})?]", "");
			formattedAmt = formattedAmt.replace(",","");
			if(formattedAmt.substring(0,1).equalsIgnoreCase(".")){
				formattedAmt = formattedAmt.substring(1);
			}
			formattedAmt = formattedAmt.trim();
			return formattedAmt;
		} catch(Exception ex){
			ex.printStackTrace();
			return "";
		}
	}
	

}
