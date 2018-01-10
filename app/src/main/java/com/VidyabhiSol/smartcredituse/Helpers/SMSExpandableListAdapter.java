package com.VidyabhiSol.smartcredituse.Helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.VidyabhiSol.smartcredituse.R;

@SuppressLint("InflateParams")
public class SMSExpandableListAdapter extends BaseExpandableListAdapter  {

	private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    public List<String> checkedMsg; 
    private HashMap<Integer, boolean[]> mChildCheckStates;
    private ChildViewHolder childViewHolder;
	private GroupViewHolder groupViewHolder;
    
	 @SuppressLint("UseSparseArrays")
	public SMSExpandableListAdapter(Context context, List<String> listDataHeader,
	            HashMap<String, List<String>> listChildData) {
	        this._context = context;
	        this._listDataHeader = listDataHeader;
	        this._listDataChild = listChildData;
	        checkedMsg = new ArrayList<String>(); 
	        mChildCheckStates = new HashMap<Integer, boolean[]>();
	    }
	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final String childText = (String) getChild(groupPosition, childPosition);
		String headerTitle = (String) getGroup(groupPosition);
		final String smsText = headerTitle + ":" + childText;
		final int mGroupPosition = groupPosition;
		final int mChildPosition = childPosition;
		
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.reportmissingcard_list_item, null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.mChildText = (TextView) convertView
                    .findViewById(R.id.reportmissingcard_lblListItem);
            childViewHolder.mCheckBox = (CheckBox) convertView
                    .findViewById(R.id.reportmissingcard_checkBoxChoice);
            convertView.setTag(R.layout.reportmissingcard_list_item, childViewHolder);
        } else{
        	childViewHolder = (ChildViewHolder) convertView
					.getTag(R.layout.reportmissingcard_list_item);
        }
        	
        childViewHolder.mChildText.setText(childText);
        childViewHolder.mCheckBox.setOnCheckedChangeListener(null);
        if (mChildCheckStates.containsKey(mGroupPosition)) {
			boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
			childViewHolder.mCheckBox.setChecked(getChecked[mChildPosition]);
 
		} else {
			boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];
			mChildCheckStates.put(mGroupPosition, getChecked);
			childViewHolder.mCheckBox.setChecked(false);
		}
        childViewHolder.mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
        	 
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				
				if (isChecked) {
					boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
					getChecked[mChildPosition] = isChecked;	
					mChildCheckStates.put(mGroupPosition, getChecked);
					checkedMsg.add(smsText);
								
				} else {
					boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
					getChecked[mChildPosition] = isChecked;
					mChildCheckStates.put(mGroupPosition, getChecked);
					if(checkedMsg.contains(smsText))
						checkedMsg.remove(smsText);
				}
			}
		});
        
        return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		 return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.reportmissingcard_list_group, null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.mGroupText = (TextView) convertView
                    .findViewById(R.id.reportmissingcard_lblListHeader);
            convertView.setTag(groupViewHolder);
            groupViewHolder.mGroupText.setTextColor(HelperUtility.CURCYCLECOLOR);
        } else{
        	groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
 
        groupViewHolder.mGroupText.setText(headerTitle);
 
        return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	public final class GroupViewHolder {
		 
		TextView mGroupText;
	}
 
	public final class ChildViewHolder {
 
		TextView mChildText;
		CheckBox mCheckBox;
	}
	
}
