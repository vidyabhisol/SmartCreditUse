package com.VidyabhiSol.smartcredituse.Data;

public class SMSBankingDataBin {
	int _id;
	int _bankID;
	String _operation;
	String _command;
	int _requiredDigits;
	String _smsNumber;
	int _isDeleted;
	public SMSBankingDataBin(int _id,int _bankID, String _operation, String _command,
			int _requiredDigits, String _smsNumber, int _isDeleted) {
		super();
		this._id = _id;
		this._bankID = _bankID;
		this._operation = _operation;
		this._command = _command;
		this._requiredDigits = _requiredDigits;
		this._smsNumber = _smsNumber;
		this._isDeleted = _isDeleted;
	}
	public int get_bankID() {
		return _bankID;
	}
	public void set_bankID(int _bankID) {
		this._bankID = _bankID;
	}
	public String get_operation() {
		return _operation;
	}
	public void set_operation(String _operation) {
		this._operation = _operation;
	}
	public String get_command() {
		return _command;
	}
	public void set_command(String _command) {
		this._command = _command;
	}
	public int get_requiredDigits() {
		return _requiredDigits;
	}
	public void set_requiredDigits(int _requiredDigits) {
		this._requiredDigits = _requiredDigits;
	}
	public String get_smsNumber() {
		return _smsNumber;
	}
	public void set_smsNumber(String _smsNumber) {
		this._smsNumber = _smsNumber;
	}
	public int get_isDeleted() {
		return _isDeleted;
	}
	public void set_isDeleted(int _isDeleted) {
		this._isDeleted = _isDeleted;
	}
	public int get_id() {
		return _id;
	}
	
	
	
}
