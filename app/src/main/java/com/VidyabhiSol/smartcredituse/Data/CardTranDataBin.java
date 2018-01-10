package com.VidyabhiSol.smartcredituse.Data;

import java.util.Date;

public class CardTranDataBin {

	private String _providerAddress; 
	private String _creditCardNo;
	private double _tranAmount;
	private String _tranVender;
	private Date _tranDate;
	private double _availCreditLimit;
	private double _totalCreditLimit;
	private int _id;
	private int _CardId;
	private String _month;
	private String _Year;
	private Boolean _curCycle;
	
	public Boolean get_curCycle() {
		return _curCycle;
	}

	public void set_curCycle(Boolean _curCycle) {
		this._curCycle = _curCycle;
	}

	public String get_month() {
		return _month;
	}

	public void set_month(String _month) {
		this._month = _month;
	}

	public CardTranDataBin(String _providerAddress, String _creditCardNo,
			double _tranAmount, String _tranVender, Date _tranDate,
			double _availCreditLimit, double _totalCreditLimit, int _CardId) {
		super();
		this._providerAddress = _providerAddress;
		this._creditCardNo = _creditCardNo;
		this._tranAmount = _tranAmount;
		this._tranVender = _tranVender;
		this._tranDate = _tranDate;
		this._availCreditLimit = _availCreditLimit;
		this._totalCreditLimit = _totalCreditLimit;
		this._CardId = _CardId;
	}

	public CardTranDataBin(String _providerAddress, String _creditCardNo,
			double _tranAmount, String _tranVender, Date _tranDate,
			double _availCreditLimit, double _totalCreditLimit) {
		super();
		this._providerAddress = _providerAddress;
		this._creditCardNo = _creditCardNo;
		this._tranAmount = _tranAmount;
		this._tranVender = _tranVender;
		this._tranDate = _tranDate;
		this._availCreditLimit = _availCreditLimit;
		this._totalCreditLimit = _totalCreditLimit;
	}
	public CardTranDataBin(double _tranAmount, String _tranVender, Date _tranDate) {
		super();
		this._tranAmount = _tranAmount;
		this._tranVender = _tranVender;
		this._tranDate = _tranDate;

	}
	public CardTranDataBin(double _tranAmount, String _tranVender, Date _tranDate, Boolean curCycle) {
		super();
		this._tranAmount = _tranAmount;
		this._tranVender = _tranVender;
		this._tranDate = _tranDate;
		this._curCycle = curCycle;
	}

	

	public CardTranDataBin(Date _tranDate, double _tranAmount) {
		super();
		this._tranAmount = _tranAmount;
		this._tranDate = _tranDate;
	}

	public String get_Year() {
		return _Year;
	}

	public void set_Year(String _Year) {
		this._Year = _Year;
	}

	public int get_CardId() {
		return _CardId;
	}

	public void set_CardId(int _CardId) {
		this._CardId = _CardId;
	}

	public int get_id() {
		return _id;
	}

	public String get_providerAddress() {
		return _providerAddress;
	}

	public void set_providerAddress(String _providerAddress) {
		this._providerAddress = _providerAddress;
	}

	public String get_creditCardNo() {
		return _creditCardNo;
	}

	public void set_creditCardNo(String _creditCardNo) {
		this._creditCardNo = _creditCardNo;
	}

	public double get_tranAmount() {
		return _tranAmount;
	}

	public void set_tranAmount(double _tranAmount) {
		this._tranAmount = _tranAmount;
	}

	public String get_tranVender() {
		return _tranVender;
	}

	public void set_tranVender(String _tranVender) {
		this._tranVender = _tranVender;
	}

	public Date get_tranDate() {
		return _tranDate;
	}

	public void set_tranDate(Date _tranDate) {
		this._tranDate = _tranDate;
	}

	public double get_availCreditLimit() {
		return _availCreditLimit;
	}

	public void set_availCreditLimit(double _availCreditLimit) {
		this._availCreditLimit = _availCreditLimit;
	}

	public double get_totalCreditLimit() {
		return _totalCreditLimit;
	}

	public void set_totalCreditLimit(double _totalCreditLimit) {
		this._totalCreditLimit = _totalCreditLimit;
	}
	
	
	
}
