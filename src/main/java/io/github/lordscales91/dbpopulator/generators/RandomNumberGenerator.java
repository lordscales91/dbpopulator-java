package io.github.lordscales91.dbpopulator.generators;

import io.github.lordscales91.dbpopulator.model.FieldDescriptor;
import io.github.lordscales91.dbpopulator.model.PopulatedField;

import java.util.Map;
import java.util.Random;

public class RandomNumberGenerator implements Generator {
	
	private Random rand = new Random();

	@Override
	public PopulatedField generate(FieldDescriptor field,
			Map<String, Object> extraparams) {
		int max = Integer.MAX_VALUE;
		if(extraparams != null && extraparams.containsKey("maxvalue")) {
			max = (int) extraparams.get("maxvalue");
		}
		if(FieldDescriptor.DataTypes.DECIMAL.equals(field.getDataType())) {
			// This should be improved
			return new PopulatedField(field.getDbtable(), field.getName(),
					field.getDataType(), myRandom(0, max));
		}
		return new PopulatedField(field.getDbtable(), field.getName(), field.getDataType(), 
				rand.nextInt(max));
	}
	
	/**
	 * See http://stackoverflow.com/a/27531793/3107765
	 */
	private double myRandom(double min, double max) {
	    return (rand.nextInt((int)((max-min)*10+1))+min*10) / 10.0;
	}

	@Override
	public boolean isSupported(String fieldType) {
		return FieldDescriptor.FieldTypes.NUMBER.equals(fieldType);
	}

}
