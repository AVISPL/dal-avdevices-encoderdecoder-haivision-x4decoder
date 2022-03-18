/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric;

import java.util.Arrays;
import java.util.Optional;

/**
 * Set of fec RTP option
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
public enum FecRTP {

	DISABLE("Disable", 0),
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
	 * This method is used to get fec rtp mode by name
	 *
	 * @param name is the name of fec rtp mode that want to get
	 * @return FecRTP is the protocol that want to get
	 */
	public static FecRTP getByName(String name) {
		Optional<FecRTP> fecRTP = Arrays.stream(FecRTP.values()).filter(com -> com.getName().equals(name)).findFirst();
		return fecRTP.orElse(FecRTP.DISABLE);
	}
}

