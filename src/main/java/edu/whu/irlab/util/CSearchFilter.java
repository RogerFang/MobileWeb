package edu.whu.irlab.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Map.Entry;

public class CSearchFilter {

	public enum COperator {
		EQ, LIKE, GT, LT, GTE, LTE, NEQ, NOTNULL, NOTEMPTY
	}

	public String fieldName;
	public Object value;
	public COperator operator;

	public CSearchFilter(String fieldName, COperator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * searchParams OPERATOR_FIELDNAME
	 */
	public static Map<String, CSearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, CSearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (StringUtils.isBlank((String) value)) {
				continue;
			}

			String[] names = StringUtils.split(key, "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			COperator operator = COperator.valueOf(names[0]);

			CSearchFilter filter = new CSearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
}
