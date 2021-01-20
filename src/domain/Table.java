/***********************************************************************
 * Module:  Table.java
 * Author:  Reda
 * Purpose: Defines the Class Table
 ***********************************************************************/
package domain;

import java.util.*;

public class Table {
	private String name;
	public ArrayList<Attribute> attributes;

	public Table(String name, ArrayList<Attribute> attributes) {
		super();
		this.name = name;
		this.attributes = attributes;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(ArrayList<Attribute> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Table : " + this.getName() + "\n");
		sb.append("Attributs : " + "\n");
		for (Attribute attribute : this.getAttributes())
			sb.append(attribute + "\n");
		return sb.toString();
	}
	
	public ArrayList<Attribute> getPrimaryKey(){
		ArrayList<Attribute> pkList = new ArrayList<Attribute>();
		for (Attribute attribute : this.getAttributes()) {
			if (attribute.getIsPrimaryKey())
				pkList.add(attribute);
		}
		return pkList;
	}

	public ArrayList<Attribute> getPurePrimaryKey(){
		ArrayList<Attribute> pkList = new ArrayList<Attribute>();
		for (Attribute attribute : this.getAttributes()) {
			if (attribute.getIsPrimaryKey() && !attribute.getIsForeignKey())
				pkList.add(attribute);
		}
		return pkList;
	}

	public ArrayList<Attribute> getNonPrimaryKeyAttributes(){
		ArrayList<Attribute> attList = new ArrayList<Attribute>();
		for (Attribute attribute : this.getAttributes()) {
			if (!attribute.getIsPrimaryKey())
				attList.add(attribute);
		}
		return attList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((attributes == null) ? 0 : attributes.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Table other = (Table) obj;
		if (attributes == null) {
			if (other.attributes != null)
				return false;
		} else if (!attributes.equals(other.attributes))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public Table clone() throws CloneNotSupportedException {
		ArrayList<Attribute> listAttributes = new ArrayList<>();
		for (Attribute attribute : this.attributes)
			listAttributes.add(attribute.clone());
		return new Table(this.name, listAttributes);
	}
}