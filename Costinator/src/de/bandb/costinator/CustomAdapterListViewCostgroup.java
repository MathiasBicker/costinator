package de.bandb.costinator;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapterListViewCostgroup extends BaseAdapter {
	
	
	 private ArrayList<CostelementListViewItem> data;
	 private Context c;

	 public CustomAdapterListViewCostgroup (ArrayList<CostelementListViewItem> data, Context c){
	        this.data = data;
	        this.c = c; 
	        
	 }
	 
	 public int getCount() {
	        // TODO Auto-generated method stub
	        return data.size();
	    }
	    
	    public Object getItem(int position) {
	        // TODO Auto-generated method stub
	        return data.get(position);
	    }
	 
	    public long getItemId(int position) {
	        // TODO Auto-generated method stub
	        return position;
	    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		 View v = convertView;
         if (v == null)
         {
            LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.activity_costgroup_listview_item, null);
         }
 
          TextView name = (TextView) v.findViewById(R.id.textViewCostelementName);
          TextView value = (TextView) v.findViewById(R.id.textViewCostelementValue);
          TextView startDate = (TextView) v.findViewById(R.id.textViewCostelementDateStart);
          TextView endDate = (TextView) v.findViewById(R.id.textViewCostelementDateEnd);
          TextView periode = (TextView) v.findViewById(R.id.textViewCostelementPeriode);
          
          
          
          CostelementListViewItem item = data.get(position);
          
          name.setText(item.getName());
          
          String valueString = String.valueOf(item.getValue());
          value.setText(valueString);
          
          String dateStartString = item.getStartDate().toString();
          startDate.setText(dateStartString);
          
          if(item.getEndDate() != null) {
        	  
          
          String dateEndString = String.valueOf(item.getEndDate());
          endDate.setText(dateEndString);
          
          periode.setText(item.getName());
          
          }
                        
        return v;
		
	}
	
	
	

}
