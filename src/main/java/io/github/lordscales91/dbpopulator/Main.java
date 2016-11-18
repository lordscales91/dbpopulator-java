package io.github.lordscales91.dbpopulator;

import io.github.lordscales91.dbpopulator.model.DatabaseDescriptor;
import io.github.lordscales91.dbpopulator.model.PopulatedEntry;
import io.github.lordscales91.dbpopulator.model.PopulatedField;
import io.github.lordscales91.dbpopulator.utils.StringUtils;

import java.util.List;

/**
 * Eventually this will be the entry point for the runnable version of 
 * this library, now it's used as a sandbox to test the code.
 * Yeah, I know that I should use unit tests for that, but this is 
 * simpler and quicker to do, and is also one of my bad habits (^_^). 
 * @author Lordscales91
 */
public class Main {

	public static void main(String[] args) {
		DatabaseDescriptor db = DatabaseDescriptor.load();
		System.out.println(db);
		DBPopulator dbp = new DBPopulator.Builder(db).build();
		List<PopulatedEntry> entries = dbp.generate();
		System.out.println(StringUtils.listToString(entries));
		PopulatedField testField = null;
		for(PopulatedEntry entry: entries) {
			for(PopulatedField fd:entry.getFields()) {
				if(fd.getDataType().equals("decimal")) {
					testField = fd;
					break;
				}				
			}
			if(testField != null) {
				break;
			}
		}
		double test = testField.getValueAsDouble();
		System.out.println(test);
		dbp.asScript(entries, "test.sql");
		// int inttest = testField.getValueAsInt(); // As expected this fails with a ClassCastException
		// System.out.println(inttest);
	}

}
