package com.VidyabhiSol.smartcredituse;


import com.VidyabhiSol.smartcredituse.Helpers.HelperUtility;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SmsListener extends BroadcastReceiver {

	private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
	Context context;
	final SmsManager sms = SmsManager.getDefault();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(SMS_RECEIVED)) {
			final Bundle bundle = intent.getExtras();
			try{
				if(bundle!=null){
					final Object[] pdusObj = (Object[]) bundle.get("pdus");
					for(int i=0;i<pdusObj.length;i++){
						SmsMessage curMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
						String phoneNo = curMessage.getOriginatingAddress();
						String message = curMessage.getDisplayMessageBody();
						HelperUtility.matchMessageUsingRegex(phoneNo, message,null, context,-1);
					}
				}
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}

}
