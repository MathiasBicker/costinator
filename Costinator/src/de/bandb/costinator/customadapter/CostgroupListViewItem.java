package de.bandb.costinator.customadapter;

public class CostgroupListViewItem {
	
	private String costgroupTitle;
	private String totalCost;
	
	public CostgroupListViewItem (String costgroupTitle, String totalCost) {
		
		this.costgroupTitle= costgroupTitle;
		this.totalCost= totalCost;
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
