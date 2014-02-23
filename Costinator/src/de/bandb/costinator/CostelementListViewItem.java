package de.bandb.costinator;

import java.util.Date;

public class CostelementListViewItem {

	private String name;
	private double value;
	private Date startDate;
	private Date endDate;
	private String periode;

	public CostelementListViewItem(String name, double value, Date startDate,
			Date endDate, String periode) {

		this.name = name;
		this.value = value;
		this.startDate = startDate;
		this.endDate = endDate;
		this.periode = periode;

	}
	
	public CostelementListViewItem(String name, double value, Date startDate) {

		this.name = name;
		this.value = value;
		this.startDate = startDate;
		

	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
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

}
