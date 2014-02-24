package de.bandb.costinator.database.entities;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 * 
 * Entitiy-class that describes the databasetable T_COSTELEMENT
 * possible periods:
 * 1 - dayly
 * 2 - weekly
 * 3 - monthly
 * 4 - quart
 * 5 - yearly
 */


@DatabaseTable(tableName="T_COSTELEMENT")
public class TCostelement implements Serializable {

	private static final long serialVersionUID = 3725841290753976725L;
	
	@DatabaseField(columnName="C_ID", generatedId=true)
	private int id;
	@DatabaseField(columnName="C_NAME")
	private String name;
	@DatabaseField(columnName="C_DESCRIPTION", canBeNull=true)
	private String description;
	@DatabaseField(columnName="C_VALUE")
	private double value;
	@DatabaseField(columnName="C_PERIOD")
	private int period;
	@DatabaseField(columnName="C_TOLERANCE", canBeNull=true)
	private int tolerance;
	@DatabaseField(columnName="C_COSTGROUP", foreign=true)
	private TCostgroup costgroup;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}
	public TCostgroup getCostgroup() {
		return costgroup;
	}
	public void setCostgroup(TCostgroup costgroup) {
		this.costgroup = costgroup;
	}
}
