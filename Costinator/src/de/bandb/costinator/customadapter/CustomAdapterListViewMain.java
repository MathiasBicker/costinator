package de.bandb.costinator.customadapter;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.ArrayList;

import de.bandb.costinator.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class CustomAdapterListViewMain extends BaseAdapter {

	
		 
	    private ArrayList<CostgroupListViewItem> 	data;
	    private Context 							c;
	    private ListView							parent;
	    
	    
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
	         
	         this.parent = (ListView) parent;
	         if(!(data.isEmpty())) {
		         CostgroupListViewItem 	item 		= data.get(position);
		         
		         delete.setOnClickListener(new OnClickListener()
		         {
		        	  @Override
		        	  public void onClick(View v)
		        	   {	
		        		  	delete(position);
		        		  	notifyDataSetChanged();
		        	   }
		        	});
		         
		         title.setText(item.getCostgroupTitle());
		         System.out.println("bla " + item.getCostgroupTitle());
		         desc.setText(item.getCostgroupDesc());
		         if(item.getTotalCost() != null)
			         if(!(item.getTotalCost().equals(""))) 
			        	 totalCost.setText(item.getTotalCost() + item.getCurrency());             
	         }
	         return v;
	    }
	    
	    public void delete(int position) {
	    	parent.getOnItemLongClickListener().onItemLongClick(null, null, position, 0);
	    }
}
	

