package de.bandb.costinator.customadapter;

import java.util.ArrayList;

import de.bandb.costinator.R;
import de.bandb.costinator.R.id;
import de.bandb.costinator.R.layout;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomAdapterListViewMain extends BaseAdapter {

	
		 
	    private ArrayList<CostgroupListViewItem> data;
	    private Context c;
	    
	    public CustomAdapterListViewMain (ArrayList<CostgroupListViewItem> data, Context c){
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
	   
	    public View getView(int position, View convertView, ViewGroup parent) {
	        // TODO Auto-generated method stub
	         View v = convertView;
	         if (v == null)
	         {
	            LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            v = vi.inflate(R.layout.activity_main_listview_item, null);
	         }
	 
	          TextView title = (TextView) v.findViewById(R.id.textViewCostgroupTitle);
	          TextView totalCost = (TextView) v.findViewById(R.id.textViewCostgroupTotalCost);
	        
	          
	          CostgroupListViewItem item = data.get(position);
	          
	          title.setText(item.getCostgroupTitle());
	          totalCost.setText(item.getTotalCost());
	                        
	        return v;
	}

		
	}
	

