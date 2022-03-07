/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Set of output frame rate option
 *
 * @author Harry
 * @since 1.0
 */
public enum OutputFrameRate {

	AUTO("Automatic", 0),
	OUTPUT_FRAME_RATE_60 ("60 fps",60),
	OUTPUT_FRAME_RATE_59 ("59.94 fps",59),
	OUTPUT_FRAME_RATE_50 ("50 fps",50),
	OUTPUT_FRAME_RATE_30 ("30 fps",30),
	OUTPUT_FRAME_RATE_29 ("29.97 fps", 29),
	OUTPUT_FRAME_RATE_25 ("25 fps", 25),
	OUTPUT_FRAME_RATE_24 ("24 fps", 24),
	OUTPUT_FRAME_RATE_23 ("23.98 fps",23);

	private final String name;
	private final Integer code;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
	 * @param code  Code of decoder output frame rate
	 */
	OutputFrameRate(String name, Integer code) {
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
	 * @return list of output frame rate mode
	 */
	public static List<String> getOutputFrameRateList() {
		List<String> list = new LinkedList<>();
		for (OutputFrameRate outputFrameRate : OutputFrameRate.values()) {
			list.add(outputFrameRate.getName());
		}
		return list;
	}

	/**
	 * This method is used to get output frame rate by name
	 *
	 * @param name is the name of output frame rate that want to get
	 * @return OutputFrameRate is the output frame rate that want to get
	 */
	public static OutputFrameRate getByName(String name) {
		Optional<OutputFrameRate> outputFrameRate = Arrays.stream(OutputFrameRate.values()).filter(com -> com.getName().equals(name)).findFirst();
		return outputFrameRate.orElse(OutputFrameRate.AUTO);
	}
}

