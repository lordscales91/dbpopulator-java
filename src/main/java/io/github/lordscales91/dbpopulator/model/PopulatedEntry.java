package io.github.lordscales91.dbpopulator.model;


import io.github.lordscales91.dbpopulator.utils.StringUtils;

import java.util.Iterator;
import java.util.List;

/**
 * A class to group together all the populated fields of a specific entity (it's like a row)
 */
public class PopulatedEntry {
	
	private String dbtable;
	private int requiredFields; // The minimum number of fields to be populated
	private int numFields; // The number of fields
	private List<PopulatedField> fields;
	
	public PopulatedEntry(String dbtable, int numFields,
			List<PopulatedField> fields) {
		this.dbtable = dbtable;
		this.numFields = numFields;
		this.requiredFields = numFields;
		this.fields = fields;
	}

	
	public boolean isValid() {
		return this.fields != null && this.fields.size() >= this.requiredFields
				&& this.fields.size() <= this.numFields;
	}
	
	public boolean isComplete() {
		return this.fields != null && this.fields.size() == this.numFields;
	}

	
	public String getFieldNames() {
		StringBuilder sb = new StringBuilder();
		Iterator<PopulatedField> it = fields.iterator();
		while(it.hasNext()) {
			sb.append(it.next().getName());
			if(it.hasNext()) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	public String getDbtable() {
		return dbtable;
	}

	public void setDbtable(String dbtable) {
		this.dbtable = dbtable;
	}

	public List<PopulatedField> getFields() {
		return fields;
	}

	public void setFields(List<PopulatedField> fields) {
		this.fields = fields;
	}

	public int getRequiredFields() {
		return requiredFields;
	}

	public int getNumFields() {
		return numFields;
	}

	@Override
	public String toString() {
		return String.format("<PopulatedEntry dbtable: %s, requiredFields: %s,"
							+ " numFields: %s, fields: %s>",
						dbtable, requiredFields, numFields, StringUtils.listToString(fields));
	}
	

}
