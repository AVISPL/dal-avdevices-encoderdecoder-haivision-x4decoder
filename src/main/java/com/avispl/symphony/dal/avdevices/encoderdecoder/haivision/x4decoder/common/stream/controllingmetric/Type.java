/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Set of network type option
 *
 * @author Harry
 * @since 1.0
 */
public enum Type {

	UNI_CAST("Unicast"),
	MULTI_CAST("Multicast");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
	 */
	Type(String name) {
		this.name = name;
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
	 * @return list of network type
	 */
	public static List<String> getTypeList() {
		List<String> list = new LinkedList<>();
		for (Type type : Type.values()) {
			list.add(type.getName());
		}
		return list;
	}

	/**
	 * This method is used to get network type by name
	 *
	 * @param name is the name of network type that want to get
	 * @return Type is the network type that want to get
	 */
	public static Type getByName(String name) {
		Optional<Type> type = Arrays.stream(Type.values()).filter(com -> com.getName().equals(name)).findFirst();
		return type.orElse(Type.UNI_CAST);
	}
}

