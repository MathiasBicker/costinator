package de.bandb.costinator.customadapter;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

public class CostgroupListViewItem {
	
	private String costgroupTitle;
	private String costgroupDesc;
	private String totalCost;
	
	public CostgroupListViewItem (String costgroupTitle, String costgroupDesc , String totalCost) {
		this.costgroupTitle= costgroupTitle;
		this.costgroupDesc= costgroupDesc;
		this.totalCost= totalCost;
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
