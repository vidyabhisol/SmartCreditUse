package com.VidyabhiSol.smartcredituse.Helpers;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.VidyabhiSol.smartcredituse.Data.BankCardDataBin;
import com.VidyabhiSol.smartcredituse.Data.BankDataBin;
import com.VidyabhiSol.smartcredituse.Data.CardAnalyticsDataBin;
import com.VidyabhiSol.smartcredituse.Data.CardPaymentDataBin;
import com.VidyabhiSol.smartcredituse.Data.CardTranDataBin;
import com.VidyabhiSol.smartcredituse.Data.SMSBankingDataBin;
import com.VidyabhiSol.smartcredituse.Data.ScannedMessagesDataBin;
import com.VidyabhiSol.smartcredituse.Helpers.DataContract.Table_BankCardMaster;
import com.VidyabhiSol.smartcredituse.Helpers.DataContract.Table_BankCardMsgConfig;
import com.VidyabhiSol.smartcredituse.Helpers.DataContract.Table_BankMaster;
import com.VidyabhiSol.smartcredituse.Helpers.DataContract.Table_CardPayments;
import com.VidyabhiSol.smartcredituse.Helpers.DataContract.Table_CardTransactions;
import com.VidyabhiSol.smartcredituse.Helpers.DataContract.Table_SMSBanking;
import com.VidyabhiSol.smartcredituse.Helpers.DataContract.Table_ScannedMessages;
import com.VidyabhiSol.smartcredituse.Helpers.HelperUtility.AnalyticsGroupByClause;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
	
	public static final int DATABASE_VERSION = 4;
	private static Context _context;
    public static final String DATABASE_NAME = "SmartCardDatabase.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String NUM_TYPE = " NUMERIC";
    private static final String DATE_TYPE = " DATETIME";
    
    private static final String BANK_ICICI = "ICICI";
    private static final String BANK_HDFC = "HDFC";
    private static final String BANK_SBI = "SBI";
    private static final String BANK_CITYBANK = "CITYBANK";
    private static final String BANK_HSBC = "HSBC";
    private static final String BANK_AXIS = "AXIS";
    private static final String BANK_STANCHART = "Stanchart";
    
    public static final String BANK_ICICI_ID = "1";
    public static final String BANK_HDFC_ID = "2";
    public static final String BANK_SBI_ID = "3";
    public static final String BANK_CITYBANK_ID = "4";
    public static final String BANK_HSBC_ID = "5";
    public static final String BANK_AXIS_ID = "6";
    public static final String BANK_STANCHART_ID = "7";
    
    public static final String BANK_ICICI_ADDR = "ICICI";
    public static final String BANK_HDFC_ADDR = "HDFC";
    public static final String BANK_SBI_ADDR = "SBI";
    public static final String BANK_CITYBANK_ADDR = "City";
    public static final String BANK_HSBC_ADDR = "HSBC";
    public static final String BANK_AXIS_ADDR = "Axis";
    public static final String BANK_STANCHART_ADDR = "SC";
    
    //public static final String BANK_ICICI_ADDR = "111";
    //public static final String BANK_HDFC_ADDR = "222";
    //public static final String BANK_SBI_ADDR = "333";
