/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.data;

/**
 * This class used to define all values of exception messages
 *
 * @author Harry
 * @since 1.0
 */
public enum ExceptionMessage {
	GETTING_SESSION_ID_ERRO("Username and Password are incorrect"),
	GETTING_DECODER_STATS_ERR("failed to get decoder statistic3\n"
			+ "failed to get decoder statistic1\n"
			+ "failed to get decoder statistic2\n"
			+ "failed to get decoder statistic0\n"),
	GETTING_STREAM_STATS_ERR("failed to get stream statistic\n");

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

