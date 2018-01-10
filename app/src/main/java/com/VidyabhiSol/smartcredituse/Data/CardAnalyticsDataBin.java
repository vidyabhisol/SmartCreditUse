package com.VidyabhiSol.smartcredituse.Data;

import java.util.Date;

public class CardAnalyticsDataBin {
	int _id; 
	public CardAnalyticsDataBin(int _id, int _bankId, String _bankName,
			int _cardId, String _cardNumber, Date _stmtDate,
			double _tranAmount, Date _tranDate, String _tranVender,
			int tranMonth, int tranYear) {
		super();
		this._id = _id;
		this._bankId = _bankId;
		this._bankName = _bankName;
		this._cardId = _cardId;
		this._cardNumber = _cardNumber;
		this._stmtDate = _stmtDate;
		this._tranAmount = _tranAmount;
		this._tranDate = _tranDate;
		this._tranVender = _tranVender;
		this.tranMonth = tranMonth;
		this.tranYear = tranYear;
	}
	int _bankId;
	String _bankName;
	int _cardId;
	String _cardNumber;
	Date _stmtDate;
	double _tranAmount;
	Date _tranDate;
	String _tranVender;
	int tranMonth;
	public int getTranMonth() {
		return tranMonth;
	}
	public void setTranMonth(int tranMonth) {
		this.tranMonth = tranMonth;
	}
	public int getTranYear() {
		return tranYear;
	}
	public void setTranYear(int tranYear) {
		this.tranYear = tranYear;
	}
	int tranYear;
	public CardAnalyticsDataBin(int _id, int _bankId, String _bankName,
			int _cardId, String _cardNumber, Date _stmtDate,
			double _tranAmount, Date _tranDate, String _tranVender) {
		super();
		this._id = _id;
		this._bankId = _bankId;
		this._bankName = _bankName;
		this._cardId = _cardId;
		this._cardNumber = _cardNumber;
		this._stmtDate = _stmtDate;
		this._tranAmount = _tranAmount;
		this._tranDate = _tranDate;
		this._tranVender = _tranVender;
	}
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public int get_bankId() {
		return _bankId;
	}
	public void set_bankId(int _bankId) {
		this._bankId = _bankId;
	}
	public String get_bankName() {
		return _bankName;
	}
	public void set_bankName(String _bankName) {
		this._bankName = _bankName;
	}
	public int get_cardId() {
		return _cardId;
	}
	public void set_cardId(int _cardId) {
		this._cardId = _cardId;
	}
	public String get_cardNumber() {
		return _cardNumber;
	}
	public void set_cardNumber(String _cardNumber) {
		this._cardNumber = _cardNumber;
	}
	public Date get_stmtDate() {
		return _stmtDate;
	}
	public void set_stmtDate(Date _stmtDate) {
		this._stmtDate = _stmtDate;
	}
	public double get_tranAmount() {
		return _tranAmount;
	}
	public void set_tranAmount(double _tranAmount) {
		this._tranAmount = _tranAmount;
	}
	public Date get_tranDate() {
		return _tranDate;
	}
	public void set_tranDate(Date _tranDate) {
		this._tranDate = _tranDate;
	}
	public String get_tranVender() {
		return _tranVender;
	}
	public void set_tranVender(String _tranVender) {
		this._tranVender = _tranVender;
	}

}
