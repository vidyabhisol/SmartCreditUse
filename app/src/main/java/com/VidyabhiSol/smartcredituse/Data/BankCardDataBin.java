package com.VidyabhiSol.smartcredituse.Data;

import java.util.Date;

public class BankCardDataBin {
	int _id;
	int _bankId;
	String _cardNumber;
	String _cardType;
	Date _stmtDate;
	int _isDeleted;
	int _creditPeriod;
	double _creditLimit;
	String _bankName;
	double _consumedCreditLimit;
	
	
	public double get_consumedCreditLimit() {
		return _consumedCreditLimit;
	}
	public void set_consumedCreditLimit(double _consumedCreditLimit) {
		this._consumedCreditLimit = _consumedCreditLimit;
	}
	public BankCardDataBin(int _Id, String _cardNumber, String _cardType,
			Date _stmtDate, int _creditPeriod,
			double _creditLimit, String _bankName, int _isDeleted,int _bankId ) {
		super();
		this._id = _Id;
		this._cardNumber = _cardNumber;
		this._cardType = _cardType;
		this._stmtDate = _stmtDate;
		this._isDeleted = _isDeleted;
		this._creditPeriod = _creditPeriod;
		this._creditLimit = _creditLimit;
		this._bankName = _bankName;
		this._bankId = _bankId;
	}
	public BankCardDataBin(int _bankId, String _cardNumber, String _cardType,
			Date _stmtDate, int _creditPeriod,
			double _creditLimit, String _bankName) {
		super();
		this._bankId = _bankId;
		this._cardNumber = _cardNumber;
		this._cardType = _cardType;
		this._stmtDate = _stmtDate;
		this._creditPeriod = _creditPeriod;
		this._creditLimit = _creditLimit;
		this._bankName = _bankName;
	}
	public BankCardDataBin(int _bankId, String _cardNumber, String _cardType,
			Date _stmtDate, int _creditPeriod,
			double _creditLimit) {
		super();
		this._bankId = _bankId;
		this._cardNumber = _cardNumber;
		this._cardType = _cardType;
		this._stmtDate = _stmtDate;
		this._creditPeriod = _creditPeriod;
		this._creditLimit = _creditLimit;
		
	}
	public int get_bankId() {
		return _bankId;
	}
	public void set_bankId(int _bankId) {
		this._bankId = _bankId;
	}
	public String get_cardNumber() {
		return _cardNumber;
	}
	public void set_cardNumber(String _cardNumber) {
		this._cardNumber = _cardNumber;
	}
	public String get_cardType() {
		return _cardType;
	}
	public void set_cardType(String _cardType) {
		this._cardType = _cardType;
	}
	public Date get_stmtDate() {
		return _stmtDate;
	}
	public void set_stmtDate(Date _stmtDate) {
		this._stmtDate = _stmtDate;
	}
	public int get_isDeleted() {
		return _isDeleted;
	}
	public void set_isDeleted(int _isDeleted) {
		this._isDeleted = _isDeleted;
	}
	public int get_creditPeriod() {
		return _creditPeriod;
	}
	public void set_creditPeriod(int _creditPeriod) {
		this._creditPeriod = _creditPeriod;
	}
	public double get_creditLimit() {
		return _creditLimit;
	}
	public void set_creditLimit(double _creditLimit) {
		this._creditLimit = _creditLimit;
	}
	public String get_bankName() {
		return _bankName;
	}
	public void set_bankName(String _bankName) {
		this._bankName = _bankName;
	}
	public int get_id() {
		return _id;
	}
	
}