/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Set of fec RTP option
 *
 * @author Harry
 * @since 1.0
 */
public enum FecRTP {

	DISABLE("disable", 0),
	MPEG_PRO_FEC("MPEG PRO FEC", 2);

	private final String name;
	private final Integer code;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
	 * @param code Code of decoder fec rtp mode
	 */
	FecRTP(String name, Integer code) {
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
	 * @return list of encapsulation
	 */
	public static List<String> getFecRTPList() {
		List<String> list = new LinkedList<>();
		for (FecRTP fecRTP : FecRTP.values()) {
			list.add(fecRTP.getName());
		}
		return list;
	}

	/**
	 * Retrieves name to value map of encapsulation mode drop down
	 *
	 * @return Map<Integer, String> are name and value
	 */
	public static Map<Integer, String> getNameToValueMap() {
		Map<Integer, String> nameToValue = new HashMap<>();
		for (FecRTP fecRTP : FecRTP.values()) {
			nameToValue.put(fecRTP.getCode(), fecRTP.getName());
		}
		return nameToValue;
	}

	/**
	 * This method is used to get encapsulation mode by name
	 *
	 * @param name is the name of encapsulation mode that want to get
	 * @return Encapsulation is the protocol that want to get
	 */
	public static FecRTP getByName(String name) {
		Optional<FecRTP> fecRTP = Arrays.stream(FecRTP.values()).filter(com -> com.getName().equals(name)).findFirst();
		return fecRTP.orElse(FecRTP.DISABLE);
	}
}