//    public static final String BANK_CITYBANK_ADDR = "444";
//    public static final String BANK_HSBC_ADDR = "555";
//    public static final String BANK_AXIS_ADDR = "666";    
//    public static final String BANK_STANCHART_ADDR = "777";
    
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
    
    SQLiteDatabase db;
    
    private static final String SQL_CREATE_SCANNEDMESSAGES_V2 =
            "CREATE TABLE " + Table_ScannedMessages.TABLE_SCANNEDMESSAGES + " (" +
            		Table_ScannedMessages._ID + INT_TYPE + " PRIMARY KEY," +
            		Table_ScannedMessages.COLUMN_SMSADDR + TEXT_TYPE + COMMA_SEP +
    		        Table_ScannedMessages.COLUMN_SMSMSG + TEXT_TYPE +
            " )";
    
    private static final String SQL_CREATE_BANKMASTER =
            "CREATE TABLE " + Table_BankMaster.TABLE_BANKMASTER + " (" +
            		Table_BankMaster._ID + INT_TYPE + " PRIMARY KEY," +
            		Table_BankMaster.COLUMN_BANKNAME + TEXT_TYPE + COMMA_SEP +
    		        Table_BankMaster.COLUMN_SMSADDRESS + TEXT_TYPE + COMMA_SEP +
    		        Table_BankMaster.COLUMN_ISDELETED + INT_TYPE + " DEFAULT 0 " +   
    		        
            " )";
    
    private static final String SQL_CREATE_BANKCARDMSGCONFIG =
            "CREATE TABLE " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + " (" +
            		Table_BankCardMsgConfig._ID + INT_TYPE + " PRIMARY KEY," +
            		Table_BankCardMsgConfig.COLUMN_BANKID + INT_TYPE  + COMMA_SEP +
    		        Table_BankCardMsgConfig.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
    		        Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + TEXT_TYPE + COMMA_SEP +
    		        Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + TEXT_TYPE + COMMA_SEP +
    		        Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + TEXT_TYPE + COMMA_SEP +
    		        Table_BankCardMsgConfig.COLUMN_MSGTYPE+ TEXT_TYPE + COMMA_SEP +
    		        Table_BankCardMsgConfig.COLUMN_ISDELETED + INT_TYPE + " DEFAULT 0 " +   
    		        
            " )";
    
    private static final String SQL_CREATE_BANKCARDMASTER = 
    		"CREATE TABLE " + Table_BankCardMaster.TABLE_BANKCARDMASTER + " (" +
    				Table_BankCardMaster._ID + INT_TYPE + " PRIMARY KEY," +
    				Table_BankCardMaster.COLUMN_BANKID + INT_TYPE + COMMA_SEP +
    		        Table_BankCardMaster.COLUMN_CARDNUMBER + TEXT_TYPE + COMMA_SEP +
    		        Table_BankCardMaster.COLUMN_CARDTYPE + TEXT_TYPE + COMMA_SEP +
    				Table_BankCardMaster.COLUMN_STMTDATE + DATE_TYPE + COMMA_SEP +
    				Table_BankCardMaster.COLUMN_CREDITPERIOD + INT_TYPE + COMMA_SEP +
    				Table_BankCardMaster.COLUMN_CREDITLIMIT + NUM_TYPE + COMMA_SEP +
    				Table_BankCardMaster.COLUMN_ISDELETED + INT_TYPE + " DEFAULT 0 " +   
    		        
            " )";
    
    private static final String SQL_CREATE_CARDTRANSACTIONS = 
    		"CREATE TABLE " + Table_CardTransactions.TABLE_CARDTRANSACTIONS + " (" +
    				Table_CardTransactions._ID + INT_TYPE + " PRIMARY KEY," +
    				Table_CardTransactions.COLUMN_AVAILCREDITLIMIT + NUM_TYPE + COMMA_SEP +
    				Table_CardTransactions.COLUMN_CARDID + INT_TYPE  + COMMA_SEP +
    				Table_CardTransactions.COLUMN_TOTALCREDITLIMIT + NUM_TYPE  + COMMA_SEP +
    				Table_CardTransactions.COLUMN_TRANAMOUNT + NUM_TYPE  + COMMA_SEP +
    				Table_CardTransactions.COLUMN_TRANDATE + DATE_TYPE  + COMMA_SEP +
    				Table_CardTransactions.COLUMN_TRANVENDOR + TEXT_TYPE + COMMA_SEP +
    				Table_CardTransactions.COLUMN_ISDELETED + INT_TYPE + " DEFAULT 0 " +   
    		        
            " )";
    
    private static final String SQL_CREATE_CARDPAYMENTS = 
    		"CREATE TABLE " + Table_CardPayments.TABLE_CARDPAYMENTS + " (" +
    				Table_CardPayments._ID + INT_TYPE + " PRIMARY KEY," +
    				Table_CardPayments.COLUMN_AMTPAID + NUM_TYPE + COMMA_SEP +
    				Table_CardPayments.COLUMN_CARDID + INT_TYPE  + COMMA_SEP +
    				Table_CardPayments.COLUMN_DATEPAID + DATE_TYPE  + COMMA_SEP +
    				Table_CardPayments.COLUMN_PAYMENTMODE + TEXT_TYPE  + COMMA_SEP +
    				Table_CardPayments.COLUMN_ISDELETED + INT_TYPE + " DEFAULT 0 " +   
    		        
            " )";
    
    private static final String SQL_CREATE_SMSBANKING_V3 = 
    		"CREATE TABLE " + Table_SMSBanking.TABLE_SMSBANKING + " (" +
    				Table_SMSBanking._ID + INT_TYPE + " PRIMARY KEY," +
    				Table_SMSBanking.COLUMN_BANKID + INT_TYPE + COMMA_SEP +
    				Table_SMSBanking.COLUMN_COMMAND + TEXT_TYPE  + COMMA_SEP +
    				Table_SMSBanking.COLUMN_OPERATION + TEXT_TYPE  + COMMA_SEP +
    				Table_SMSBanking.COLUMN_REQUIREDDIGITS + INT_TYPE  + COMMA_SEP +
    				Table_SMSBanking.COLUMN_SMSNUMBER + TEXT_TYPE  + COMMA_SEP +
    				Table_SMSBanking.COLUMN_ISDELETED + INT_TYPE + " DEFAULT 0 " +   
    		        
            " )";
    
    private static final String SQL_DELETE_BANKMASTER =
            "DROP TABLE IF EXISTS " + Table_BankMaster.TABLE_BANKMASTER;
    
    private static final String SQL_DELETE_BANKCARDMASTER =
            "DROP TABLE IF EXISTS " + Table_BankCardMaster.TABLE_BANKCARDMASTER;
    
    private static final String SQL_DELETE_CARDTRANSACTIONS =
            "DROP TABLE IF EXISTS " + Table_CardTransactions.TABLE_CARDTRANSACTIONS;
    private static final String SQL_DELETE_BANKCARDMSGCONFIG =
            "DROP TABLE IF EXISTS " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG;
    private static final String SQL_DELETE_CARDPAYMENTS =
            "DROP TABLE IF EXISTS " + Table_CardPayments.TABLE_CARDPAYMENTS;
    
    private static final String SQL_DELETE_SCANNEDMESSAGES_V2 =
            "DROP TABLE IF EXISTS " + Table_ScannedMessages.TABLE_SCANNEDMESSAGES;
    
    private static final String SQL_DELETE_SMSBANKING_V3 =
            "DROP TABLE IF EXISTS " + Table_SMSBanking.TABLE_SMSBANKING;
    
    private void DeleteTables(SQLiteDatabase db){
		   db.execSQL(SQL_DELETE_BANKMASTER);
	       db.execSQL(SQL_DELETE_BANKCARDMASTER);
	       db.execSQL(SQL_DELETE_CARDTRANSACTIONS);
	       db.execSQL(SQL_DELETE_BANKCARDMSGCONFIG);
	       db.execSQL(SQL_DELETE_CARDPAYMENTS);
	       db.execSQL(SQL_DELETE_SCANNEDMESSAGES_V2);
	       db.execSQL(SQL_DELETE_SMSBANKING_V3);
	}
    private void insertSMSBanking_V3(SQLiteDatabase db){
    	db.execSQL(SQL_CREATE_SMSBANKING_V3);
    	String sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_ICICI_ID + ",'IBALCC','Balance Enquiry',6,'5676766',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_ICICI_ID + ",'IRPCC','Reward Points',6,'5676766',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_ICICI_ID + ",'ILPCC','Last Payment',6,'5676766',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_ICICI_ID + ",'IPDDCC','Payment Due Date',6,'5676766',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_SBI_ID + ",'BAL','Balance Enquiry',4,'5676791',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_SBI_ID + ",'AVAIL','Available Credit & Cash Limit',4,'5676791',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_SBI_ID + ",'BLOCK','Hotlist Lost/Stolen Card',4,'5676791',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_SBI_ID + ",'PAYMENT','Last Payment Status',4,'5676791',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_SBI_ID + ",'REWARD','Reward Point Summary',4,'5676791',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_SBI_ID + ",'ESTMT','Subscribe to E-Statement',4,'5676791',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_SBI_ID + ",'OTP','Request for One time Password (OTP)',4,'5676791',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_CITYBANK_ID + ",'STMT','Total Amount Due, Minimum Amount Due and Payment Due Date',4,'52484',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_CITYBANK_ID + ",'CARDBAL','Credit Card Outstanding with Available Credit Limit',4,'52484',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_CITYBANK_ID + ",'REWARDS','Reward Points',4,'52484',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_CITYBANK_ID + ",'OTP','One time Password for your Payment Gateway transactions',4,'52484',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_HSBC_ID + ",'BLOCK','Block the lost card',4,'575750',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_HDFC_ID + ",'CCINFO','Check Credit Card account online',0,'5676712',0)";
    	db.execSQL(sqlStmt);
    	

    			
    }
    private void insertICICIBankMSGConfig(SQLiteDatabase db){
    	String sqlStmt = "INSERT into " + Table_BankMaster.TABLE_BANKMASTER + "( " + Table_BankMaster.COLUMN_BANKNAME + 
    			"," + Table_BankMaster.COLUMN_SMSADDRESS + ")" + " VALUES " + "('" + BANK_ICICI + "','" + BANK_ICICI_ADDR + "')";
    	
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
				+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
			    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGTYPE 
				+ ")" + " VALUES " + "(" + BANK_ICICI_ID + "," 
			    + "'Extractor for ICICI Credit Card usage alert sms'" + ","
				+ "'Tranx of INR ([\\d,.]*) using Credit Card ([0-9a-z]*) is made at ([\\sa-zA-Z0-9\\-\\*\\_\\.]*) on (\\d+-[a-zA-Z]*-\\d+). Avbl Cr lmt:INR ([\\d,.]*) Total Cr lmt: INR ([\\d,.]*).*'" + ","
				+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDVENDOR + "," + HelperUtility.FLDDATE + "," + HelperUtility.FLDAVLCRLMT+ "," + HelperUtility.FLDTOTCRLMT +"'" + ","
				+ "'Tranx of INR ([\\d,.]*) using Credit Card ([0-9a-z]*) is made at.*'" + ","
				+ "'ICICITransaction'"
				+ ")";
	db.execSQL(sqlStmt);
	
	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
			+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
		    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
		    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
		    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
		    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
		    + Table_BankCardMsgConfig.COLUMN_MSGTYPE 
			+ ")" + " VALUES " + "(" + BANK_ICICI_ID + "," 
		    + "'Extractor for ICICI Credit Card payment alert sms'" + ","
			+ "'Dear Customer,Payment of INR ([\\d,.]*) towards Credit Card ([0-9a-zA-Z]*) has been received by cheque (\\d+) on (\\d+-[a-zA-z]*-\\d+),subject.*'" + ","
			+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDVENDOR +"," + HelperUtility.FLDDATE +"'" + ","
			+ "'Dear customer,payment of INR ([\\d,.]*) towards Credit Card ([0-9a-zA-Z]*) has been received by cheque.*'" + ","
			+ "'ICICIPaymentCheque'"
			+ ")";
	db.execSQL(sqlStmt);
	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
			+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
		    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
		    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
		    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
		    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
		    + Table_BankCardMsgConfig.COLUMN_MSGTYPE 
			+ ")" + " VALUES " + "(" + BANK_ICICI_ID + "," 
		    + "'Extractor for ICICI Credit Card payment alert sms'" + ","
			+ "'Dear customer, payment of INR ([\\d,.]*) towards credit card ([0-9a-zA-Z]*) has been received by cheque (\\d+) on (\\d+-[a-zA-Z]*-\\d+), subject.*'" + ","
			+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDVENDOR + ","+ HelperUtility.FLDDATE +"'" + ","
			+ "'Dear customer, payment of INR ([\\d,.]*) towards Credit Card ([0-9a-zA-Z]*) has been received by cheque.*'" + ","
			+ "'ICICIPaymentChequeWithSpace'"
			+ ")";
	db.execSQL(sqlStmt);
	
	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
			+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
		    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
		    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
		    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
		    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
		    + Table_BankCardMsgConfig.COLUMN_MSGTYPE 
			+ ")" + " VALUES " + "(" + BANK_ICICI_ID + "," 
		    + "'Extractor for ICICI Credit Card payment alert sms'" + ","
			+ "'Dear customer, payment of INR ([\\d,.]*) has been received on your ICICI Bank Credit Card Account ([0-9a-zA-Z]*) on (\\d+-[a-zA-Z]*-\\d+). Thank You.*'" + ","
			+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDDATE +"'" + ","
			+ "'Dear customer, payment of INR ([\\d,.]*) has been received on your ICICI Bank Credit Card Account.*'" + ","
			+ "'ICICIPaymentOnline'"
			+ ")";
	db.execSQL(sqlStmt);
	
	
    }
    private void alterTableBankMsgConfig_V3(SQLiteDatabase db){
    	String sqlStmt = "ALTER TABLE " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + 
    			" ADD " + Table_BankCardMsgConfig.COLUMN_ADDDEDINVERSION + INT_TYPE + " DEFAULT 2";
    	db.execSQL(sqlStmt);
    }
    private void insertHDFCBankMSGConfig_V3(SQLiteDatabase db){
    	String sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
				+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
			    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGTYPE + ","
			    + Table_BankCardMsgConfig.COLUMN_ADDDEDINVERSION
				+ ")" + " VALUES " + "(" + BANK_HDFC_ID + "," 
			    + "'Extractor for HDFC Credit Card payment alert sms'" + ","
				+ "'Dear HDFC Bank cardmember Online Payment of Rs.([\\d,.]*) vide Ref# ([\\sa-zA-Z0-9\\-\\*\\_\\.]*) was credited to your card ending -([0-9a-zA-Z]*)-ON-([\\d+/\\d+/\\d+]*)_value Date -([\\d+/\\d+/\\d+]*).*'" + ","
				+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDVENDOR + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDDATE  +"'" + ","
				+ "'Dear HDFC Bank cardmember Online Payment of Rs.([\\d,.]*).*'" + ","
				+ "'HDFCPayment'" + "," + "3" 
				+ ")";
    	db.execSQL(sqlStmt);
    }
    private void insertHDFCBankMSGConfig(SQLiteDatabase db){
    	String sqlStmt = "INSERT into " + Table_BankMaster.TABLE_BANKMASTER + "( " + Table_BankMaster.COLUMN_BANKNAME + 
    			"," + Table_BankMaster.COLUMN_SMSADDRESS + ")" + " VALUES " + "('" + BANK_HDFC + "','" + BANK_HDFC_ADDR + "')";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
				+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
			    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGTYPE 
				+ ")" + " VALUES " + "(" + BANK_HDFC_ID + "," 
			    + "'Extractor for HDFC Credit Card usage alert sms'" + ","
				+ "'Rs.([\\d,.]*) was spent on ur HDFCBank CREDIT Card ending ([0-9a-zA-Z]*) on (\\d+-\\d+-\\d+:\\d+:\\d+:\\d+) at ([\\sa-zA-Z0-9\\-\\*\\_\\.]*).Avl bal - Rs.([\\d,.]*) curr o/s - Rs.([\\d,.]*).*'" + ","
				+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDDATE + "," + HelperUtility.FLDVENDOR + "," + HelperUtility.FLDAVLCRLMT +"'" + ","
				+ "'Rs.([\\d,.]*) was spent on ur HDFCBank CREDIT Card.*'" + ","
				+ "'HDFCTransaction'"
				+ ")";
    	db.execSQL(sqlStmt);
    	
    	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
				+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
			    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGTYPE 
				+ ")" + " VALUES " + "(" + BANK_HDFC_ID + "," 
			    + "'Extractor for HDFC Credit Card payment alert sms'" + ","
				+ "'Dear HDFC Bank cardmember, Online Payment of Rs.([\\d,.]*) vide Ref# ([\\sa-zA-Z0-9\\-\\*\\_\\.]*) was credited to your card ending -([0-9a-zA-Z]*)-ON-([\\d+/\\d+/\\d+]*)_value Date -([\\d+/\\d+/\\d+]*).*'" + ","
				+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDVENDOR + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDDATE  +"'" + ","
				+ "'Dear HDFC Bank cardmember, Online Payment of Rs.([\\d,.]*).*'" + ","
				+ "'HDFCPayment'"
				+ ")";
    	db.execSQL(sqlStmt);
    	
    	
    }
    private void insertSBIBankMSGConfig(SQLiteDatabase db){
    	String sqlStmt = "INSERT into " + Table_BankMaster.TABLE_BANKMASTER + "( " + Table_BankMaster.COLUMN_BANKNAME + 
    			"," + Table_BankMaster.COLUMN_SMSADDRESS + ")" + " VALUES " + "('" + BANK_SBI + "','" + BANK_SBI_ADDR + "')";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
				+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
			    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGTYPE 
				+ ")" + " VALUES " + "(" + BANK_SBI_ID + ","
			    + "'Extractor for SBI Credit Card usage alert sms'" + ","
				+ "'Transaction of Rs.([\\d,.]*) made on SBI Credit Card ([0-9a-zA-Z]*) at ([\\sa-zA-Z0-9\\-\\*\\_\\.]*) on (\\d+\\s[a-zA-Z]*\\s\\d+).*'" + ","
				+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDVENDOR + "," + HelperUtility.FLDDATE +"'" + ","
				+ "'Transaction of Rs.([\\d,.]*) made on SBI Credit Card ([0-9a-zA-Z]*).*'" + ","
				+ "'SBITransaction'"
				+ ")";
    	db.execSQL(sqlStmt);
    	
    	
    	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
				+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
			    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGTYPE 
				+ ")" + " VALUES " + "(" + BANK_SBI_ID + ","
			    + "'Extractor for SBI Credit Card payment alert sms'" + ","
				+ "'We have received payment of Rs. ([\\d,.]*) via ([\\sa-zA-Z0-9\\-\\*\\_\\.]*) and same has been credited to your SBI Card no ([0-9a-zA-Z]*) on (\\d+-[a-zA-Z]*-\\d+).*'" + ","
				+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDVENDOR + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDDATE +"'" + ","
				+ "'We have received payment of Rs. ([\\d,.]*) via ([\\sa-zA-Z0-9\\-\\*\\_\\.]*) and same has been credited to your SBI.*'" + ","
				+ "'SBIPayment'"
				+ ")";
    	db.execSQL(sqlStmt);
    	
    	
    }
    private void insertCITYBankMSGConfig(SQLiteDatabase db){
    	String sqlStmt = "INSERT into " + Table_BankMaster.TABLE_BANKMASTER + "( " + Table_BankMaster.COLUMN_BANKNAME + 
    			"," + Table_BankMaster.COLUMN_SMSADDRESS + ")" + " VALUES " + "('" + BANK_CITYBANK + "','" + BANK_CITYBANK_ADDR +"')";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
				+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
			    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGTYPE 
				+ ")" + " VALUES " + "(" + BANK_CITYBANK_ID + ","
			    + "'Extractor for City Bank Credit Card Transaction alert sms'" + ","
				+ "'Rs ([\\d,.]*) was spent on your Credit Card ([0-9a-zA-Z]*) on (\\d+-[a-zA-Z]*-\\d+) at ([\\sa-zA-Z0-9\\-\\*\\_\\.\\\\\\u003f]*).*'" + ","
				+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDDATE  + "," + HelperUtility.FLDVENDOR   +"'" + ","
				+ "'Rs ([\\d,.]*) was spent on your Credit Card ([0-9a-zA-Z]*) on (\\d+-[a-zA-Z]*-\\d+) at.*'" + ","
				+ "'CITYTransaction'"
				+ ")";
    	db.execSQL(sqlStmt);
    	
    	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
				+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
			    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGTYPE 
				+ ")" + " VALUES " + "(" + BANK_CITYBANK_ID + ","
			    + "'Extractor for City Bank Credit Card Payment alert sms'" + ","
				+ "'Payment received for amount Rs ([\\d,.]*) for Credit card No. ([0-9a-zA-Z\\*]*) on ([\\d+/\\d+/\\d+]*).*'" + ","
				+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDDATE  + "'" + ","
				+ "'Payment received for amount Rs ([\\d,.]*) for Credit card No. ([0-9a-zA-Z\\*]*) on.*'" + ","
				+ "'CITYPayment'"
				+ ")";
    	db.execSQL(sqlStmt);
    	
    
    }
    
    private void insertHSBCBankMSGConfig(SQLiteDatabase db){
    	String sqlStmt = "INSERT into " + Table_BankMaster.TABLE_BANKMASTER + "( " + Table_BankMaster.COLUMN_BANKNAME + 
    			"," + Table_BankMaster.COLUMN_SMSADDRESS + ")" + " VALUES " + "('" + BANK_HSBC + "','" + BANK_HSBC_ADDR + "')";
    	db.execSQL(sqlStmt);
    	
    	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
				+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
			    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGTYPE 
				+ ")" + " VALUES " + "(" + BANK_HSBC_ID + ","
			    + "'Extractor for HSBC Bank Credit Card Transaction alert sms'" + ","
				+ "'Your credit card ([0-9a-zA-Z]*) is used for INR ([\\d,.]*) on ([\\d+/\\d+/\\d+]*) at ([\\sa-zA-Z0-9\\-\\*\\_\\.\\\\\\u003f]*). Call.*'" + ","
				+ "'" + HelperUtility.FLDCCMASKEDNO  + "," +  HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDDATE + "," +  HelperUtility.FLDVENDOR +"'" + ","
				+ "'Your credit card ([0-9a-zA-Z]*) is used for INR ([\\d,.]*).*'" + ","
				+ "'HSBCTransaction'"
				+ ")";
    	db.execSQL(sqlStmt);
    	
    	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
				+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
			    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGTYPE 
				+ ")" + " VALUES " + "(" + BANK_HSBC_ID + ","
			    + "'Extractor for HSBC Credit Card Payment alert sms'" + ","
				+ "'Dear Customer, we have received a payment of Rs.([\\d,.]*) for credit card ending ([0-9a-zA-Z]*) on ([\\d+\\-\\d+\\-\\d+]*). Thank.*'" + ","
				+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDDATE  + "'" + ","
				+ "'Dear Customer, we have received a payment of Rs.([\\d,.]*) for credit card ending ([0-9a-zA-Z]*).*'" + ","
				+ "'HSBCPayment'"
				+ ")";
    	db.execSQL(sqlStmt);
    	
    }

    
    private void insertStanChartBankMSGConfig_V4(SQLiteDatabase db){
    	String sqlStmt = "INSERT into " + Table_BankMaster.TABLE_BANKMASTER + "( " + Table_BankMaster.COLUMN_BANKNAME + 
    			"," + Table_BankMaster.COLUMN_SMSADDRESS + ")" + " VALUES " + "('" + BANK_STANCHART + "','" + BANK_STANCHART_ADDR + "')";
    	db.execSQL(sqlStmt);
    	
    	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
				+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
			    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGTYPE + ","
			    + Table_BankCardMsgConfig.COLUMN_ADDDEDINVERSION
				+ ")" + " VALUES " + "(" + BANK_STANCHART_ID + "," 
			    + "'Extractor for StanChart Bank Credit Card Transaction alert sms'" + ","
				+ "'Thank you for using StanChart Credit card no. ending ([0-9a-zA-Z]*) on ([\\d+/\\d+/\\d+]*) for INR ([\\d,.]*). Call.*'" + ","
				+ "'" + HelperUtility.FLDCCMASKEDNO  + "," + HelperUtility.FLDDATE + "," +  HelperUtility.FLDTXNAMT +"'" + ","
				+ "'Thank you for using StanChart Credit card no. ending ([0-9a-zA-Z]*) on.*'" + ","
				+ "'StanChartTransaction'" + ",4"
				+ ")";
    	db.execSQL(sqlStmt);
    	
    }

    private void insertSupportedBanks(SQLiteDatabase db){
    		insertICICIBankMSGConfig(db);
    		insertHDFCBankMSGConfig(db);
    		insertSBIBankMSGConfig(db);
    		insertCITYBankMSGConfig(db);
    		insertHSBCBankMSGConfig(db);
    		//insertStanChartBankMSGConfig(db);
    }
    
    private void insertAXISBankMSGConfig_V4(SQLiteDatabase db){
    	String sqlStmt = "INSERT into " + Table_BankMaster.TABLE_BANKMASTER + "( " + Table_BankMaster.COLUMN_BANKNAME + 
    			"," + Table_BankMaster.COLUMN_SMSADDRESS +  ")" + " VALUES " + "('" + BANK_AXIS + "','" + BANK_AXIS_ADDR + "')";
    	
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
				+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
			    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
			    + Table_BankCardMsgConfig.COLUMN_MSGTYPE + ","
			    + Table_BankCardMsgConfig.COLUMN_ADDDEDINVERSION
				+ ")" + " VALUES " + "(" + BANK_AXIS_ID + "," 
			    + "'Extractor for ICICI Credit Card usage alert sms'" + ","
				+ "'Dear Customer, Rs.([\\d,.]*) was spent on your Credit Card ([0-9a-zA-Z]*) on (\\d+-[a-zA-Z]*-\\d+) at ([\\sa-zA-Z0-9\\-\\*\\_\\.\\\\\\u003f]*). Avbl.*'" + ","
				+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDDATE + "," + HelperUtility.FLDVENDOR + "'" + ","
				+ "'Dear Customer, Rs.([\\d,.]*) was spent on your Credit Card ([0-9a-zA-Z]*).*'" + ","
				+ "'AXISTransaction'" + ",4" 
				+ ")";
	db.execSQL(sqlStmt);
	sqlStmt = "INSERT into " + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "( " 
			+ Table_BankCardMsgConfig.COLUMN_BANKID + "," 
		    + Table_BankCardMsgConfig.COLUMN_DESCRIPTION + ","
		    + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX + ","
		    + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT + ","
		    + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX + ","
		    + Table_BankCardMsgConfig.COLUMN_MSGTYPE + ","
		    + Table_BankCardMsgConfig.COLUMN_ADDDEDINVERSION 
			+ ")" + " VALUES " + "(" + BANK_AXIS_ID + "," 
		    + "'Extractor for AXIS Credit Card payment alert sms'" + ","
			+ "'Dear Customer, Payment received for amount of Rs ([\\d,.]*) for creditcard number ([0-9a-zA-Z]*) on (\\d+-[a-zA-Z]*-\\d+).*'" + ","
			+ "'" + HelperUtility.FLDTXNAMT + "," + HelperUtility.FLDCCMASKEDNO + "," + HelperUtility.FLDDATE +"'" + ","
			+ "'Dear Customer, Payment received for amount of Rs ([\\d,.]*).*'" + ","
			+ "'AXISPaymentOnline'" + ",4" 
			+ ")";
	db.execSQL(sqlStmt);
	
    }
    
    private void insertAXISSMSBanking_V4(SQLiteDatabase db){
    	String sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_AXIS_ID + ",'LASTPAY','Last payment amount & date',4,'5676782',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_AXIS_ID + ",'REWBAL','PlusPoint balance',4,'5676782',0)";
    	db.execSQL(sqlStmt);
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_AXIS_ID + ",'CARDBAL','Credit card outstanding balance',4,'5676782',0)";
    	db.execSQL(sqlStmt);
    	
    	sqlStmt = "INSERT into " + Table_SMSBanking.TABLE_SMSBANKING 
    			+ "( " + Table_SMSBanking.COLUMN_BANKID 
    			+ "," + Table_SMSBanking.COLUMN_COMMAND 
    			+ "," + Table_SMSBanking.COLUMN_OPERATION
    			+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
    			+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
    			+ "," + Table_SMSBanking.COLUMN_ISDELETED
    			+ ")" 
    			+ " VALUES " + "(" + BANK_AXIS_ID + ",'AVAILBAL','Available balance',4,'5676782',0)";
    	db.execSQL(sqlStmt);
    	
    	
    }
	public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        _context = context;
    }
	
	@Override
	public void onCreate(SQLiteDatabase arg0) {
		arg0.execSQL(SQL_CREATE_BANKMASTER);
		arg0.execSQL(SQL_CREATE_BANKCARDMASTER);
		arg0.execSQL(SQL_CREATE_CARDTRANSACTIONS);
		arg0.execSQL(SQL_CREATE_BANKCARDMSGCONFIG);
		arg0.execSQL(SQL_CREATE_CARDPAYMENTS);
		arg0.execSQL(SQL_CREATE_SCANNEDMESSAGES_V2);
		alterTableBankMsgConfig_V3(arg0);
		insertSupportedBanks(arg0);
		insertSMSBanking_V3(arg0);
		insertHDFCBankMSGConfig_V3(arg0);
		insertAXISBankMSGConfig_V4(arg0);
		insertAXISSMSBanking_V4(arg0);
		insertStanChartBankMSGConfig_V4(arg0);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		int upgradeTo = oldVersion + 1;
		Boolean upgradeFromTwo = false;
		while (upgradeTo <= newVersion){
			switch(upgradeTo){
				case 2: 
					DeleteTables(db);
			        onCreate(db);
			        upgradeFromTwo = true;
			        break;
				case 3:
					if(!upgradeFromTwo){
						alterTableBankMsgConfig_V3(db);
						insertSMSBanking_V3(db);
						insertHDFCBankMSGConfig_V3(db);
					}
					ScanCardTaskOnUpgrade up;
					try {
							if(!upgradeFromTwo)
								up = new ScanCardTaskOnUpgrade(_context,_context.getPackageManager().getPackageInfo(_context.getPackageName(), 0).versionCode);
							else
								up = new ScanCardTaskOnUpgrade(_context,-1);
							up.execute();
					} catch (NameNotFoundException e) {
						e.printStackTrace();
					}
					break;
				case 4:
					insertAXISBankMSGConfig_V4(db);
					insertAXISSMSBanking_V4(db);
					insertStanChartBankMSGConfig_V4(db);
				try {
					ScanCardTaskOnUpgrade up4 = new ScanCardTaskOnUpgrade(_context,_context.getPackageManager().getPackageInfo(_context.getPackageName(), 0).versionCode);
					up4.execute();
				} catch (NameNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
					break;
			}
			upgradeTo = upgradeTo + 1;
			
		}
	}
	@Override
	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		DeleteTables(db);
        onCreate(db);
    }
	
	public List<BankDataBin> getSupportedBanks(int version){
		List<BankDataBin> lstBankList = new ArrayList<BankDataBin>();
		String selectQuery; 
		if(version>0){
			selectQuery = "SELECT " + Table_BankMaster.TABLE_BANKMASTER + "." +  Table_BankMaster._ID
				+ "," + Table_BankMaster.COLUMN_BANKNAME
				+ "," + Table_BankMaster.COLUMN_SMSADDRESS
				+ "," + Table_BankMaster.TABLE_BANKMASTER + "." + Table_BankMaster.COLUMN_ISDELETED
				+ "," + Table_BankCardMsgConfig.COLUMN_MSGTYPE
				+ "," + Table_BankCardMsgConfig.COLUMN_DESCRIPTION
				+ "," + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX
				+ "," + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT
				+ "," + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX
				+ " FROM " + Table_BankMaster.TABLE_BANKMASTER + "," + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG 
				+ " WHERE "+ Table_BankMaster.TABLE_BANKMASTER + "." + Table_BankMaster.COLUMN_ISDELETED + " = 0"
				+ " AND " + Table_BankCardMsgConfig.COLUMN_ADDDEDINVERSION + " = " + version
				+ " AND " + Table_BankMaster.TABLE_BANKMASTER + "." +  Table_BankMaster._ID + "="
				+  Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "." +  Table_BankCardMsgConfig.COLUMN_BANKID ;
		} else{
			selectQuery = "SELECT " + Table_BankMaster.TABLE_BANKMASTER + "." +  Table_BankMaster._ID
					+ "," + Table_BankMaster.COLUMN_BANKNAME
					+ "," + Table_BankMaster.COLUMN_SMSADDRESS
					+ "," + Table_BankMaster.TABLE_BANKMASTER + "." + Table_BankMaster.COLUMN_ISDELETED
					+ "," + Table_BankCardMsgConfig.COLUMN_MSGTYPE
					+ "," + Table_BankCardMsgConfig.COLUMN_DESCRIPTION
					+ "," + Table_BankCardMsgConfig.COLUMN_MSGSIGNATUREREGEX
					+ "," + Table_BankCardMsgConfig.COLUMN_FIELDTOEXTRACT
					+ "," + Table_BankCardMsgConfig.COLUMN_FIELDEXTRACTREGEX
					+ " FROM " + Table_BankMaster.TABLE_BANKMASTER + "," + Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG 
					+ " WHERE "+ Table_BankMaster.TABLE_BANKMASTER + "." + Table_BankMaster.COLUMN_ISDELETED + " = 0"
					+ " AND " + Table_BankMaster.TABLE_BANKMASTER + "." +  Table_BankMaster._ID + "="
					+  Table_BankCardMsgConfig.TABLE_BANKCARDMSGCONFIG + "." +  Table_BankCardMsgConfig.COLUMN_BANKID ;
		}
		
		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	   lstBankList.add(new BankDataBin(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)
        			   ,cursor.getString(4), cursor.getString(5),cursor.getString(6),cursor.getString(7),cursor.getString(8)));
        	   
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       return lstBankList;
	}
	public String getBankName(int address){
		String b = null;
		String selectQuery = "SELECT " + Table_BankMaster._ID
				+ "," + Table_BankMaster.COLUMN_BANKNAME
				+ "," + Table_BankMaster.COLUMN_SMSADDRESS
				+ "," + Table_BankMaster.COLUMN_ISDELETED
				+ " FROM " + Table_BankMaster.TABLE_BANKMASTER
				+ " WHERE "+ Table_BankMaster.COLUMN_ISDELETED + " = 0";
		
		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	   b = cursor.getString(1);
        	   
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       return b;
	}
	
	public List<CardTranDataBin> getCardTransactions(int cardID,Date stmtDate){
		List<CardTranDataBin> lstCardTrans = new ArrayList<CardTranDataBin>();
		String fromDate = null;
		String toDate = null;
		
		Date[] returnDt = HelperUtility.getDateForRange(stmtDate, 1);
		fromDate = format.format(returnDt[0]);
		toDate = format.format(returnDt[1]);
		
		String selectQuery = "SELECT " + Table_CardTransactions._ID
				+ "," + Table_CardTransactions.COLUMN_TRANAMOUNT
				+ "," + Table_CardTransactions.COLUMN_TRANVENDOR
				+ "," + Table_CardTransactions.COLUMN_TRANDATE
				+ " FROM " + Table_CardTransactions.TABLE_CARDTRANSACTIONS 
				+ " WHERE "+ Table_CardTransactions.COLUMN_ISDELETED + " = 0"
				+ " AND " + Table_CardTransactions.COLUMN_CARDID + "=" + cardID
				+ " AND (" + Table_CardTransactions.COLUMN_TRANDATE + ">='" + fromDate + "'" 
				+ " AND " + Table_CardTransactions.COLUMN_TRANDATE + "<='" + toDate + "')" 
				+ " ORDER BY " + Table_CardTransactions.COLUMN_TRANDATE + " DESC"; 

		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	   try {
				lstCardTrans.add(new CardTranDataBin(cursor.getDouble(1), cursor.getString(2), format.parse(cursor.getString(3))));
			} catch (ParseException e) {
				e.printStackTrace();
			}
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       return lstCardTrans;
		
	}
	
	
	public List<CardTranDataBin> getCardTransactionsWithCurCycleTran(int cardID,Date stmtDate){
		List<CardTranDataBin> lstCardTrans = new ArrayList<CardTranDataBin>();
		String[] returnstr = getDateArray(stmtDate, 1);
		Date fromDate =null;
		Date toDate = null; 
		Date tranDate = null;
		
		try {
			 fromDate =format.parse(returnstr[0]);
			 toDate = format.parse(returnstr[1]);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String selectQuery = "SELECT " + Table_CardTransactions._ID
				+ "," + Table_CardTransactions.COLUMN_TRANAMOUNT
				+ "," + Table_CardTransactions.COLUMN_TRANVENDOR
				+ "," + Table_CardTransactions.COLUMN_TRANDATE
				+ " FROM " + Table_CardTransactions.TABLE_CARDTRANSACTIONS 
				+ " WHERE "+ Table_CardTransactions.COLUMN_ISDELETED + " = 0"
				+ " AND " + Table_CardTransactions.COLUMN_CARDID + "=" + cardID 
				+ " ORDER BY " + Table_CardTransactions.COLUMN_TRANDATE + " DESC"; 

		Boolean curCycle = false;
		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	  
        	   try {
        		   		tranDate = format.parse(cursor.getString(3));
        		   		if((tranDate.after(fromDate) || tranDate.equals(fromDate)) && (tranDate.before(toDate)||tranDate.equals(toDate)))
        		   			curCycle = true;
        		   		else
        		   			curCycle = false;
        		   		lstCardTrans.add(new CardTranDataBin(cursor.getDouble(1), cursor.getString(2),tranDate,curCycle));
			} catch (ParseException e) {
				e.printStackTrace();
			}
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       return lstCardTrans;
		
	}
	public void deleteBankCardDetails(int cardId){
		String deleteSql = "UPDATE " + Table_BankCardMaster.TABLE_BANKCARDMASTER
				+ " SET " + Table_BankCardMaster.COLUMN_ISDELETED + "=1"
				+ " WHERE " + Table_BankCardMaster._ID + " = " + cardId;
		db = this.getWritableDatabase();
		db.execSQL(deleteSql);
		db.close();
	}
	public List<CardPaymentDataBin> getCardPayments(int cardID){
		List<CardPaymentDataBin> lstCardPayments = new ArrayList<CardPaymentDataBin>();
		String selectQuery = "SELECT " + Table_CardPayments._ID
				+ "," + Table_CardPayments.COLUMN_AMTPAID
				+ "," + Table_CardPayments.COLUMN_DATEPAID
				+ "," + Table_CardPayments.COLUMN_PAYMENTMODE
				+ " FROM " + Table_CardPayments.TABLE_CARDPAYMENTS 
				+ " WHERE "+ Table_CardPayments.COLUMN_ISDELETED + " = 0"
				+ " AND " + Table_CardPayments.COLUMN_CARDID + "=" + cardID 
				+ " ORDER BY " + Table_CardPayments.COLUMN_DATEPAID + " DESC"; 

		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	   try {
				lstCardPayments.add(new CardPaymentDataBin(format.parse(cursor.getString(2)), cursor.getDouble(1)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       return lstCardPayments;
		
	}

	public List<String> getBankCardNumberList(){
		List<String> lstCard = new ArrayList<String>();
		String selectQuery = "SELECT " + Table_BankCardMaster.COLUMN_CARDNUMBER
				+ " FROM " + Table_BankCardMaster.TABLE_BANKCARDMASTER; 
				//+ " WHERE "+ Table_BankCardMaster.COLUMN_ISDELETED + " = 0";
		
		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	   lstCard.add(cursor.getString(0));
        	   
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       return lstCard;
	}
	public BankCardDataBin getBankCardDetails(int cardId){
		BankCardDataBin bin = null;
		String selectQuery = "SELECT " + Table_BankCardMaster.COLUMN_BANKID
						+ "," + Table_BankCardMaster.COLUMN_CARDNUMBER
						+ "," + Table_BankCardMaster.COLUMN_CARDTYPE
						+ "," + Table_BankCardMaster.COLUMN_STMTDATE
						+ "," + Table_BankCardMaster.COLUMN_CREDITPERIOD
						+ "," + Table_BankCardMaster.COLUMN_CREDITLIMIT
						+ "," + Table_BankCardMaster.COLUMN_ISDELETED
						+ " FROM " + Table_BankCardMaster.TABLE_BANKCARDMASTER
						+ " WHERE " + Table_BankCardMaster._ID + " = " + cardId;
		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	   
        	   if(cursor.getString(3)!=null)
				try {
					bin = new BankCardDataBin(cursor.getInt(0), cursor.getString(1), 
						   cursor.getString(2), format.parse(cursor.getString(3))
						   , cursor.getInt(4), cursor.getDouble(5));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			else
        		   bin = new BankCardDataBin(cursor.getInt(0), cursor.getString(1), 
            			   cursor.getString(2), null
            			   , cursor.getInt(4), cursor.getDouble(5));
        	   
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
	   return bin;
		
	}
	public List<BankCardDataBin> getBankCardDetails(){
		List<BankCardDataBin> lstBankCardDetails = new ArrayList<BankCardDataBin>();
		String selectQuery = "SELECT " + Table_BankCardMaster.TABLE_BANKCARDMASTER + "." + Table_BankCardMaster._ID
				+ "," + Table_BankCardMaster.COLUMN_BANKID
				+ "," + Table_BankCardMaster.COLUMN_CARDNUMBER
				+ "," + Table_BankCardMaster.COLUMN_CARDTYPE
				+ "," + Table_BankCardMaster.COLUMN_STMTDATE
				+ "," + Table_BankCardMaster.COLUMN_CREDITPERIOD
				+ "," + Table_BankCardMaster.COLUMN_CREDITLIMIT
				+ "," + Table_BankMaster.COLUMN_BANKNAME
				+ "," + Table_BankCardMaster.TABLE_BANKCARDMASTER + "." + Table_BankCardMaster.COLUMN_ISDELETED
				+ "," + Table_BankMaster.TABLE_BANKMASTER + "." + Table_BankMaster._ID
				+ " FROM " + Table_BankCardMaster.TABLE_BANKCARDMASTER + "," +  Table_BankMaster.TABLE_BANKMASTER
				+ " WHERE "+ Table_BankCardMaster.TABLE_BANKCARDMASTER + "." + Table_BankCardMaster.COLUMN_ISDELETED + " = 0"
				+ " AND " + Table_BankCardMaster.TABLE_BANKCARDMASTER + "." + Table_BankCardMaster.COLUMN_BANKID + "=" 
				+ Table_BankMaster.TABLE_BANKMASTER + "." + Table_BankMaster._ID;
		
		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	    if(cursor.getString(4)!=null)
					try {
						lstBankCardDetails.add(new BankCardDataBin(cursor.getInt(0), cursor.getString(2), cursor.getString(3),
							format.parse(cursor.getString(4)), cursor.getInt(5), cursor.getDouble(6), cursor.getString(7), cursor.getInt(8),cursor.getInt(9)));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				else
        	    	lstBankCardDetails.add(new BankCardDataBin(cursor.getInt(0), cursor.getString(2), cursor.getString(3),
            	    		null, cursor.getInt(5), cursor.getDouble(6), cursor.getString(7), cursor.getInt(8),cursor.getInt(9)));
        	   
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       return lstBankCardDetails;
	}
	public List<SMSBankingDataBin> getSMSBanking(int bankID){
		List<SMSBankingDataBin> _lstReturn = new ArrayList<SMSBankingDataBin>();
		String selectQuery = "SELECT " + Table_SMSBanking._ID
				+ "," + Table_SMSBanking.COLUMN_BANKID
				+ "," + Table_SMSBanking.COLUMN_OPERATION
				+ "," + Table_SMSBanking.COLUMN_COMMAND
				+ "," + Table_SMSBanking.COLUMN_REQUIREDDIGITS
				+ "," + Table_SMSBanking.COLUMN_SMSNUMBER
				+ "," + Table_SMSBanking.COLUMN_ISDELETED
				+ " FROM " + Table_SMSBanking.TABLE_SMSBANKING 
				+ " WHERE "+ Table_SMSBanking.COLUMN_ISDELETED + " = 0"
				+ " AND " + Table_SMSBanking.COLUMN_BANKID + "=" + bankID ; 

		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	   _lstReturn.add(new SMSBankingDataBin(cursor.getInt(0),cursor.getInt(1),cursor.getString(2), 
        			   cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getInt(6)));
        	   
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       return _lstReturn;
	}
	
	
	public int getBankCardID(String cardNo){
		int returnCardId=0;
		String selectQuery = "SELECT " + Table_BankCardMaster._ID
				+ " FROM " + Table_BankCardMaster.TABLE_BANKCARDMASTER 
				+ " WHERE "+ Table_BankCardMaster.COLUMN_ISDELETED + " = 0"
				+ " AND " + Table_BankCardMaster.COLUMN_CARDNUMBER + "='" + cardNo + "'"; 
				
		
		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	   returnCardId = cursor.getInt(0);
        	   
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       return returnCardId;
	}
	public Boolean updateBankCardDetails(int cardId, double creditLimit, Date stmtDate, int creditperiod){
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Table_BankCardMaster.COLUMN_CREDITLIMIT, creditLimit);
		values.put(Table_BankCardMaster.COLUMN_STMTDATE, HelperUtility.formatDateForDB(stmtDate));
		values.put(Table_BankCardMaster.COLUMN_CREDITPERIOD, creditperiod);
		int result = db.update(Table_BankCardMaster.TABLE_BANKCARDMASTER, values, Table_BankCardMaster._ID + "= ? " ,new String[] { String.valueOf(cardId)});
		db.close();
		if(result>0)		
			return true;
		else
			return false;
	}
	public long addScannedMessages(ScannedMessagesDataBin b){
		long id;
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Table_ScannedMessages.COLUMN_SMSADDR,b.get_SMSAddr());
		values.put(Table_ScannedMessages.COLUMN_SMSMSG,b.get_SMSMsg());
		id = db.insert(Table_ScannedMessages.TABLE_SCANNEDMESSAGES, null, values);
		db.close();
		return id;
	}
	public long addSupportedBank(BankDataBin bin){
		long id;
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Table_BankMaster.COLUMN_BANKNAME,bin.get_bankName());
		values.put(Table_BankMaster.COLUMN_ISDELETED,0);
		values.put(Table_BankMaster.COLUMN_SMSADDRESS,bin.get_smsAddress());
		id = db.insert(Table_BankMaster.TABLE_BANKMASTER, null, values);
		return id;
	}
	public long addBankCardData(BankCardDataBin b){
		long id;
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Table_BankCardMaster.COLUMN_BANKID,b.get_bankId());
		values.put(Table_BankCardMaster.COLUMN_CARDNUMBER,b.get_cardNumber());
		values.put(Table_BankCardMaster.COLUMN_CARDTYPE,b.get_cardType());
		values.put(Table_BankCardMaster.COLUMN_CREDITLIMIT,b.get_creditLimit());
		values.put(Table_BankCardMaster.COLUMN_CREDITPERIOD,b.get_creditPeriod());
		if(b.get_stmtDate()!=null)
			values.put(Table_BankCardMaster.COLUMN_STMTDATE,HelperUtility.formatDateForDB(b.get_stmtDate()));
		id = db.insert(Table_BankCardMaster.TABLE_BANKCARDMASTER, null, values);
		db.close();
		return id;
	}
	public long addCardTransaction(CardTranDataBin b){
		long id;
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Table_CardTransactions.COLUMN_AVAILCREDITLIMIT,b.get_availCreditLimit());
		values.put(Table_CardTransactions.COLUMN_CARDID,b.get_CardId());
		values.put(Table_CardTransactions.COLUMN_ISDELETED,0);
		values.put(Table_CardTransactions.COLUMN_TOTALCREDITLIMIT,b.get_totalCreditLimit());
		values.put(Table_CardTransactions.COLUMN_TRANAMOUNT,b.get_tranAmount());
		values.put(Table_CardTransactions.COLUMN_TRANDATE,HelperUtility.formatDateForDB(b.get_tranDate()));
		values.put(Table_CardTransactions.COLUMN_TRANVENDOR,b.get_tranVender());
		id = db.insert(Table_CardTransactions.TABLE_CARDTRANSACTIONS, null, values);
		db.close();
		return id;
	}
	public long addCardPayment(CardPaymentDataBin b){
		long id;
		db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(Table_CardPayments.COLUMN_AMTPAID,b.get_amtPaid());
		values.put(Table_CardPayments.COLUMN_CARDID,b.get_cardId());
		values.put(Table_CardPayments.COLUMN_ISDELETED,0);
		values.put(Table_CardPayments.COLUMN_DATEPAID,HelperUtility.formatDateForDB(b.get_paymentDate()));
		values.put(Table_CardPayments.COLUMN_PAYMENTMODE,b.get_paymentMode());
		id = db.insert(Table_CardPayments.TABLE_CARDPAYMENTS, null, values);
		db.close();
		return id;
	}
	
	public void deleteCardTran(int tranId){
		String delquery = "UPDATE " + Table_CardTransactions.TABLE_CARDTRANSACTIONS
				+ " SET " + Table_CardTransactions.COLUMN_ISDELETED + " = 1"
				+ " WHERE " + Table_CardTransactions._ID + " = " + tranId;
		db = this.getWritableDatabase();
		db.execSQL(delquery);
		db.close();
	}
	private String[] getDateArray(Date stmtDate, int duration){
		String fromDate = null;
		String toDate = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
		java.util.Date[] returnDt = HelperUtility.getDateForRange(stmtDate, duration);
		fromDate = format.format(returnDt[0]);
		toDate = format.format(returnDt[1]);
		String[] arrReturn = new String[] { fromDate, toDate};
		return arrReturn;
	}
	
	public List<CardPaymentDataBin> getDataForDateWisePayments(int cardID,Date stmtDate, int duration) {
		List<CardPaymentDataBin> lstCardTrans = new ArrayList<CardPaymentDataBin>();
		String[] returnstr = getDateArray(stmtDate, duration);
		String fromDate = returnstr[0];
		String toDate = returnstr[1];
			
		String selectQuery = "SELECT " + Table_CardPayments.COLUMN_DATEPAID 
				+ ", sum(" + Table_CardPayments.COLUMN_AMTPAID + ")"
				+ " FROM " + Table_CardPayments.TABLE_CARDPAYMENTS 
				+ " WHERE "+ Table_CardPayments.COLUMN_ISDELETED + " = 0"
				+ " AND " + Table_CardPayments.COLUMN_CARDID + "='" + cardID + "'"
				+ " AND (" + Table_CardPayments.COLUMN_DATEPAID + ">='" + fromDate + "'" 
				+ " AND " + Table_CardPayments.COLUMN_DATEPAID + "<='" + toDate + "')" 
				+ " GROUP BY " + Table_CardPayments.COLUMN_DATEPAID; 

		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	   
        	   try {
				lstCardTrans.add(new CardPaymentDataBin(format.parse(cursor.getString(0)),cursor.getDouble(1)));
			} catch (ParseException e) {
				e.printStackTrace();
			}
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       return lstCardTrans;

		
	}
    
	public double getTranAmtSumInCurrentCycle(int cardId, Date stmtDate){
		double returnamt = 0;
		String[] returnstr = getDateArray(stmtDate, 1);
		String fromDate = returnstr[0];
		String toDate = returnstr[1];
		String selectQuery = "SELECT " + "sum(" + Table_CardTransactions.COLUMN_TRANAMOUNT + ")"
				+ " FROM " + Table_CardTransactions.TABLE_CARDTRANSACTIONS 
				+ " WHERE "+ Table_CardTransactions.COLUMN_ISDELETED + " = 0"
				+ " AND " + Table_CardTransactions.COLUMN_CARDID + "=" + cardId 
				+ " AND (" + Table_CardTransactions.COLUMN_TRANDATE + ">='" + fromDate + "'" 
				+ " AND " + Table_CardTransactions.COLUMN_TRANDATE + "<='" + toDate + "')";
		
		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	   returnamt = cursor.getDouble(0);
       		   
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
	   return returnamt;
	}
	
	public List<CardTranDataBin> getDataForDateWiseTransactions(int cardID, Date stmtDate, int duration) {
		List<CardTranDataBin> lstCardTrans = new ArrayList<CardTranDataBin>();
		String[] returnstr = getDateArray(stmtDate, duration);
		String fromDate = returnstr[0];
		String toDate = returnstr[1];
		
		String selectQuery = "SELECT " + Table_CardTransactions.COLUMN_TRANDATE 
				+ ", sum(" + Table_CardTransactions.COLUMN_TRANAMOUNT + ")"
				+ " FROM " + Table_CardTransactions.TABLE_CARDTRANSACTIONS 
				+ " WHERE "+ Table_CardTransactions.COLUMN_ISDELETED + " = 0"
				+ " AND " + Table_CardTransactions.COLUMN_CARDID + "=" + cardID 
				+ " AND (" + Table_CardTransactions.COLUMN_TRANDATE + ">='" + fromDate + "'" 
				+ " AND " + Table_CardTransactions.COLUMN_TRANDATE + "<='" + toDate + "')" 
				+ " GROUP BY " + Table_CardTransactions.COLUMN_TRANDATE
				+ " ORDER BY " + Table_CardTransactions.COLUMN_TRANDATE; 
		
		
		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
       		   try {
				lstCardTrans.add(new CardTranDataBin(format.parse(cursor.getString(0)),cursor.getDouble(1)));
			} catch (ParseException e) {
				e.printStackTrace();
			}   
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       return lstCardTrans;

		
	}
	public List<CardAnalyticsDataBin> getDataForVendorWiseTransactions(AnalyticsGroupByClause clause) {
		List<CardAnalyticsDataBin> lstCardTrans = new ArrayList<CardAnalyticsDataBin>();
		
		String selectQuery = "SELECT " + Table_CardTransactions.TABLE_CARDTRANSACTIONS + "." + Table_CardTransactions._ID + ","  
				+ Table_BankCardMaster.TABLE_BANKCARDMASTER + "." + Table_BankCardMaster.COLUMN_BANKID + ","
				+ Table_BankMaster.TABLE_BANKMASTER + "." + Table_BankMaster.COLUMN_BANKNAME + ","
				+ Table_CardTransactions.TABLE_CARDTRANSACTIONS + "." + Table_CardTransactions.COLUMN_CARDID + ","
				+ Table_BankCardMaster.TABLE_BANKCARDMASTER + "." + Table_BankCardMaster.COLUMN_CARDNUMBER + ","
				+ Table_BankCardMaster.TABLE_BANKCARDMASTER + "." + Table_BankCardMaster.COLUMN_STMTDATE + ","
				+ " SUM( " + Table_CardTransactions.TABLE_CARDTRANSACTIONS + "." + Table_CardTransactions.COLUMN_TRANAMOUNT + ") as TranAmount,"
				+ Table_CardTransactions.TABLE_CARDTRANSACTIONS + "." + Table_CardTransactions.COLUMN_TRANDATE + ","
				+ Table_CardTransactions.TABLE_CARDTRANSACTIONS + "." + Table_CardTransactions.COLUMN_TRANVENDOR + ","
				+ " strftime('%m'," + Table_CardTransactions.TABLE_CARDTRANSACTIONS + "." + Table_CardTransactions.COLUMN_TRANDATE + ") as tranDateMonth , "
				+ " strftime('%Y'," + Table_CardTransactions.TABLE_CARDTRANSACTIONS + "." + Table_CardTransactions.COLUMN_TRANDATE + ") as tranDateYear "
				+ " FROM " + Table_CardTransactions.TABLE_CARDTRANSACTIONS + ","
				+ Table_BankMaster.TABLE_BANKMASTER + "," + Table_BankCardMaster.TABLE_BANKCARDMASTER
				+ " WHERE " + Table_CardTransactions.TABLE_CARDTRANSACTIONS + "." + Table_CardTransactions.COLUMN_CARDID + "=" + Table_BankCardMaster.TABLE_BANKCARDMASTER + "." + Table_BankCardMaster._ID
				+ " AND " + Table_BankCardMaster.TABLE_BANKCARDMASTER  + "." + Table_BankCardMaster.COLUMN_BANKID + "=" + Table_BankMaster.TABLE_BANKMASTER + "." + Table_BankMaster._ID
				+ " AND " + Table_CardTransactions.TABLE_CARDTRANSACTIONS + "." + Table_CardTransactions.COLUMN_ISDELETED + " = 0"
				+ " AND " + Table_BankCardMaster.TABLE_BANKCARDMASTER + "." + Table_BankCardMaster.COLUMN_ISDELETED + " = 0"
				+ " AND " + Table_BankMaster.TABLE_BANKMASTER + "." + Table_BankMaster.COLUMN_ISDELETED + " = 0";
				if(clause == AnalyticsGroupByClause.vendor){
					selectQuery = selectQuery + " GROUP BY " + Table_CardTransactions.TABLE_CARDTRANSACTIONS + "." + Table_CardTransactions.COLUMN_TRANVENDOR;
					selectQuery = selectQuery + " ORDER BY TranAmount DESC";
				} else if(clause == AnalyticsGroupByClause.card){
					selectQuery = selectQuery + " GROUP BY " + Table_CardTransactions.TABLE_CARDTRANSACTIONS + "." + Table_CardTransactions.COLUMN_CARDID;
					selectQuery = selectQuery + " ORDER BY TranAmount DESC";
				} else if(clause == AnalyticsGroupByClause.tranDate){
					//selectQuery = selectQuery + " GROUP BY " + Table_CardTransactions.TABLE_CARDTRANSACTIONS + "." + Table_CardTransactions.COLUMN_TRANDATE;
					selectQuery = selectQuery + " GROUP BY tranDateYear, tranDateMonth";
					selectQuery = selectQuery + " ORDER BY tranDateYear, tranDateMonth";
				}
				  
						
        Log.d("DBHelper", selectQuery);
		db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
           do {
        	   
        	   try {
        		   Date stmtDate = null, tranDate = null;
        		   if(cursor.getString(5)!=null){
        			   stmtDate =format.parse(cursor.getString(5));
        		   }
        		   if(cursor.getString(7)!=null){
        			   tranDate =stmtDate =format.parse(cursor.getString(7));
        		   }   
    			   lstCardTrans.add(new CardAnalyticsDataBin(cursor.getInt(0), cursor.getInt(1), 
					   cursor.getString(2), cursor.getInt(3), cursor.getString(4),
					   stmtDate ,  Double.parseDouble(cursor.getString(6)),
					   tranDate, cursor.getString(8),Integer.parseInt(cursor.getString(9)),Integer.parseInt(cursor.getString(10))));
    			   //cursor.getDouble(6)
			} catch (ParseException e) {
				e.printStackTrace();
			}
        			   
           } while (cursor.moveToNext());
       }
       cursor.close();
       db.close();
       return lstCardTrans;
	
	}

}
