package io.github.lordscales91.dbpopulator.generators;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import io.github.lordscales91.dbpopulator.model.FieldDescriptor;
import io.github.lordscales91.dbpopulator.model.PopulatedField;

public class RandomTextGenerator implements Generator {
	
	private static final List<String> SUPPORTED_FIELDS = Arrays.asList(
			new String[] {FieldDescriptor.FieldTypes.TEXT});
	private static final String CHARACTER_BASE;
	private static Random rand = new Random();
	
	static {
		StringBuilder sb = new StringBuilder();
		for(char c='a';c<='z';c++) {
			sb.append(c);
		}
		sb.append(' ');
		for(char c='A';c<='Z';c++) {
			sb.append(c);
		}
		CHARACTER_BASE = sb.toString();
	}

	@Override
	public PopulatedField generate(FieldDescriptor field,
			Map<String, Object> extraparams) {
		int length = 20;
		if(extraparams != null && extraparams.containsKey("length")) {
			length = (int)extraparams.get("length");
		}
		StringBuilder sb = new StringBuilder();		
		for(int i=0;i<length;i++) {
			sb.append(CHARACTER_BASE.charAt(rand.nextInt(CHARACTER_BASE.length())));
		}		
		return new PopulatedField(field.getDbtable(), field.getName(), field.getDataType(), sb.toString());
	}

	@Override
	public boolean isSupported(String fieldType) {
		return SUPPORTED_FIELDS.contains(fieldType);
	}

}
