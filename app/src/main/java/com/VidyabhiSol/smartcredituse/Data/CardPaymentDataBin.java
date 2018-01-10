package com.VidyabhiSol.smartcredituse.Data;

import java.util.Date;

public class CardPaymentDataBin {
	int _id;
	int _cardId;
	Date _paymentDate;
	String _creditCardNo;
	double _amtPaid;
	String _paymentMode;
	String _month;
	public CardPaymentDataBin(int _id, int _cardId, Date _paymentDate,
			String _creditCardNo, double _amtPaid,String _paymentMode) {
		super();
		this._id = _id;
		this._cardId = _cardId;
		this._paymentDate = _paymentDate;
		this._creditCardNo = _creditCardNo;
		this._amtPaid = _amtPaid;
		this._paymentMode = _paymentMode;
	}
	public CardPaymentDataBin(int _cardId, Date _paymentDate,
			String _creditCardNo, double _amtPaid,String _paymentMode) {
		super();
		this._cardId = _cardId;
		this._paymentDate = _paymentDate;
		this._creditCardNo = _creditCardNo;
		this._amtPaid = _amtPaid;
		this._paymentMode = _paymentMode;
	}
	public CardPaymentDataBin(Date _paymentDate,double _amtPaid) {
		super();
		this._paymentDate = _paymentDate;
		this._amtPaid = _amtPaid;
		
	}
	
	public String get_month() {
		return _month;
	}
	public void set_month(String _month) {
		this._month = _month;
	}
	public String get_paymentMode() {
		return _paymentMode;
	}
	public void set_paymentMode(String _paymentMode) {
		this._paymentMode = _paymentMode;
	}
	public int get_cardId() {
		return _cardId;
	}
	public void set_cardId(int _cardId) {
		this._cardId = _cardId;
	}
	public Date get_paymentDate() {
		return _paymentDate;
	}
	public void set_paymentDate(Date _paymentDate) {
		this._paymentDate = _paymentDate;
	}
	public String get_creditCardNo() {
		return _creditCardNo;
	}
	public void set_creditCardNo(String _creditCardNo) {
		this._creditCardNo = _creditCardNo;
	}
	public double get_amtPaid() {
		return _amtPaid;
	}
	public void set_amtPaid(double _amtPaid) {
		this._amtPaid = _amtPaid;
	}
	public int get_id() {
		return _id;
	}
	

}
