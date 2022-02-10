/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for QSYSCoreCommunicator
 *
 * Send and retrieve data success
 * Failed retrieve data
 * Existing Extended Statistics
 *
 * @author Harry
 * @version 1.0
 * @since 1.0
 */
public class HaivisionX4DecoderCommunicatorTest {
	private  final  HaivisionX4DecoderCommunicator haivisionX4DecoderCommunicator = new HaivisionX4DecoderCommunicator();

	@BeforeEach()
	public void setUp() throws Exception {
		haivisionX4DecoderCommunicator.setHost("mx4d.demo.haivision.com");
		haivisionX4DecoderCommunicator.setPort(443);
		haivisionX4DecoderCommunicator.setLogin("operator");
		haivisionX4DecoderCommunicator.setPassword("supervisor");
		haivisionX4DecoderCommunicator.init();
		haivisionX4DecoderCommunicator.connect();
	}

	@AfterEach()
	public void destroy() throws Exception {
		haivisionX4DecoderCommunicator.disconnect();
	}

	@Test
	void  testHaivisionX4DecoderCommunicatorLoginSuccess() throws Exception {

	}
}
