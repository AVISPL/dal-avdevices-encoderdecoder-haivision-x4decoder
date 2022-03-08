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
public enum ControllingData {
	DECODER_ID("1"),
	OUTPUT_2("Output2");

	private final String data;

	/**
	 * Parameterized constructor
	 *
	 * @param data response data of monitoring
	 */
	ControllingData(String data) {
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
