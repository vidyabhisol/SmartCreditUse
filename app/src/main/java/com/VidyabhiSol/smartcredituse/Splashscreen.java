package com.VidyabhiSol.smartcredituse;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
public class Splashscreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splashscreen);

		Thread splashThread = new Thread(){
			@Override
			public void run(){
				try {
					synchronized (this) {
						wait(2000);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				finally {
					finish();
					Intent intmain = new Intent(getBaseContext(),MainActivity.class);
					startActivity(intmain);
					
				}
			}
		};
		
		splashThread.start();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		return true;
	}
	

}
