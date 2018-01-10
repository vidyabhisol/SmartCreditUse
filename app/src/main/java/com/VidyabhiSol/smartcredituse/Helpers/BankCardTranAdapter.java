package com.VidyabhiSol.smartcredituse.Helpers;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.VidyabhiSol.smartcredituse.R;
import com.VidyabhiSol.smartcredituse.Data.CardTranDataBin;

public class BankCardTranAdapter extends ArrayAdapter<CardTranDataBin> {
	Context _context;
	List<CardTranDataBin> objList;
	
	public BankCardTranAdapter(Context context,List<CardTranDataBin> objects){
		super(context, android.R.layout.activity_list_item, objects);
		_context = context;
		objList = objects;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewHolder holder;
		if(convertView==null){
			convertView = inflater.inflate(R.layout.cardtranlistitem,parent,false);
			holder = new ViewHolder();
			holder.textDate = (TextView) convertView.findViewById(R.id.cardtranlistitem_textViewDate);
			holder.textTranAmt = (TextView) convertView.findViewById(R.id.cardtranlistitem_textViewTranAmt);
			holder.textVendor = (TextView) convertView.findViewById(R.id.cardtranlistitem_textViewVendor);
			if(objList.get(position).get_curCycle()){
				holder.textVendor.setTextColor(HelperUtility.CURCYCLECOLOR);
				holder.textDate.setTextColor(HelperUtility.CURCYCLECOLOR);
				holder.textTranAmt.setTextColor(HelperUtility.CURCYCLECOLOR);
				
			}
			convertView.setTag(holder);
		} else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textVendor.setText(objList.get(position).get_tranVender());
		holder.textDate.setText(HelperUtility.formatDateForDisplay(objList.get(position).get_tranDate()));
		holder.textTranAmt.setText(HelperUtility.formatDouble(objList.get(position).get_tranAmount()));
		return convertView;
		
	  }
	private static class ViewHolder {
	    public TextView textVendor;
	    public TextView textDate;
	    public TextView textTranAmt;
	}

}
