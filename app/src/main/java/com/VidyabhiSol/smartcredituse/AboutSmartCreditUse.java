package com.VidyabhiSol.smartcredituse;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutSmartCreditUse extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about_smart_credit_use);
	}
	public void OnShareClicked(View view){
		Intent sendIntent = new Intent();
		sendIntent.setAction(Intent.ACTION_SEND);
		sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.MainActivity_ShareMessage) + "\n" + 
				getString(R.string.AppPackageURL) + this.getPackageName());
		sendIntent.setType("text/plain");
		startActivity(sendIntent);
	}
	public void OnSendFeedbackClicked(View view){
		Intent intent = new Intent(Intent.ACTION_SENDTO);
		intent.setType("text/plain");
		intent.setData(Uri.parse("mailto:" + getString(R.string.aboutscu_Vidyabhimail)));
		intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.aboutscu_YourFeedback));
		startActivity(Intent.createChooser(intent, "Send Email"));
	}
	public void CardNotListedClicked(View view){
		Intent intent = new Intent(this,ReportMissingCard.class);
		startActivity(intent);
	
	}
}
