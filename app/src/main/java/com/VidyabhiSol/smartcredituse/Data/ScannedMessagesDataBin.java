package com.VidyabhiSol.smartcredituse.Data;

public class ScannedMessagesDataBin {
	private String _SMSAddr;
	private int _id;
	private String _SMSMsg;
	public ScannedMessagesDataBin(String _SMSAddr, String _SMSMsg) {
		super();
		this._SMSAddr = _SMSAddr;
		this._SMSMsg = _SMSMsg;
	}
	public String get_SMSAddr() {
		return _SMSAddr;
	}
	public void set_SMSAddr(String _SMSAddr) {
		this._SMSAddr = _SMSAddr;
	}
	public String get_SMSMsg() {
		return _SMSMsg;
	}
	public void set_SMSMsg(String _SMSMsg) {
		this._SMSMsg = _SMSMsg;
	}
	public int get_id() {
		return _id;
	}
	

}
