/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.decoder.controllingmetric;

import java.util.Arrays;
import java.util.Optional;

/**
 * Set of decoder state option
 *
 * @author Harry
 * @since 1.0
 */
public enum State {

	STOPPED("Stopped", 0, false),
	START("Started", 1, true),
	ACTIVE("Active", 2, false),
	NOT_DECODING("Not Decoding", -1, false);

	private final String name;
	private final Integer code;
	private final boolean isRunning;

	/**
	 * Parameterized constructor
	 * @param name Name of decoder monitoring metric
	 * @param code Code of decoder status
	 * @param isRunning status of decoder
	 */
	State(String name, Integer code, boolean isRunning) {
		this.name = name;
		this.code = code;
		this.isRunning = isRunning;
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
	 * Retrieves {@code {@link #isRunning}}
	 *
	 * @return value of {@link #isRunning}
	 */
	public boolean isRunning() {
		return isRunning;
	}

	/**
	 * This method is used to get state by name
	 *
	 * @param name is the name of state that want to get
	 * @return State is the state that want to get
	 */
	public static State getByCode(Integer name) {
		Optional<State> state = Arrays.stream(State.values()).filter(com -> com.getCode().equals(name)).findFirst();
		return state.orElse(State.STOPPED);
	}
}

