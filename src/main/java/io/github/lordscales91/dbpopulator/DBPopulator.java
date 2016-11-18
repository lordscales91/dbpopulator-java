package io.github.lordscales91.dbpopulator;

import io.github.lordscales91.dbpopulator.generators.Generator;
import io.github.lordscales91.dbpopulator.generators.RandomNumberGenerator;
import io.github.lordscales91.dbpopulator.generators.RandomTextGenerator;
import io.github.lordscales91.dbpopulator.impl.DBMySQLPopulator;
import io.github.lordscales91.dbpopulator.model.DatabaseDescriptor;
import io.github.lordscales91.dbpopulator.model.FieldDescriptor;
import io.github.lordscales91.dbpopulator.model.PopulatedEntry;
import io.github.lordscales91.dbpopulator.model.PopulatedField;
import io.github.lordscales91.dbpopulator.model.TableDescriptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DBPopulator {

	protected DatabaseDescriptor descriptor;
	protected List<Generator> generators;

	/**
	 * Indicates if the passed dbtype is supported by the populator
	 */
	public abstract boolean isSupported(String dbtype);

	/**
	 * Populates the database
	 */
	public abstract void submit(List<PopulatedEntry> entries, Map<String, Object> extraparams);

	/**
	 * Saves populated fields as a script instead
	 */
	public abstract void asScript(List<PopulatedEntry> entries, String fileout, Map<String, Object> extraparams);
	
	private Map<String, Object> buildParams(Object[] extraparams) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(extraparams.length > 0 && extraparams.length % 2 == 0) {
			for(int i=0;i<extraparams.length;i+=2) {
				params.put(extraparams[i].toString(), extraparams[i+1]);
			}
		}
		return params;
	}
	
	// Helpful overloads
	public void submit(List<PopulatedEntry> entries) {
		this.submit(entries, new Object[]{});
	}
	
	public void submit(List<PopulatedEntry> entries, Object... extraparams) {
		this.submit(entries, buildParams(extraparams));
	}
	
	public void asScript(List<PopulatedEntry> entries) {
		this.asScript(entries, "script.sql");
	}

	public void asScript(List<PopulatedEntry> entries, String fileout) {
		this.asScript(entries, fileout, new Object[]{});
	}

	public void asScript(List<PopulatedEntry> entries, String fileout, Object... extraparams) {
		this.asScript(entries, fileout, buildParams(extraparams));
	}

	protected void init(DatabaseDescriptor desc, List<Generator> generators) {
		this.descriptor = desc;
		this.generators = generators;
	}

	public List<PopulatedEntry> generate() {
		return generate(10, new Object[]{});
	}
	
	public List<PopulatedEntry> generate(int num) { 
		return generate(num, new Object[]{});
	}
	
	public List<PopulatedEntry> generate(int num, Object... extraparams) {		
		return generate(num, buildParams(extraparams));
	}

	public List<PopulatedEntry> generate(int num,
			Map<String, Object> extraparams) {
		List<PopulatedEntry> entries = new ArrayList<PopulatedEntry>();
		for (TableDescriptor table : this.descriptor.getTables()) {
			// Create a populated entry.
			// Repeat to get the required number of entries.
			for (int i = 0; i < num; i++) {
				PopulatedEntry entry = new PopulatedEntry(table.getName(), table.getFields().size(), null);
				List<PopulatedField> fields = new ArrayList<PopulatedField>();
				for(FieldDescriptor fd:table.getFields()) {
					for(Generator gen:this.generators) {
						if(gen.isSupported(fd.getFieldType())) {
							fields.add(gen.generate(fd, extraparams));
							break; // Process each field only once.
						}
					}
				}
				entry.setFields(fields);
				entries.add(entry);
			}
			
		}
	
		return entries;
	}

	public static class Builder {

		private DatabaseDescriptor descriptor;
		private List<Generator> generators = null;
		private List<DBPopulator> populators = null;

		public Builder() {
			this(DatabaseDescriptor.load());
		}

		public Builder(DatabaseDescriptor descriptor) {
			this.descriptor = descriptor;
		}

		public Builder addPopulator(DBPopulator populator) {
			if (this.populators == null) {
				this.populators = new ArrayList<DBPopulator>();
			}
			this.populators.add(populator);
			return this;
		}
		
		public Builder addGenerator(Generator gen) {
			if (this.generators == null) {
				this.generators = new ArrayList<Generator>();
			}
			this.generators.add(gen);
			return this;
		}

		public DBPopulator build() {
			addDefaults();
			for (DBPopulator dbp : this.populators) {
				if (dbp.isSupported(this.descriptor.getDbtype())) {
					dbp.init(descriptor, generators);
					return dbp;
				}
			}
			return null;
		}

		private void addDefaults() {
			if (populators == null) {
				populators = new ArrayList<DBPopulator>();
			}
			populators.add(new DBMySQLPopulator());
			if (generators == null) {
				generators = new ArrayList<Generator>();
			}
			generators.add(new RandomTextGenerator());
			generators.add(new RandomNumberGenerator());
		}

	}
}
