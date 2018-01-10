package com.VidyabhiSol.smartcredituse.Helpers;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.VidyabhiSol.smartcredituse.R;
import com.VidyabhiSol.smartcredituse.Data.CardPaymentDataBin;

public class BankCardPaymentAdapter extends ArrayAdapter<CardPaymentDataBin>  {
	Context _context;
	List<CardPaymentDataBin> objList;
	
	public BankCardPaymentAdapter(Context context,List<CardPaymentDataBin> objects){
		super(context, android.R.layout.activity_list_item, objects);;
		_context = context;
		objList = objects;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if(convertView==null){
			convertView = inflater.inflate(R.layout.cardpaymentlistitem,parent,false);
			holder = new ViewHolder();
			holder.textDate = (TextView) convertView.findViewById(R.id.cardpaymentlistitem_textViewDate);
			holder.textTranAmt = (TextView) convertView.findViewById(R.id.cardpaymentlistitem_textViewAmtPaid);
			convertView.setTag(holder);
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textDate.setText(HelperUtility.formatDateForDisplay(objList.get(position).get_paymentDate()));
		holder.textTranAmt.setText(HelperUtility.formatDouble(objList.get(position).get_amtPaid()));
		return convertView;
		
	  }
	private static class ViewHolder {
	    public TextView textDate;
	    public TextView textTranAmt;
	}

}
