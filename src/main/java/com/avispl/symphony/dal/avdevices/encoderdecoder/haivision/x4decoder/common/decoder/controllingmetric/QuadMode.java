/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Set of quad mode option
 *
 * @author Harry
 * @since 1.0
 */
public enum QuadMode {

	NORMAL("Normal",0),
	QUAD_MODE_2SI("QuadMode2SI",1);

	private final String name;
	private final Integer code;

	/**
	 *Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 * @param code  Code of decoder quad mode
	 */
	QuadMode(String name, Integer code) {
		this.name = name;
		this.code = code;
	}

	/**
	 * retrieve {@code {@link #name}}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Retrieves {@code {@link #code}}
	 *
	 * @return value of {@link #code}
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 *
	 * @return list of quad mode
	 */
	public static List<String> getQuadModeList() {
		List<String> list = new LinkedList<>();
		for (QuadMode quadMode : QuadMode.values()) {
			list.add(quadMode.getName());
		}
		return list;
	}

	/**
	 * Retrieves name to value map of quad mode drop down
	 *
	 * @return Map<Integer, String> are name and value
	 */
	public static Map<Integer, String> getNameToValueMap() {
		Map<Integer, String> nameToValue = new HashMap<>();
		for (QuadMode quadMode : QuadMode.values()) {
			nameToValue.put(quadMode.getCode(), quadMode.getName());
		}
		return nameToValue;
	}
}

