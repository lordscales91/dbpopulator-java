package io.github.lordscales91.dbpopulator.model;

import io.github.lordscales91.dbpopulator.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * Class that describes a database table (the name and its fields)
 * @author Lordscales91
 */
public class TableDescriptor {
	private String name;
	private List<FieldDescriptor> fields;

	public TableDescriptor(String name, List<FieldDescriptor> fields) {
		this.name = name;
		this.fields = fields;
	}
	
	public TableDescriptor(String name) {
		this(name, null);
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<FieldDescriptor> getFields() {
		return fields;
	}

	public void setFields(List<FieldDescriptor> fields) {
		this.fields = fields;
	}

	public static TableDescriptor fromJson(JsonNode tableobject) {
		TableDescriptor tbl = null;
		if(tableobject.has("name")) {
			tbl = new TableDescriptor(tableobject.get("name").asText());
		}
		if(tableobject.has("fields")) {
			tbl.fields = new ArrayList<FieldDescriptor>();
			ArrayNode fieldArr = (ArrayNode) tableobject.get("fields");
			Iterator<JsonNode> it = fieldArr.iterator();
			while(it.hasNext()) {
				FieldDescriptor fd = FieldDescriptor.fromJson(it.next());
				fd.setDbtable(tbl.getName());
				tbl.fields.add(fd);
			}
		}
		return tbl;
	}
	@Override
	public String toString() {
		return String.format("<%s name: %s, fields: %s>", 
				getClass().getSimpleName(),
				name, StringUtils.listToString(fields));
	}
}
