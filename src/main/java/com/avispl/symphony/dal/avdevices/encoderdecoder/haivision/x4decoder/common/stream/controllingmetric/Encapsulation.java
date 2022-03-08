/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Set of encapsulation (protocol) option
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
public enum Encapsulation {

	TS_OVER_UDP("TS over UDP", 2),
	TS_OVER_RTP("TS over RTP", 3),
	TS_OVER_SRT("TS over SRT", 34);

	private final String name;
	private final Integer code;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
	 * @param code  Code of decoder encapsulation mode
	 */
	Encapsulation(String name, Integer code) {
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
	 * @return list of encapsulation
	 */
	public static List<String> getEncapsulationList() {
		List<String> list = new LinkedList<>();
		for (Encapsulation encapsulation : Encapsulation.values()) {
			list.add(encapsulation.getName());
		}
		return list;
	}

	/**
	 * This method is used to get encapsulation mode by name
	 *
	 * @param name is the name of encapsulation mode that want to get
	 * @return Encapsulation is the protocol that want to get
	 */
	public static Encapsulation getByName(String name) {
		Optional<Encapsulation> encapsulation = Arrays.stream(Encapsulation.values()).filter(com -> com.getName().equals(name)).findFirst();
		return encapsulation.orElse(Encapsulation.TS_OVER_UDP);
	}
}

