/*
 * Copyright (c) 2022 AVI-SPL, Inc. All Rights Reserved.
 */

package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.avispl.symphony.api.dal.dto.monitor.ExtendedStatistics;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.MonitoringMetricGroup;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.StreamMonitoringMetric;

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
		System.out.println(stats.get(MonitoringMetricGroup.DECODER_STATISTICS.getName() + 1 + DecoderConstant.HASH + DecoderMonitoringMetric.DECODER_ID.getName()) );
		System.out.println(MonitoringMetricGroup.STREAM_STATISTICS.getName() + DecoderConstant.COLON + "SRT - WAN Listen (6518)" +
				DecoderConstant.HASH + StreamMonitoringMetric.ID.getName() + " " + stats.get(MonitoringMetricGroup.STREAM_STATISTICS.getName() + DecoderConstant.COLON + "SRT - WAN Listen (6518)" +
				DecoderConstant.HASH + StreamMonitoringMetric.ID.getName()));
		Assertions.assertNotNull(stats.get(MonitoringMetricGroup.DECODER_STATISTICS.getName() + 1 + DecoderConstant.HASH + DecoderMonitoringMetric.DECODER_ID.getName()));
		Assertions.assertNotNull(stats.get(MonitoringMetricGroup.STREAM_STATISTICS.getName() + DecoderConstant.COLON + "SRT - WAN Listen (6518)" +
				DecoderConstant.HASH + StreamMonitoringMetric.ID.getName()));
	}
}
