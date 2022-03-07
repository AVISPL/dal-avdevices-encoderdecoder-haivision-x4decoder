/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.test.x4decoder.common.stream.controllingmetric;

import java.util.Arrays;
import java.util.Optional;

/**
 * Set of decoder controlling metric keys
 *
 * @author Harry
 * @since 1.0
 */
public enum StreamControllingMetric {

	STREAM_NAME("StreamName"),
	ENCAPSULATION("Protocol"),
	MULTICAST_ADDRESS("MulticastAddress"),
	SOURCE_ADDRESS("SourceAddress"),
	DESTINATION_PORT("DestinationPort"),
	REJECT_UNENCRYPTED_CALLERS("RejectUnencryptedCallers"),
	TYPE("Type"),
	SRT_TO_UDP_STREAM_CONVERSION("SrtToUdpStreamConversion"),
	ADDRESS("Address"),
	DECODER_ID("DecoderId"),
	FEC_RTP("FecRtp"),
	ID("Id"),
	LATENCY("Latency"),
	PASSPHRASE("Passphrase"),
	PASSPHRASE_SET("PassphraseSet"),
	PORT("Port"),
	SOURCE_IP("SourceIp"),
	SOURCE_PORT("SourcePort"),
	SRT_MODE("SrtMode"),
	SRT_TO_UDP("SrtToUdp"),
	SRT_TO_UDP_ADDRESS("SrtToUdp_address"),
	SRT_TO_UDP_PORT("SrtToUdp_port"),
	SRT_TO_UDP_TOS("SrtToUdp_tos"),
	SRT_TO_UDP_TTL("SrtToUdp_ttl"),
	STRICT_MODE("StrictMode"),
	USER_DATA("UserData"),
	ENCRYPTED("Encrypted"),
	CREATE("Create"),
	DELETE("Delete"),
	UPDATE("Update");

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

