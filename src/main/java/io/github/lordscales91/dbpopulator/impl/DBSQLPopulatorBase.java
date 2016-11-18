package io.github.lordscales91.dbpopulator.impl;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import io.github.lordscales91.dbpopulator.DBPopulator;
import io.github.lordscales91.dbpopulator.model.FieldDescriptor;
import io.github.lordscales91.dbpopulator.model.PopulatedEntry;
import io.github.lordscales91.dbpopulator.model.PopulatedField;

/**
 * Base class for SQL-based database populators. It implements the asScript method.
 */
public abstract class DBSQLPopulatorBase extends DBPopulator {


	@Override
	public void asScript(List<PopulatedEntry> entries, String fileout, Map<String, Object> extraparams) {
		String database = super.descriptor.getDbschema();
		try {
			PrintWriter pw = new PrintWriter(fileout, "UTF-8");
			pw.printf("USE %s;\n\n", database);
			for(PopulatedEntry e:entries) {
				pw.printf("INSERT INTO %s (%s) ", e.getDbtable(), e.getFieldNames());
				StringBuilder values = new StringBuilder();
				Iterator<PopulatedField> it = e.getFields().iterator();
				while(it.hasNext()) {
					PopulatedField val = it.next();
					if(val.getDataType().equals(FieldDescriptor.DataTypes.STRING)) {
						values.append(String.format("'%s'", val.getValueAsString()));
					} else if (val.getDataType().equals(FieldDescriptor.DataTypes.DECIMAL)) {
						 // TODO: Add other types and improve formating.
						values.append(String.format(Locale.ROOT, "%.08f", val.getValueAsDouble()));
					} else if (val.getDataType().equals(FieldDescriptor.DataTypes.INTEGER)) {
						values.append(String.format(Locale.ROOT, "%d", val.getValueAsInt()));
					}
					if(it.hasNext()) {
						values.append(", ");
					}
				}
				pw.printf("VALUES (%s);\n", values.toString());
			}
			pw.flush();
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
