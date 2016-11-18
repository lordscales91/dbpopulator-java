package io.github.lordscales91.dbpopulator.generators;

import io.github.lordscales91.dbpopulator.model.FieldDescriptor;
import io.github.lordscales91.dbpopulator.model.PopulatedField;

import java.util.Map;

public interface Generator {
	public PopulatedField generate(FieldDescriptor field, Map<String, Object> extraparams);

	public boolean isSupported(String fieldType);
}
