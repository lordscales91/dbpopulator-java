package io.github.lordscales91.dbpopulator.model;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Class that describes a table field
 * @author Lordscales91
 */
public class FieldDescriptor {
	
	// Standard constants containers
	
	/**
	 * Container for standard data types
	 */
	public static class DataTypes {
		public static final String STRING = "string";
		public static final String INTEGER = "integer";
		public static final String DECIMAL = "decimal";
	}
	/**
	 * Container for standard field types 
	 */
	public static class FieldTypes {
		public static final String USERNAME = "username"; // johnsmith 
		public static final String NAME = "name"; // John Smith
		public static final String FIRSTNAME = "firstname"; // John
		public static final String LASTNAME = "lastname"; // Smith
		public static final String TEXT = "text"; // Generic text
		public static final String NUMBER = "number"; // A random integer
	}
	
	// Class fields
	private String name;
	private String dbtable; // Set by the table descriptor
	private String fieldType; // defines how the field will be populated (name, address, email, etc...)
	private String dataType; // string, integer, decimal, etc...

	public FieldDescriptor(String name, String fieldType, String dataType) {		
		this.name = name;
		this.fieldType = fieldType;
		this.dataType = dataType;
	}
	
	public FieldDescriptor(String name, String fieldType) {
		this(name, fieldType, DataTypes.STRING);
	}
	

	// Getters/Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFieldType() {
		return fieldType;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDbtable() {
		return dbtable;
	}

	public void setDbtable(String dbtable) {
		this.dbtable = dbtable;
	}

	public static FieldDescriptor fromJson(JsonNode fdobject) {
		FieldDescriptor fd = null;
		if(fdobject.has("name") && fdobject.has("fieldType")) {
			fd = new FieldDescriptor(fdobject.get("name").asText(),fdobject.get("fieldType").asText());
			if(fdobject.has("dataType")) {
				fd.setDataType(fdobject.get("dataType").asText());
			}
		}
		
		return fd;
	}
	
	@Override
	public String toString() {
		return String.format("<%s name: %s, fieldType: %s, dataType: %s>",
				getClass().getSimpleName(), name, fieldType, dataType);
	}
}
