/*
 * Copyright (c) 2026 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationCookie;

/**
 * A monitoring cycle stops at the first timeout / unreachable error instead of issuing every remaining request, each
 * waiting for its own timeout, which delayed the next cycle well past the monitoring rate (SYAL-2597).
 */
@Tag("unitTest")
public class MonitoringCycleTimeoutTest {

	/** Communicator whose GET requests fail as configured, without a device. */
	static class TestCommunicator extends HaivisionX4DecoderCommunicator {
		final AtomicInteger getCalls = new AtomicInteger();
		RuntimeException error;

		@Override
		protected <Response> Response doGet(String uri, Class<Response> responseClass) {
			getCalls.incrementAndGet();
			throw error;
		}
	}

	private TestCommunicator communicator;

	@BeforeEach
	void setUp() throws Exception {
		communicator = new TestCommunicator();
		communicator.setHost("127.0.0.1");
		Field cookie = HaivisionX4DecoderCommunicator.class.getDeclaredField("authenticationCookie");
		cookie.setAccessible(true);
		cookie.set(communicator, new AuthenticationCookie());
		Field failedMonitor = HaivisionX4DecoderCommunicator.class.getDeclaredField("failedMonitor");
		failedMonitor.setAccessible(true);
		failedMonitor.set(communicator, new HashMap<String, String>());
	}

	private void runCycle() throws Throwable {
		Method method = HaivisionX4DecoderCommunicator.class.getDeclaredMethod("populateDecoderMonitoringMetrics", Map.class);
		method.setAccessible(true);
		try {
			method.invoke(communicator, new HashMap<String, String>());
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Test
	void testCycleStopsAtFirstTimeout() {
		communicator.error = new ResourceNotReachableException("Cannot reach resource at 127.0.0.1: Read timed out");
		Assertions.assertThrows(ResourceNotReachableException.class, this::runCycle);
		Assertions.assertEquals(1, communicator.getCalls.get(), "no further requests after a timeout");
	}

	@Test
	void testDataErrorDoesNotStopCycle() {
		communicator.error = new IllegalStateException("unexpected response");
		Assertions.assertThrows(ResourceNotReachableException.class, this::runCycle);
		// status, 4 decoders, streams: all groups are still requested
		Assertions.assertEquals(6, communicator.getCalls.get());
	}
}
