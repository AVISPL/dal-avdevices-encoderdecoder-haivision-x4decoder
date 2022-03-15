/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.decoder.controllingmetric;

import java.util.Arrays;
import java.util.Optional;

/**
 * Set of still image mode option
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
public enum StillImage {

	FREEZE("Freeze", 1),
	BLACK("Black", 2),
	BLUE("Blue", 3),
	COLOR_BARS("ColorBars", 4),
	MUTE("Mute", 5);

	private final String name;
	private final Integer code;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
	 * @param code  Code of decoder still image
	 */
	StillImage(String name, Integer code) {
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
	 * This method is used to get still image by name
	 *
	 * @param name is the name of still image that want to get
	 * @return StillImage is the still image that want to get
	 */
	public static StillImage getByName(String name) {
		Optional<StillImage> stillImage = Arrays.stream(StillImage.values()).filter(com -> com.getName().equals(name)).findFirst();
		return stillImage.orElse(StillImage.FREEZE);
	}
}

