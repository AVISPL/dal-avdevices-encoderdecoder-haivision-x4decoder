/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common;

/**
 * Set of device info metric
 *
 * @author Harry
 * @since 1.0
 */
public enum DeviceInfoMetric {

	// Static metric
	SERIAL_NUMBER("Serial Number"),
	HARDWARE_REVISION("Hardware Revision"),
	PART_NUMBER("Part Number"),
	FIRMWARE_VERSION("Firmware Version"),

	// To Do: Can be Historical metric in next version
	TEMPERATURE("Temperature");
	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 */
	DeviceInfoMetric(String name) {
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

