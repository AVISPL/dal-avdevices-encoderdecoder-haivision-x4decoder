/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.decoder.controllingmetric;

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
public enum State {

	STOPPED("Stopped", 0, false),
	START("Started", 1, true),
	ACTIVE("Active", 2, false),
	NOT_DECODING("Not Decoding", -1, false);

	private final String name;
	private final Integer code;
	private final boolean isRunning;

	/**
	 *Parameterized constructor
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
	 *
	 * @return list of state mode
	 */
	public static List<String> getStateList() {
		List<String> list = new LinkedList<>();
		for (State state : State.values()) {
			list.add(state.getName());
		}
		return list;
	}

	/**
	 * Retrieves name to value map of State drop down
	 *
	 * @return Map<Integer, String> are name and value
	 */
	public static Map<Integer, String> getNameToValueMap() {
		Map<Integer, String> nameToValue = new HashMap<>();
		for (State state : State.values()) {
			nameToValue.put(state.getCode(), state.getName());
		}
		return nameToValue;
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

