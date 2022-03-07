/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Set of buffering mode option
 *
 * @author Harry
 * @since 1.0
 */
public enum BufferingMode {

	AUTO("Auto", 3),
	FIXED("Fixed", 2),
	MULTI_SYNC("Multi Sync", 4);

	private final String name;
	private final Integer code;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
	 * @param code  Code of decoder buffering mode
	 */
	BufferingMode(String name, Integer code) {
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
	 * @return list of buffering mode
	 */
	public static List<String> getBufferingList() {
		List<String> list = new LinkedList<>();
		for (BufferingMode bufferingMode : BufferingMode.values()) {
			list.add(bufferingMode.getName());
		}
		return list;
	}

	/**
	 * This method is used to get buffering mode by name
	 *
	 * @param name is the name of buffering mode that want to get
	 * @return BufferingMode is the buffering mode that want to get
	 */
	public static BufferingMode getByName(String name) {
		Optional<BufferingMode> bufferingMode = Arrays.stream(BufferingMode.values()).filter(com -> com.getName().equals(name)).findFirst();
		return bufferingMode.orElse(BufferingMode.AUTO);
	}
}

