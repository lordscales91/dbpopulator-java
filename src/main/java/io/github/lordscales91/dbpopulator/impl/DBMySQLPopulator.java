package io.github.lordscales91.dbpopulator.impl;

import io.github.lordscales91.dbpopulator.model.DatabaseDescriptor;
import io.github.lordscales91.dbpopulator.model.PopulatedEntry;

import java.util.List;
import java.util.Map;

public class DBMySQLPopulator extends DBSQLPopulatorBase {

	@Override
	public boolean isSupported(String dbtype) {
		return DatabaseDescriptor.DbTypes.MYSQL.equals(dbtype);
	}

	@Override
	public void submit(List<PopulatedEntry> entries, Map<String, Object> extraparams) {
		// Not implemented yet
		throw new UnsupportedOperationException();
	}

}
