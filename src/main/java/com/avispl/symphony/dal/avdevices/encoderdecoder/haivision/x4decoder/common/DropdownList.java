package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.avispl.symphony.api.dal.error.ResourceNotReachableException;

/**
 * DropdownList
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/11/2022
 * @since 1.0.0
 */
public class DropdownList {
	/**
	 * Get all name of enum to String Array
	 *
	 * @param enumType the enumtype is enum class
	 */
	public static <T extends Enum<T>> List<String> Names(Class<T> enumType) {
		List<String> names = new ArrayList<>();
		for (T c : enumType.getEnumConstants()) {
			try {
				Method method = c.getClass().getMethod("getName");
				String name = (String) method.invoke(c); // getName executed
				names.add(name);
			} catch (Exception e) {
				throw new ResourceNotReachableException("Error to convert enum " + enumType.getSimpleName() + " to names");
			}
		}
		return names;
	}
}
