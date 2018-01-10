package com.VidyabhiSol.smartcredituse.Helpers;

import android.provider.BaseColumns;

public final class DataContract {
	public DataContract(){
		
	}
	public static abstract class Table_BankMaster implements BaseColumns {
        public static final String TABLE_BANKMASTER = "BankMaster";
        public static final String COLUMN_BANKNAME = "BankName";
        public static final String COLUMN_SMSADDRESS = "SMSAddress";
        public static final String COLUMN_ISDELETED = "IsDeleted";
    }
	public static abstract class Table_BankCardMsgConfig implements BaseColumns {
        public static final String TABLE_BANKCARDMSGCONFIG = "BankCardMsgConfig";
        public static final String COLUMN_MSGTYPE = "MsgType";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_MSGSIGNATUREREGEX = "msgSignatureRegex";
        public static final String COLUMN_FIELDTOEXTRACT = "fieldsToExtract";
        public static final String COLUMN_FIELDEXTRACTREGEX = "fieldExtractRegex";
        public static final String COLUMN_BANKID = "BankID";
        public static final String COLUMN_ADDDEDINVERSION = "AddedInVersion";
        public static final String COLUMN_ISDELETED = "IsDeleted";
    }
	public static abstract class Table_BankCardMaster implements BaseColumns {
        public static final String TABLE_BANKCARDMASTER = "BankCardMaster";
        public static final String COLUMN_CARDNUMBER = "CardNumber";
        public static final String COLUMN_CARDTYPE = "CardType";
        public static final String COLUMN_STMTDATE = "StmtDate";
        public static final String COLUMN_BANKID = "BankID";
        public static final String COLUMN_CREDITPERIOD = "CreditPeriod";
        public static final String COLUMN_CREDITLIMIT = "CreditLimit";
        public static final String COLUMN_ISDELETED = "IsDeleted";
    }
	public static abstract class Table_CardTransactions implements BaseColumns {
        public static final String TABLE_CARDTRANSACTIONS = "CardTransactions";
        public static final String COLUMN_CARDID = "CardID";
        public static final String COLUMN_TRANAMOUNT = "TranAmount";
        public static final String COLUMN_TRANVENDOR = "TranVendor";
        public static final String COLUMN_TRANDATE = "TranDate";
        public static final String COLUMN_AVAILCREDITLIMIT = "AvailCreditLimit";
        public static final String COLUMN_TOTALCREDITLIMIT = "TotalCreditLimit";
        public static final String COLUMN_ISDELETED = "IsDeleted";
    }
	public static abstract class Table_CardPayments implements BaseColumns {
        public static final String TABLE_CARDPAYMENTS = "CardPayments";
        public static final String COLUMN_CARDID = "CardID";
        public static final String COLUMN_AMTPAID = "AmtPaid";
        public static final String COLUMN_DATEPAID = "DatePaid";
        public static final String COLUMN_PAYMENTMODE = "PaymentMode";
        public static final String COLUMN_ISDELETED = "IsDeleted";
    }
	public static abstract class Table_ScannedMessages implements BaseColumns {
        public static final String TABLE_SCANNEDMESSAGES = "ScannedMessages";
        public static final String COLUMN_SMSADDR = "SMSAddr";
        public static final String COLUMN_SMSMSG = "SMSMessage";
    }
	public static abstract class Table_SMSBanking implements BaseColumns {
        public static final String TABLE_SMSBANKING = "SMSBanking";
        public static final String COLUMN_BANKID = "BankID";
        public static final String COLUMN_OPERATION = "Operation";
        public static final String COLUMN_COMMAND = "COMMAND";
        public static final String COLUMN_REQUIREDDIGITS = "RequiredDigits";
        public static final String COLUMN_SMSNUMBER = "SMSNumber";
        public static final String COLUMN_ISDELETED = "IsDeleted";
    }

}
