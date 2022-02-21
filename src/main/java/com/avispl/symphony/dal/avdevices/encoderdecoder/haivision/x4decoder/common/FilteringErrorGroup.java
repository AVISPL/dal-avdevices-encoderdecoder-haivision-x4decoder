/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common;

/**
 * Set of Filtering Errors Group
 *
 * @author Harry
 * @since 1.0
 */
public enum FilteringErrorGroup {

	STREAM_NAME("Mismatch Stream Name Filter"),
	STREAM_STATUS_AND_PORT_NUMBER("Mismatch Stream Status And Port Number");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 */
	FilteringErrorGroup(String name) {
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

