package io.github.lordscales91.dbpopulator.utils;

import java.util.Collection;
import java.util.Iterator;

public class StringUtils {
	public static String listToString(Collection<?> list) {
		StringBuilder sb = new StringBuilder("[");
		Iterator<?> it = list.iterator();
		while(it.hasNext()) {
			sb.append(it.next().toString());
			if(it.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
