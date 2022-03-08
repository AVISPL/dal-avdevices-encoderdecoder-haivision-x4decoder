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
public enum NetworkType {

	UNI_CAST("Unicast"),
	MULTI_CAST("Multicast");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
	 */
	NetworkType(String name) {
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
		for (NetworkType type : NetworkType.values()) {
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
	public static NetworkType getByName(String name) {
		Optional<NetworkType> type = Arrays.stream(NetworkType.values()).filter(com -> com.getName().equals(name)).findFirst();
		return type.orElse(NetworkType.UNI_CAST);
	}
}

