/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.deviceinfo;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Device info
 *
 * @author Harry
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceInfo {

	@JsonAlias("serialNumber")
	private String serialNumber;

	@JsonAlias("hardwareRevision")
	private String hardwareRevision;

	@JsonAlias("partNumber")
	private String partNumber;

	@JsonAlias("firmwareVersion")
	private String firmwareVersion;

	@JsonAlias("temperature")
	private String temperature;

	/**
	 * Retrieves {@code {@link #serialNumber}}
	 *
	 * @return value of {@link #serialNumber}
	 */
	public String getSerialNumber() {
		return serialNumber;
	}

	/**
	 * Sets {@code serialNumber}
	 *
	 * @param serialNumber the {@code java.lang.String} field
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * Retrieves {@code {@link #hardwareRevision}}
	 *
	 * @return value of {@link #hardwareRevision}
	 */
	public String getHardwareRevision() {
		return hardwareRevision;
	}

	/**
	 * Sets {@code hardwareRevision}
	 *
	 * @param hardwareRevision the {@code java.lang.String} field
	 */
	public void setHardwareRevision(String hardwareRevision) {
		this.hardwareRevision = hardwareRevision;
	}

	/**
	 * Retrieves {@code {@link #partNumber}}
	 *
	 * @return value of {@link #partNumber}
	 */
	public String getPartNumber() {
		return partNumber;
	}

	/**
	 * Sets {@code partNumber}
	 *
	 * @param partNumber the {@code java.lang.String} field
	 */
	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	/**
	 * Retrieves {@code {@link #firmwareVersion}}
	 *
	 * @return value of {@link #firmwareVersion}
	 */
	public String getFirmwareVersion() {
		return firmwareVersion;
	}

	/**
	 * Sets {@code firmwareVersion}
	 *
	 * @param firmwareVersion the {@code java.lang.String} field
	 */
	public void setFirmwareVersion(String firmwareVersion) {
		this.firmwareVersion = firmwareVersion;
	}

	/**
	 * Retrieves {@code {@link #temperature}}
	 *
	 * @return value of {@link #temperature}
	 */
	public String getTemperature() {
		return temperature;
	}

	/**
	 * Sets {@code temperature}
	 *
	 * @param temperature the {@code java.lang.String} field
	 */
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
}
