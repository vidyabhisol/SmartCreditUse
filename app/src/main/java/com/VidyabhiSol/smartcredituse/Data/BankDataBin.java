package com.VidyabhiSol.smartcredituse.Data;

public class BankDataBin {
	int _id;
	String _bankName;
	String _smsAddress;
	int _isDeleted;
	String _configMsgType;
	String _configMsgDesc;
	String _configMsgSignatureRegex;
	String _configFieldsToExtract;
	String _configfieldExtractRegex;
	
	public BankDataBin(int _id, String _bankName, String _smsAddress,
			int _isDeleted) {
		super();
		this._id = _id;
		this._bankName = _bankName;
		this._smsAddress = _smsAddress;
		this._isDeleted = _isDeleted;
	}
	
	public int get_id() {
		return _id;
	}

	public BankDataBin(int _id, String _bankName, String _smsAddress,
			int _isDeleted, String _configMsgType, String _configMsgDesc,
			String _configMsgSignatureRegex, String _configFieldsToExtract,
			String _configfieldExtractRegex) {
		super();
		this._id = _id;
		this._bankName = _bankName;
		this._smsAddress = _smsAddress;
		this._isDeleted = _isDeleted;
		this._configMsgType = _configMsgType;
		this._configMsgDesc = _configMsgDesc;
		this._configMsgSignatureRegex = _configMsgSignatureRegex;
		this._configFieldsToExtract = _configFieldsToExtract;
		this._configfieldExtractRegex = _configfieldExtractRegex;
	}

	public String get_configMsgType() {
		return _configMsgType;
	}

	public void set_configMsgType(String _configMsgType) {
		this._configMsgType = _configMsgType;
	}

	public String get_configMsgDesc() {
		return _configMsgDesc;
	}

	public void set_configMsgDesc(String _configMsgDesc) {
		this._configMsgDesc = _configMsgDesc;
	}

	public String get_configMsgSignatureRegex() {
		return _configMsgSignatureRegex;
	}

	public void set_configMsgSignatureRegex(String _configMsgSignatureRegex) {
		this._configMsgSignatureRegex = _configMsgSignatureRegex;
	}

	public String get_configFieldsToExtract() {
		return _configFieldsToExtract;
	}

	public void set_configFieldsToExtract(String _configFieldsToExtract) {
		this._configFieldsToExtract = _configFieldsToExtract;
	}

	public String get_configfieldExtractRegex() {
		return _configfieldExtractRegex;
	}

	public void set_configfieldExtractRegex(String _configfieldExtractRegex) {
		this._configfieldExtractRegex = _configfieldExtractRegex;
	}

	public String get_bankName() {
		return _bankName;
	}
	public void set_bankName(String _bankName) {
		this._bankName = _bankName;
	}
	public String get_smsAddress() {
		return _smsAddress;
	}
	public void set_smsAddress(String _smsAddress) {
		this._smsAddress = _smsAddress;
	}
	public int get_isDeleted() {
		return _isDeleted;
	}
	public void set_isDeleted(int _isDeleted) {
		this._isDeleted = _isDeleted;
	}
	
}
