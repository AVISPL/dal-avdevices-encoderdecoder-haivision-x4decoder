/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Set of still image mode option
 *
 * @author Harry
 * @since 1.0
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
	 *Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
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
	 *
	 * @return list of still image mode
	 */
	public static List<String> getStillImageList() {
		List<String> list = new LinkedList<>();
		for (StillImage bufferingMode : StillImage.values()) {
			list.add(bufferingMode.getName());
		}
		return list;
	}

	/**
	 * Retrieves name to value map of buffering mode drop down
	 *
	 * @return Map<Integer, String> are name and value
	 */
	public static Map<Integer, String> getNameToValueMap() {
		Map<Integer, String> nameToValue = new HashMap<>();
		for (StillImage stillImage : StillImage.values()) {
			nameToValue.put(stillImage.getCode(), stillImage.getName());
		}
		return nameToValue;
	}

	/**
	 * This method is used to get still image by name
	 *
	 * @param name is the name of still image that want to get
	 * @return StillImage is the still image that want to get
	 */
	public static StillImage getByName(String name) {
		Optional<StillImage> stillImage = Arrays.stream(StillImage.values()).filter(com -> com.getName().equals(name)).findFirst();
		return stillImage.orElse(null);
	}
}

