/*
 *  Copyright (c) 2021 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.data;

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
	FIRMWARE_VERSION("1.3.0-47"),
	HARDWARE_COMPATIBILITY("-003G"),
	CPLD_REVISION("4 (Official, Internal flash)"),
	BOOT_VERSION("U-Boot 2018.01 (Apr 06 2021 - 14:11:03 -0400)"),
	CARD_TYPE("Makito X4 SDI Decoder"),
	FIRMWARE_DATE("Aug 17 2021");

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
