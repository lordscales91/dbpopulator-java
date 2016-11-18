package io.github.lordscales91.dbpopulator.model;

/**
 * This class represents a populated database field. It will be grouped in populated entries.
 */
public class PopulatedField {
	
	private String dbtable;
	private String name;
	private String dataType;
	private Object value;
	
	public PopulatedField(String dbtable, String name, String dataType) {
		this.dbtable = dbtable;
		this.name = name;
		this.dataType = dataType;
	}
	
	public PopulatedField(String dbtable, String name, String dataType, Object value) {
		this.dbtable = dbtable;
		this.name = name;
		this.value = value;
		this.dataType = dataType;
	}
	
	public static PopulatedField fromDescriptor(FieldDescriptor desc) {
		return new PopulatedField(desc.getDbtable(), desc.getName(), desc.getDataType());
	}

	public String getDbtable() {
		return dbtable;
	}

	public void setDbtable(String dbtable) {
		this.dbtable = dbtable;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getValue() {
		return value;
	}
	
	public String getValueAsString() {
		return value.toString();
	}
	
	public int getValueAsInt() {
		return (int) value;
	}
	
	public double getValueAsDouble() {
		return (double) value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	@Override
	public String toString() {
		return String.format(
				"<PopulatedField dbtable: %s, name: %s, value: %s>", dbtable,
				name, value);
	}
	
}
