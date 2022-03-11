/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.controllingmetric;

import java.util.Arrays;
import java.util.Optional;

/**
 * Set of still image mode option
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
public enum HDR {

	DISABLED("Disabled", -1),
	AUTO("Auto", 0),
	FORCE_HLG("ForceHLG", 1),
	FORCE_PQ("ForcePQ", 2);

	private final String name;
	private final Integer code;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
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
	 * This method is used to get HDR mode by name
	 *
	 * @param name is the name of HDR mode that want to get
	 * @return HDR is the HDR that want to get
	 */
	public static HDR getByName(String name) {
		Optional<HDR> hdr = Arrays.stream(HDR.values()).filter(com -> com.getName().equals(name)).findFirst();
		return hdr.orElse(HDR.AUTO);
	}
}

