/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric;

import java.util.Arrays;
import java.util.Optional;

/**
 * Set of quad mode option
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
public enum QuadMode {

	NORMAL("Normal", 0),
	QUAD_MODE_2SI("QuadMode2SI", 1);

	private final String name;
	private final Integer code;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
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
	 * This method is used to get quad mode by name
	 *
	 * @param name is the name of quad mode that want to get
	 * @return QuadMode is the quad mode that want to get
	 */
	public static QuadMode getByName(String name) {
		Optional<QuadMode> quadMode = Arrays.stream(QuadMode.values()).filter(com -> com.getName().equals(name)).findFirst();
		return quadMode.orElse(QuadMode.NORMAL);
	}
}

