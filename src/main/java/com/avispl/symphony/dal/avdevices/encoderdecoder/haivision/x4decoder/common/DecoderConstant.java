/*
 *  Copyright (c) 2021 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common;

/**
 * Set of constants
 *
 * @author Harry
 * @since 1.0
 */
public class DecoderConstant {

	public static final char HASH = '#';
	public static final char COLON= ':';
	public static final char NEXT_LINE = '\n';
	public static final char SLASH = '/';
	public static final String SPACE =  " ";
	public static final String HTTPS = "https://";
	public static final String NONE = "None";
	public static final String AUTHORIZED = "Authorized";
	public static final String GETTING_DECODER_STATS_ERR = "failed to get decoder statistic";
	public static final String GETTING_STREAM_STATS_ERR = "failed to get stream statistic";
	public static final String GETTING_SESSION_ID_ERR = "Username and Password are incorrect";
	public static final String PASSWORD = "password";
	public static final String USERNAME = "username";
	public static final String SESSION_ID = "Set-Cookie";
	/**
	 * Token timeout is 1 hour ( 60 minutes), as this case reserve 5 minutes to make sure we never failed because of the timeout
	 */
	public static final long TIMEOUT = 55;

}
