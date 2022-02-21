/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common;

/**
 * Set of monitoring group metrics keys
 *
 * @author Harry
 * @since 1.0
 */
public enum MonitoringMetricGroup {

	DECODER_STATISTICS("Decoder Statistics", true),
	STREAM_STATISTICS("Stream Statistics", true),
	DEVICE_INFO("Device Info", true);

	private final String name;
	private final boolean isFailedMonitorCheck;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 */
	MonitoringMetricGroup(String name, boolean isFailedMonitor) {
		this.name = name;
		this.isFailedMonitorCheck = isFailedMonitor;
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
	 *
	 * @return isFailedMonitorCheck
	 */
	public boolean isFailedMonitorCheck() {
		return isFailedMonitorCheck;
	}
}
