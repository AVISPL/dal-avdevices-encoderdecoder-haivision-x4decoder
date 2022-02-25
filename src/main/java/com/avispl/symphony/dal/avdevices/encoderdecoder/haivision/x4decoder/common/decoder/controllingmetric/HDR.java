/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Set of still image mode option
 *
 * @author Harry
 * @since 1.0
 */
public enum HDR {

	DISABLED("Disabled", -1),
	AUTO("Auto", 0),
	FORCE_HLG("ForceHLG", 1),
	FORCE_PQ("ForcePQ", 2);

	private final String name;
	private final Integer code;

	/**
	 *Parameterized constructor
	 *
	 * @param name Name of Decoder monitoring metric
	 * @param code  Code of decoder HDR mode
	 */
	HDR(String name, Integer code) {
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
	 * @return list of HDR mode
	 */
	public static List<String> getHDRList() {
		List<String> list = new LinkedList<>();
		for (HDR hdr : HDR.values()) {
			list.add(hdr.getName());
		}
		return list;
	}

	/**
	 * Retrieves name to value map of HDR drop down
	 *
	 * @return Map<Integer, String> are name and value
	 */
	public static Map<Integer, String> getNameToValueMap() {
		Map<Integer, String> nameToValue = new HashMap<>();
		for (HDR hdr : HDR.values()) {
			nameToValue.put(hdr.getCode(), hdr.getName());
		}
		return nameToValue;
	}

	/**
	 * This method is used to get HDR mode by name
	 *
	 * @param name is the name of HDR mode that want to get
	 * @return HDR is the HDR that want to get
	 */
	public static HDR getByName(String name) {
		Optional<HDR> hdr = Arrays.stream(HDR.values()).filter(com -> com.getName().equals(name)).findFirst();
		return hdr.orElse(null);
	}
}

