/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common;

/**
 * Set of stream monitoring metrics keys
 *
 * @author Harry
 * @since 1.0
 */
public enum StreamConfigMetric {
	PORT("Port Number");

	private final String name;

	/**
	 *Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 */
	StreamConfigMetric(String name) {
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
}

