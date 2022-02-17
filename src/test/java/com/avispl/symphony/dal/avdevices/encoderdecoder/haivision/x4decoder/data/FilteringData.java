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
public enum FilteringData {
	INVALID_INPUT("is invalid or not exist");


	private final String data;

	/**
	 * Parameterized constructor
	 *
	 * @param data response data of monitoring
	 */
	FilteringData(String data) {
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
