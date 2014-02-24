package de.bandb.costinator.CustomAdapter;



public class CostelementListViewItem {

	private String name;
	private double value;
	private String startDate;
	private String endDate;
	private String periode;

	public CostelementListViewItem(String name, double value, String startDate,
			String endDate, String periode) {

		this.name = name;
		this.value = value;
		this.startDate = startDate;
		this.endDate = endDate;
		this.periode = periode;

	}
	
	public CostelementListViewItem(String name, double value, String startDate) {

		this.name = name;
		this.value = value;
		this.startDate = startDate;
		

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}

	
}
