/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common;

import java.util.Arrays;
import java.util.Optional;

/**
 * Set of monitoring group metrics keys
 *
 * @author Harry
 * @since 1.0
 */
public enum ControllingMetricGroup {

	DECODER("Decoder"),
	STREAM("Stream");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 */
	ControllingMetricGroup(String name) {
		this.name = name;

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
	 * This method is used to get controlling metric by name
	 *
	 * @param name is the name of controlling metric that want to get
	 * @return ControllingMetric is the controlling metric group that want to get
	 */
	public static ControllingMetricGroup getByName(String name) {
		Optional<ControllingMetricGroup> controllingMetricGroup = Arrays.stream(ControllingMetricGroup.values()).filter(com -> com.getName().equals(name)).findFirst();
		return controllingMetricGroup.orElse(null);
	}

}
