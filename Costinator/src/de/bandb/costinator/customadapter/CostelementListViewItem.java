package de.bandb.costinator.customadapter;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

public class CostelementListViewItem {	
	private String name;
	private String desc;
	private double value;
	private String tolerance;
	private String periode;
	private String currency;

	public CostelementListViewItem(String name, String desc, double value, String currency, String periode, String tolerance) {
		this(name, desc, value, currency, periode);
		this.tolerance = tolerance;
	}
	
	public CostelementListViewItem(String name, String desc, double value, String currency, String periode) {
		this.name = name;
		this.desc = desc;
		this.value = value;
		this.currency = currency;
		this.periode = periode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public String getTolerance() {
		return tolerance;
	}

	public void setTolerance(String tolerance) {
		this.tolerance = tolerance;
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}	
}
