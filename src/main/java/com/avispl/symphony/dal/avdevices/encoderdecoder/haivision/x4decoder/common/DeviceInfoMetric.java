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
	UPTIME("Uptime"),
	CARD_STATUS("CardStatus"),
	SERIAL_NUMBER("SerialNumber"),
	HARDWARE_COMPATIBILITY("HardwareCompatibility"),
	MEZZANINE_PRESENT("MezzaninePresent"),
	HARDWARE_REVISION("HardwareRevision"),
	CPLD_REVISION("CpldRevision"),
	BOOT_VERSION("BootVersion"),
	CARD_TYPE("CardType"),
	PART_NUMBER("PartNumber"),
	FIRMWARE_DATE("FirmwareDate"),
	FIRMWARE_VERSION("FirmwareVersion"),
	FIRMWARE_OPTIONS("FirmwareOptions"),

	// TODO: Can be Historical metric in next version
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

