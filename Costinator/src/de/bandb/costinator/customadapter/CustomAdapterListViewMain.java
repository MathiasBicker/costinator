package de.bandb.costinator.customadapter;

import java.util.ArrayList;

import de.bandb.costinator.Main;
import de.bandb.costinator.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class CustomAdapterListViewMain extends BaseAdapter {

	
		 
	    private ArrayList<CostgroupListViewItem> 	data;
	    private Context 							c;
	    
	    public CustomAdapterListViewMain (ArrayList<CostgroupListViewItem> data, Context c) {
	        this.data = data;
	        this.c = c;
	    }
	   
	    public int getCount() {
	        return data.size();
	    }
	    
	    public Object getItem(int position) {
	        return data.get(position);
	    }
	 
	    public long getItemId(int position) {
	        return position;
	    }
	   
	    public View getView(final int position, View convertView, ViewGroup parent) {
	         View v = convertView;
	         if (v == null) {
	            LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	            v = vi.inflate(R.layout.activity_main_listview_item, null);
	         }
	 
	         TextView 				title 		= (TextView) v.findViewById(R.id.textViewCostgroupTitle);
	         TextView				desc		= (TextView) v.findViewById(R.id.textViewCostgroupDesc);
	         TextView 				totalCost 	= (TextView) v.findViewById(R.id.textViewCostgroupTotalCost);
	         ImageButton			delete		= (ImageButton) v.findViewById(R.id.imageButtonCostgroupDelete);
	         
	         CostgroupListViewItem 	item 		= data.get(position);
	         
	         delete.setOnClickListener(new OnClickListener()
	         {
	        	  @Override
	        	  public void onClick(View v)
	        	   {	
	        
	        		  	data.remove(position);
	        		  	notifyDataSetChanged();
	        	   }
	        	});
	         
	         title.setText(item.getCostgroupTitle());
	         desc.setText(item.getCostgroupDesc());
	         totalCost.setText(item.getTotalCost());
	                        
	         return v;
	    }
}
	

