/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.stream.controllingmetric;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Set of SRT option
 *
 * @author Harry
 * @since 1.0
 */
public enum SRTMode {

	CALLER("Caller", 0),
	LISTENER("Listener", 1),
	RENDEZVOUS("Rendezvous", 2);

	private final String name;
	private final Integer code;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
	 * @param code  Code of decoder SRT mode
	 */
	SRTMode(String name, Integer code) {
		this.name = name;
		this.code = code;
	}

	/**
	 * retrieve {@code {@link #name}}
	 *
	 * @return value of {@link #name}
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Retrieves {@code {@link #code}}
	 *
	 * @return value of {@link #code}
	 */
	public Integer getCode() {
		return code;
	}

	/**
	 *
	 * @return list of srt mode
	 */
	public static List<String> getSRTModeList() {
		List<String> list = new LinkedList<>();
		for (SRTMode srtMode : SRTMode.values()) {
			list.add(srtMode.getName());
		}
		return list;
	}

	/**
	 * This method is used to get srt mode by name
	 *
	 * @param name is the name of srt mode that want to get
	 * @return SRTMode is the srt that want to get
	 */
	public static SRTMode getByName(String name) {
		Optional<SRTMode> srtMode = Arrays.stream(SRTMode.values()).filter(com -> com.getName().equals(name)).findFirst();
		return srtMode.orElse(SRTMode.LISTENER);
	}
}

