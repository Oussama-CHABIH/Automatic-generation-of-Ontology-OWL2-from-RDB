/***********************************************************************
 * Module:  Attribute.java
 * Author:  Reda
 * Purpose: Classe représentant un attribut d'une table
 ***********************************************************************/
package domain;

public class Attribute {
	private String name;
	private int size;
	private Boolean isUnique;
	private Boolean canBeNull;
	private Boolean isPrimaryKey;
	private Type type;
	private Boolean isForeignKey;
	private String foreignKeyRefTable;
	private String defautValue;
	private String roleName;
	private Double entropy ;
	private Double entropy_percentage;

	public Attribute(String name, int size, Boolean isUnique,
			Boolean canBeNull, Boolean isPrimaryKey, Type type,
			Boolean isForeignKey, String foreignKeyRefTable,
			String defautValue, String roleName ,Double entropy,
			Double entropy_percentage) {
		super();
		this.name = name;
		this.size = size;
		this.isUnique = isUnique;
		this.canBeNull = canBeNull;
		this.isPrimaryKey = isPrimaryKey;
		this.type = type;
		this.isForeignKey = isForeignKey;
		this.foreignKeyRefTable = foreignKeyRefTable;
		this.defautValue = defautValue;
		this.roleName = roleName;
		this.entropy = entropy;
		this.entropy_percentage = entropy_percentage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Boolean getIsUnique() {
		return isUnique;
	}

	public void setIsUnique(Boolean isUnique) {
		this.isUnique = isUnique;
	}

	public Boolean getCanBeNull() {
		return canBeNull;
	}

	public void setCanBeNull(Boolean canBeNull) {
		this.canBeNull = canBeNull;
	}

	public Boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(Boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDefautValue() {
		return defautValue;
	}

	public void setDefautValue(String defautValue) {
		this.defautValue = defautValue;
	}

	public java.lang.Boolean getIsForeignKey() {
		return isForeignKey;
	}

	public void setIsForeignKey(java.lang.Boolean isForeignKey) {
		this.isForeignKey = isForeignKey;
	}

	public java.lang.String getForeignKeyRefTable() {
		return foreignKeyRefTable;
	}

	public void setForeignKeyRefTable(java.lang.String foreignKeyRefTable) {
		this.foreignKeyRefTable = foreignKeyRefTable;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
	public Double getEntropy() {
		return entropy;
	}

	public void setEntropy(Double entropy) {
		this.entropy = entropy;
	}
	
	public Double getEntropy_percentage() {
		return entropy_percentage;
	}

	public void setEntropy_percentage(Double entropy) {
		this.entropy_percentage = entropy;
	}

	@Override
	public String toString() {
		return "Attribute [name=" + name + ", size=" + size + ", isUnique="
				+ isUnique + ", canBeNull=" + canBeNull + ", isPrimaryKey="
				+ isPrimaryKey + ", type=" + type + ", isForeignKey="
				+ isForeignKey + ", foreignKeyRefTable=" + foreignKeyRefTable
				+ ", defautValue=" + defautValue + ", roleName=" + roleName
				+ ", entropy=" + entropy + ", entropy_percentage=" + entropy_percentage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((canBeNull == null) ? 0 : canBeNull.hashCode());
		result = prime * result
				+ ((defautValue == null) ? 0 : defautValue.hashCode());
		result = prime
				* result
				+ ((foreignKeyRefTable == null) ? 0 : foreignKeyRefTable
						.hashCode());
		result = prime * result
				+ ((isForeignKey == null) ? 0 : isForeignKey.hashCode());
		result = prime * result
				+ ((isPrimaryKey == null) ? 0 : isPrimaryKey.hashCode());
		result = prime * result
				+ ((isUnique == null) ? 0 : isUnique.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((roleName == null) ? 0 : roleName.hashCode());
		result = prime * result + size;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Attribute other = (Attribute) obj;
		if (canBeNull == null) {
			if (other.canBeNull != null)
				return false;
		} else if (!canBeNull.equals(other.canBeNull))
			return false;
		if (defautValue == null) {
			if (other.defautValue != null)
				return false;
		} else if (!defautValue.equals(other.defautValue))
			return false;
		if (foreignKeyRefTable == null) {
			if (other.foreignKeyRefTable != null)
				return false;
		} else if (!foreignKeyRefTable.equals(other.foreignKeyRefTable))
			return false;
		if (isForeignKey == null) {
			if (other.isForeignKey != null)
				return false;
		} else if (!isForeignKey.equals(other.isForeignKey))
			return false;
		if (isPrimaryKey == null) {
			if (other.isPrimaryKey != null)
				return false;
		} else if (!isPrimaryKey.equals(other.isPrimaryKey))
			return false;
		if (isUnique == null) {
			if (other.isUnique != null)
				return false;
		} else if (!isUnique.equals(other.isUnique))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roleName == null) {
			if (other.roleName != null)
				return false;
		} else if (!roleName.equals(other.roleName))
			return false;
		if (size != other.size)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public Attribute clone() throws CloneNotSupportedException {
		return new Attribute(this.name, this.size, this.isUnique, this.canBeNull, this.isPrimaryKey, this.type, this.isForeignKey, this.foreignKeyRefTable, this.defautValue, this.roleName, this.entropy, this.entropy_percentage);
	}
}