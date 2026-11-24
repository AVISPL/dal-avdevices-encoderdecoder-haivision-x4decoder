/*
 * Copyright (c) 2026 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.NormalizeData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.decoder.monitoringmetric.DecoderMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.stream.monitoringmetric.StreamMonitoringMetric;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderstats.DecoderData;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.decoderstats.DecoderStats;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.Stream;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.streamstats.StreamStats;

/**
 * Enumerated state/mode values are reported with consistent capitalization (SYAL-2286).
 */
@Tag("unitTest")
public class ValueFormattingTest {

	@Test
	void testFormatEnumValue() {
		Assertions.assertEquals("Automatic", NormalizeData.formatEnumValue("AUTOMATIC"));
		Assertions.assertEquals("Unspecified", NormalizeData.formatEnumValue("UNSPECIFIED"));
		Assertions.assertEquals("Not Decoding", NormalizeData.formatEnumValue("NOT_DECODING"));
		Assertions.assertEquals("Unspecified", NormalizeData.formatEnumValue("Unspecified"));
		Assertions.assertEquals("H.264", NormalizeData.formatEnumValue("H.264"));
		Assertions.assertNull(NormalizeData.formatEnumValue(null));
	}

	@Test
	void testDecoderStateValuesFormatted() {
		DecoderStats stats = new DecoderStats();
		stats.setBufferingMode("AUTOMATIC");
		stats.setClockStatus("SEARCHING");
		DecoderData data = new DecoderData();
		data.setDecoderStats(stats);
		Assertions.assertEquals("Automatic", data.getValueByDecoderMonitoringMetric(DecoderMonitoringMetric.BUFFERING_MODE));
		Assertions.assertEquals("Searching", data.getValueByDecoderMonitoringMetric(DecoderMonitoringMetric.CLOCK_STATUS));
	}

	@Test
	void testStreamStateFormattedButFilterValueUnchanged() {
		StreamStats streamStats = new StreamStats();
		streamStats.setState(2);
		Stream stream = new Stream();
		stream.setStreamStats(streamStats);
		Assertions.assertEquals("Listening", stream.getValueByStreamMonitoringMetric(StreamMonitoringMetric.STATE));
		// the stream status filter compares against the raw value
		Assertions.assertEquals("LISTENING", streamStats.getState());
	}
}
