/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.data;

/**
 * This class used to define all values of exception messages
 *
 * @author Harry
 * @since 1.0
 */
public enum ExceptionMessage {
	GETTING_SESSION_ID_ERRO("Username and Password are incorrect"),
	GETTING_DEVICE_INFO("Failed to get device info\n"),
	GETTING_SYSTEM_INFO("Failed to get system info\n"),
	GETTING_DECODER_STATS_ERR("Failed to get decoder statistic3\n"
			+ "Failed to get decoder statistic1\n"
			+ "Failed to get decoder statistic2\n"
			+ "Failed to get decoder statistic0\n"),
	GETTING_STREAM_STATS_ERR("Failed to get stream statistic\n");

	private final String message;

	/**
	 * Parameterized constructor
	 *
	 * @param message Exception message
	 */
	ExceptionMessage(String message) {
		this.message = message;
	}

	/**
	 * retrieve {@code {@link #message }}
	 *
	 * @return value of {@link #message}
	 */
	public String getMessage() {
		return this.message;
	}
}

