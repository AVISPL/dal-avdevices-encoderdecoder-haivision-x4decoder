/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common;

/**
 * Set of monitoring group metrics keys
 *
 * @author Harry
 * @since 1.0
 */
public enum MonitoringMetricGroup {

	DECODER_STATISTICS("Decoder Statistics"),
	STREAM_STATISTICS("Stream Statistics"),
	DEVICE_INFO("Device Info");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 */
	MonitoringMetricGroup(String name) {
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
