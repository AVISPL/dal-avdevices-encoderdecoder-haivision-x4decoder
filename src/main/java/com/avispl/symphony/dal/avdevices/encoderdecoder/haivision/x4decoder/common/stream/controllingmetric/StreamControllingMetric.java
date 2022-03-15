/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.controllingmetric;

import java.util.Arrays;
import java.util.Optional;

/**
 * Set of stream controlling metric keys
 *
 * @author Harry / Symphony Dev Team<br>
 * Created on 3/8/2022
 * @since 1.0.0
 */
public enum StreamControllingMetric {

	STREAM_NAME("StreamName"),
	ENCAPSULATION("Protocol"),
	MULTICAST_ADDRESS("MulticastAddress"),
	SOURCE_ADDRESS("SourceAddress"),
	DESTINATION_PORT("DestinationPort"),
	REJECT_UNENCRYPTED_CALLERS("RejectUnencryptedCallers"),
	NETWORK_TYPE("Type"),
	ADDRESS("Address"),
	FEC_RTP("FecRtp"),
	ID("Id"),
	LATENCY("Latency"),
	PASSPHRASE("Passphrase"),
	PORT("Port"),
	SOURCE_PORT("SourcePort"),
	SRT_MODE("SrtMode"),
	SRT_TO_UDP_STREAM_CONVERSION("SrtToUdpStreamConversion"),
	SRT_TO_UDP_ADDRESS("SrtToUdpAddress"),
	SRT_TO_UDP_PORT("SrtToUdpPort"),
	SRT_TO_UDP_TOS("SrtToUdpTos"),
	SRT_TO_UDP_TTL("SrtToUdpTtl"),
	ENCRYPTED("Encrypted"),
	CREATE("Create"),
	DELETE("Delete"),
	UPDATE("Update"),
	APPLY_CHANGE("ApplyChange"),
	CANCEL("Cancel"),
	EDITED("Edited");

	private final String name;

	/**
	 * Parameterized constructor
	 *
	 * @param name Name of decoder monitoring metric
	 */
	StreamControllingMetric(String name) {
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
	 * This method is used to get decoder controlling metric by name
	 *
	 * @param name is the name of decoder controlling metric that want to get
	 * @return DecoderControllingMetric is the decoder controlling metric that want to get
	 */
	public static StreamControllingMetric getByName(String name) {
		Optional<StreamControllingMetric> streamControllingMetric = Arrays.stream(StreamControllingMetric.values()).filter(com -> com.getName().equals(name)).findFirst();
		return streamControllingMetric.orElse(null);
	}
}

