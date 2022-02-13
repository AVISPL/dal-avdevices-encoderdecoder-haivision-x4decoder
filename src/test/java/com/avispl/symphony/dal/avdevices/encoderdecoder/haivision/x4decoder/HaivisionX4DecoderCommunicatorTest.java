/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import static org.mockito.Mockito.doReturn;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.MonitoringMetricGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.data.ExceptionMessage;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.data.MonitoringData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationCookie;

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

	/**
	 * Test HaivisionX4DecoderCommunicator.getMultipleStatistics successful with valid username password
	 * Expected retrieve valid device monitoring data
	 */
	@Tag("RealDevice")
	@Test
	void testHaivisionX4DecoderCommunicator() throws Exception {
		ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicator.getMultipleStatistics().get(0);
		Map<String, String> stats = extendedStatistics.getStatistics();

		String decoderStatisticGroup = MonitoringMetricGroup.DECODER_STATISTICS.getName() + DecoderConstant.SPACE + 1 + DecoderConstant.HASH;
		Assertions.assertEquals(MonitoringData.DECODER_ID.getData(),stats.get(decoderStatisticGroup + DecoderMonitoringMetric.DECODER_ID.getName()));
	}

	/**
	 * Test QSYSCoreCommunicator.getMultipleStatistics will not throw ResourceNotReachableException when Username or Password are incorrect
	 * Expected throw ResourceNotReachableException "Username and Password are incorrect";
	 */
	@Tag("RealDevice")
	@Test
	void testHaivisionX4DecoderCommunicatorLogInFailed() {
		HaivisionX4DecoderCommunicator haivisionX4DecoderCommunicatorSpy = Mockito.spy(HaivisionX4DecoderCommunicator.class);
		haivisionX4DecoderCommunicatorSpy.setHost("mx4d.demo.haivision.com");
		Mockito.when(haivisionX4DecoderCommunicatorSpy.getLogin()).thenReturn("123");
		Mockito.when(haivisionX4DecoderCommunicatorSpy.getPassword()).thenReturn("456");
		ResourceNotReachableException exception = Assertions.assertThrows(ResourceNotReachableException.class, () -> {
			ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicatorSpy.getMultipleStatistics().get(0);
		});
		Assertions.assertEquals(ExceptionMessage.GETTING_SESSION_ID_ERRO.getMessage(), exception.getMessage());
	}

	/**
	 * Test QSYSCoreCommunicator.getMultipleStatistics get errors when retrieving decoders statistic and streams statistics
	 * Expected throw ResourceNotReachableException
	 *  failed to get decoder statistic3
	 * 	failed to get decoder statistic1
	 * 	failed to get decoder statistic2
	 * 	failed to get decoder statistic0
	 * 	failed to get stream statistic
	 */
	@Tag("Mock")
	@Test
	void testQSysCoreCommunicatorDeviceHaveDataWithAccessControlDisable() {
		AuthenticationCookie authenticationCookie = new AuthenticationCookie();
		authenticationCookie.setSessionID(DecoderConstant.AUTHORIZED);
		HaivisionX4DecoderCommunicator haivisionX4DecoderCommunicatorSpy = Mockito.spy(new HaivisionX4DecoderCommunicator());
		doReturn(authenticationCookie).when(haivisionX4DecoderCommunicatorSpy).initAuthenticationCookie();
		haivisionX4DecoderCommunicatorSpy.setHost("mx4d.demo.haivision.com");

		ResourceNotReachableException exception = Assertions.assertThrows(ResourceNotReachableException.class, () -> {
			ExtendedStatistics extendedStatistics = (ExtendedStatistics) haivisionX4DecoderCommunicatorSpy.getMultipleStatistics().get(0);
		});
		Assertions.assertEquals(ExceptionMessage.GETTING_DECODER_STATS_ERR.getMessage() + ExceptionMessage.GETTING_STREAM_STATS_ERR.getMessage(), exception.getMessage());
	}
}
