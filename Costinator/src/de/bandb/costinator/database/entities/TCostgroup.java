package de.bandb.costinator.database.entities;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 * 
 * Entitiy-class that describes the databasetable T_COSTGROUP
 */

@DatabaseTable(tableName="T_COSTGROUP")
public class TCostgroup implements Serializable{

	private static final long serialVersionUID = 4224423552186186195L;
	
	@DatabaseField(columnName="C_ID", generatedId=true)
	private int id;
	@DatabaseField(columnName="C_NAME")
	private String name;
	@DatabaseField(columnName="C_DESCRIPTION", canBeNull=true)
	private String description;
	@DatabaseField(columnName="C_MONTHLYCOST", canBeNull=true)
	private double monthlyCost;
	
	public TCostgroup() {
	}
	
	public double getMonthlyCost() {
		return monthlyCost;
	}
	public void setMonthlyCost(double monthlyCost) {
		this.monthlyCost = monthlyCost;
	}
	private TCostelement[] elements;
	
	public TCostelement[] getElements() {
		return elements;
	}
	public void setElements(TCostelement[] elements) {
		this.elements = elements;
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
	
	public String toString() {
		return "id: " + id + "; name: " + name + "; cost: " + monthlyCost;
	}
}
