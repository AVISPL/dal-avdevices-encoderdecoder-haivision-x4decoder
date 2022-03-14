/*
 *  Copyright (c) 2021 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common;

/**
 * Set of constants
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
public class DecoderConstant {

	public static final char HASH = '#';
	public static final char NEXT_LINE = '\n';
	public static final char SLASH = '/';
	public static final String COMMA = ",";
	public static final String RIGHT_PARENTHESES = ")";
	public static final String DASH = "-";
	public static final String SPACE =  " ";
	public static final String EMPTY = "";
	public static final String HTTPS = "https://";
	public static final String NONE = "None";
	public static final String AUTHORIZED = "Authorized";
	public static final String GETTING_DEVICE_INFO_ERR = "Failed to get device info";
	public static final String GETTING_SYSTEM_INFO_ERR = "Failed to get system info";
	public static final String GETTING_DECODER_STATS_ERR = "Failed to get decoder statistic";
	public static final String GETTING_STREAM_STATS_ERR = "Failed to get stream statistic";
	public static final String GETTING_SESSION_ID_ERR = "Username and Password are incorrect";
	public static final String ROLE_BASED_ERR = "Role based is empty";
	public static final String PORT_NUMBER_ERROR = "Invalid port number";
	public static final String DECODER_CONTROL_ERR = "Failed to control decoder: ";
	public static final String CREATE_STREAM_CONTROL_ERR = "Failed to create stream: ";
	public static final String APPLY_CHANGE_STREAM_CONTROL_ERR = "Failed to control stream: ";
	public static final String DELETE_STREAM_CONTROL_ERR = "Failed to control stream: ";
	public static final String PASSWORD = "password";
	public static final String USERNAME = "username";
	public static final String OPERATOR_ROLE = "Operator";
	public static final String ADMIN_ROLE = "Administrator";
	public static final String SESSION_ID = "Set-Cookie";
	public static final String COOKIE = "Cookie";
	public static final String DAY = " day(s) ";
	public static final String HOUR = " hour(s) ";
	public static final String MINUTE = " minute(s) ";
	public static final String SECOND = " second(s) ";
	public static final String AUDIO_PAIR = "Decoder Audio Pair";
	public static final String DEFAULT_STREAM_ID = "-1";
	public static final String ENABLE = "Enable";
	public static final String DISABLE = "Disable";
	public static final String ON = "On";
	public static final String OFF = "Off";
	public static final String APPLY = "Apply";
	public static final String CANCEL = "Cancel";
	public static final String APPLYING = "Applying";
	public static final String CANCELLING = "Canceling";
	public static final String CREATE = "Create";
	public static final String CREATING = "Creating";
	public static final String DELETE = "Delete";
	public static final String DELETING = "Deleting";
	public static final String ADDRESS_ANY = "Any";
	public static final String SRT_TO_UDP_TOS = "0x80";
	public static final int MIN_DECODER_ID = 0;
	public static final int MAX_DECODER_ID = 4;
	public static final Integer MIN_STILL_IMAGE_DELAY = 1;
	public static final Integer MAX_STILL_IMAGE_DELAY = 1000;
	public static final Integer MIN_BUFFERING_DELAY = 0;
	public static final Integer MAX_BUFFERING_DELAY = 3000;
	public static final Integer MIN_MULTI_SYNC_BUFFERING_DELAY = 0;
	public static final Integer MAX_MULTI_SYNC_BUFFERING_DELAY = 10000;
	public static final Integer MIN_PORT = 1025;
	public static final Integer MAX_PORT = 65535;
	public static final Integer MIN_LATENCY = 20;
	public static final Integer MAX_LATENCY = 8000;
}
