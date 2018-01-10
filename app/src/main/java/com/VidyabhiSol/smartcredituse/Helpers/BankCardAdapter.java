package com.VidyabhiSol.smartcredituse.Helpers;

import java.util.List;

import com.VidyabhiSol.smartcredituse.R;
import com.VidyabhiSol.smartcredituse.Data.BankCardDataBin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BankCardAdapter extends ArrayAdapter<BankCardDataBin>{
	Context _context;
	List<BankCardDataBin> objList;
	int curPosition;
	int gCard;
	public BankCardAdapter(Context context,List<BankCardDataBin> objects,int goodCard){
		super(context, android.R.layout.activity_list_item, objects);;
		_context = context;
		objList = objects;
		gCard = goodCard;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
			convertView = inflater.inflate(R.layout.smslistrow,parent,false);
			holder = new ViewHolder();
			holder.textBankName =  (TextView) convertView.findViewById(R.id.smslayoutrow_textViewBank);
			holder.textBankCard = (TextView) convertView.findViewById(R.id.smslayoutrow_textViewCard);
			holder.star = (ImageView) convertView.findViewById(R.id.smslistrow_imageViewIndicator);
			holder.bar = (ProgressBar) convertView.findViewById(R.id.smslistrow_progressBarLimit);
			holder.bar.setIndeterminate(false);
			convertView.setTag(holder);
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
		if(objList.get(position).get_id()==gCard)
			holder.star.setVisibility(View.VISIBLE);
		else
			holder.star.setVisibility(View.INVISIBLE);
		holder.textBankName.setText(objList.get(position).get_bankName());
		holder.textBankCard.setText(objList.get(position).get_cardNumber());
		Log.d("BankCardAdapter_BankCard", objList.get(position).get_bankName() + objList.get(position).get_cardNumber());
		Log.d("BankCardAdapter_Total", String.valueOf(objList.get(position).get_creditLimit()) );
		Log.d("BankCardAdapter_Consumed", String.valueOf(objList.get(position).get_consumedCreditLimit()) );
		int progress = (int) ((objList.get(position).get_consumedCreditLimit() * 100)/ objList.get(position).get_creditLimit());
		holder.bar.setProgress(progress);		
		return convertView;
		
	  }
	private static class ViewHolder {
	    public TextView textBankName;
	    public TextView textBankCard;
	    public ImageView star;
	    public ProgressBar bar;
	}

	
}
