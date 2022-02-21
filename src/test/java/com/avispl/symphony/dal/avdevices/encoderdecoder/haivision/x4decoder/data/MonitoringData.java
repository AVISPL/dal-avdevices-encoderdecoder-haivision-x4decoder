/*
 *  Copyright (c) 2021 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.data;

/**
 * This class used to define all values of device info monitoring metrics
 *
 * @author Harry
 * @since 1.0
 */
public enum MonitoringData {
	DECODER_ID("1"),
	SERIAL_NUMBER("HAI-032024020197"),
	HARDWARE_REVISION("A"),
	PART_NUMBER("S-MX4D-SDI"),
	FIRMWARE_VERSION("1.3.0-47");

	private final String data;

	/**
	 * Parameterized constructor
	 *
	 * @param data response data of monitoring
	 */
	MonitoringData(String data) {
		this.data = data;
	}

	/**
	 * retrieve {@code {@link #data }}
	 *
	 * @return value of {@link #data}
	 */
	public String getData() {
		return this.data;
	}
}
