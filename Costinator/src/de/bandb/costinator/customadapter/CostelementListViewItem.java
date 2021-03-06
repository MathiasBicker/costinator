package de.bandb.costinator.customadapter;

import java.io.Serializable;

import de.bandb.costinator.database.entities.TCostelement;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 * proxy class for TCostelement that only contains Strings that can be displayed plus the id
 */

public class CostelementListViewItem implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5570690110208248210L;
	private String 	name;
	private String 	desc;
	private String 	value;
	private String 	tolerance;
	private String 	periode;
	private String 	currency;
	private int 	id;

	public CostelementListViewItem(String name, String desc, String value, String currency, String periode, String tolerance) {
		this(name, desc, value, currency, periode);
		setTolerance(tolerance);
	}
	
	public CostelementListViewItem(String name, String desc, String value, String currency, String periode) {
		this.name = name;
		this.desc = desc;
		this.value = value;
		this.currency = currency;
		this.periode = periode;
	}
	
	public CostelementListViewItem (TCostelement c, String period, String currency) {
		name = c.getName();
		if(c.getDescription() != null)
			desc = c.getDescription();
		else
			desc = "";
		if(c.getValue() * 10 % 1.0 == 0)
			value = String.valueOf(c.getValue()) + "0";
		else if (c.getValue() * 100 % 1.0 == 0)
			value = String.valueOf(c.getValue()) + ".00";
		else
			value = String.valueOf(c.getValue());
		tolerance = (String.valueOf(c.getTolerance()) + "%");
		periode 		= period;
		this.currency  	= currency;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTolerance() {
		return tolerance;
	}

	public void setTolerance(String tolerance) {
		if(tolerance == null)
			this.tolerance = "0%";
		else if(tolerance.charAt(tolerance.length() - 1) == '%') {
			this.tolerance = tolerance;
			return;
		}
		else if(tolerance.substring(tolerance.length()-2).equals("--"))
			this.tolerance = "0%";
		else
			this.tolerance = tolerance + "%";
	}

	public String getPeriode() {
		return periode;
	}

	public void setPeriode(String periode) {
		this.periode = periode;
	}	
}
