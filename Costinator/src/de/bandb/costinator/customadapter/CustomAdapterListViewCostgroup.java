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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class CustomAdapterListViewCostgroup extends BaseAdapter {
	
	private ArrayList<CostelementListViewItem> data;
	private Context c;
	private ListView parent;

	public CustomAdapterListViewCostgroup (ArrayList<CostelementListViewItem> data, Context c){
		this.data 	= data;
		this.c 		= c; 
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		 View v = convertView;
         if (v == null)
         {
            LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.activity_costgroup_listview_item, null);
         }
         
         this.parent = (ListView) parent;
 
          TextView name = 		(TextView) v.findViewById(R.id.textViewCostelementName);
          TextView desc  = 		(TextView) v.findViewById(R.id.textViewCostelementDesc);
          TextView value = 		(TextView) v.findViewById(R.id.textViewCostelementValue);
          TextView periode =	(TextView) v.findViewById(R.id.textViewCostelementPeriode);
          TextView tolerance = 	(TextView) v.findViewById(R.id.textViewCostelementToleranz);
          TextView currency = 	(TextView) v.findViewById(R.id.textViewCostelementCurrency);
          ImageButton delete  = (ImageButton) v.findViewById(R.id.imageButtonCostelementDelete);
          
          delete.setOnClickListener(new OnClickListener() {
        	  @Override
        	  public void onClick(View v)
        	   {	
        		  	delete(position);
        		  	notifyDataSetChanged();
        	   }
        	});
          
          CostelementListViewItem item = data.get(position);
          
          name.setText(item.getName());
          desc.setText(item.getDesc());
          value.setText(item.getValue());
          periode.setText(item.getPeriode());
          tolerance.setText(item.getTolerance());
          currency.setText(item.getCurrency());              
        return v;	
	}
	
	public void delete(int position) {
    	parent.getOnItemLongClickListener().onItemLongClick(null, null, position, 0);
    }
}
