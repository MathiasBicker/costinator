package de.bandb.costinator.database.entities;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import de.bandb.costinator.CostgroupBusinessAssesment;
import de.bandb.costinator.customadapter.CostelementListViewItem;

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

	private static final long 	serialVersionUID = 3725841290753976725L;
	
	public static final String 	COSTGROUP 		= "C_COSTGROUP";
	public static final String 	BADTOLERANCE 	= "tolerance must be between 1 and 100";
	public static final int		DAYLY 			= 1;
	public static final int		WEEKLY 			= 2;
	public static final int		MONTHLY 		= 3;
	public static final int		QUART 			= 4;
	public static final int		YEARLY 			= 5;
	
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
	@DatabaseField(columnName=COSTGROUP, canBeNull=false, foreign=true)
	private TCostgroup costgroup;
	
	private double endvalue; 
	
	public TCostelement() {
	}
	
	public double getEndvalue() {
		return endvalue;
	}

	public void setEndvalue(double endvalue) {
		this.endvalue = endvalue;
	}

	public TCostelement(CostelementListViewItem c, int period, int tolerance) {
		this(c, period);
		setTolerance(tolerance);
	}
	
	public TCostelement(CostelementListViewItem c, int period) {
		name = c.getName();
		description = c.getDesc();
		value = Double.valueOf(c.getValue());
		setPeriod(period);
		tolerance = Integer.parseInt(c.getTolerance());
	}
	
	public int getTolerance() {
		return tolerance;
	}

	public void setTolerance(int tolerance) {
		if(tolerance > 100 || tolerance < 1)
			throw new RuntimeException(BADTOLERANCE);
		this.tolerance = tolerance;
	}

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
		if(period > YEARLY || period < DAYLY)
			throw new RuntimeException(CostgroupBusinessAssesment.WRONGPERIOD);
		this.period = period;
	}
	public TCostgroup getCostgroup() {
		return costgroup;
	}
	public void setCostgroup(TCostgroup costgroup) {
		this.costgroup = costgroup;
	}
	
	public String toString() {
		return "id: " + id + "; name: " + name + "; value: " + value + "; period: " + period + 
				"; tolerance: " + tolerance + "; group: " + costgroup.getId();
	}
}
