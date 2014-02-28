package de.bandb.costinator.customadapter;

import de.bandb.costinator.database.entities.TCostgroup;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

public class CostgroupListViewItem {
	
	private String 	costgroupTitle;
	private String 	costgroupDesc;
	private String 	currency;
	private String 	totalCost;
	private int 	id;
	
	public CostgroupListViewItem (String costgroupTitle, String costgroupDesc , String totalCost) {
		this.costgroupTitle= costgroupTitle;
		this.costgroupDesc= costgroupDesc;
		this.totalCost= totalCost;
	}
	
	public CostgroupListViewItem(TCostgroup c, String currency) {
		costgroupTitle = c.getName();
		if(c.getDescription() != null)
			costgroupDesc = c.getDescription();
		else
			costgroupDesc = "";
		if(c.getMonthlyCost() != 0.0)
			totalCost = String.valueOf(c.getMonthlyCost());
		else
			costgroupDesc = "";
		this.currency = currency;
		id = c.getId();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCostgroupDesc() {
		return costgroupDesc;
	}

	public void setCostgroupDesc(String costgroupDesc) {
		this.costgroupDesc = costgroupDesc;
	}

	public CostgroupListViewItem (String costgroupTitle) {
		
		this.costgroupTitle= costgroupTitle;
	}
	

	public String getCostgroupTitle() {
		return costgroupTitle;
	}

	public void setCostgroupTitle(String costgroupTitle) {
		this.costgroupTitle = costgroupTitle;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
}
