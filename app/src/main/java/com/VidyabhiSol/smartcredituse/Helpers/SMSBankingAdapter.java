package com.VidyabhiSol.smartcredituse.Helpers;

import java.util.List;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import com.VidyabhiSol.smartcredituse.R;
import com.VidyabhiSol.smartcredituse.Data.SMSBankingDataBin;


public class SMSBankingAdapter extends ArrayAdapter<SMSBankingDataBin> {
	Context _context;
	List<SMSBankingDataBin> objList;
	String _selectedCardNumber;
	public SMSBankingAdapter(Context context,List<SMSBankingDataBin> objects, String selectedCardNumber){
		super(context, android.R.layout.activity_list_item, objects);
		_context = context;
		objList = objects;
		_selectedCardNumber = selectedCardNumber;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewHolder holder;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.smsbanklistitem,parent,false);
			holder = new ViewHolder();
			holder.textOper = (TextView) convertView.findViewById(R.id.smsbanklistitem_textViewOperation);
			holder.btnSMS = (ImageButton) convertView.findViewById(R.id.smsbanklistitem_imageButtonSend);
			convertView.setTag(holder);
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textOper.setText(objList.get(position).get_operation());
		holder.btnSMS.setTag(objList.get(position).get_smsNumber() + ":" + objList.get(position).get_command());
		holder.btnSMS.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.setData(Uri.parse("sms:"));
				sendIntent.setType("vnd.android-dir/mms-sms");
				String valTag = arg0.getTag().toString();
				String smsBody = "", sender="";
				if(valTag.length()>0){
					smsBody = valTag.split(":")[1];
					sender = valTag.split(":")[0];
				}
				sendIntent.putExtra("address", sender);
				sendIntent.putExtra("sms_body", smsBody + " " + _selectedCardNumber);
				_context.startActivity(sendIntent);
				
			}
		});
		return convertView;
		
	  }
	private static class ViewHolder {
	    public TextView textOper;
	    public ImageButton btnSMS;
	}	

}
