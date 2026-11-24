/*
 * Copyright (c) 2026 AVI-SPL, Inc. All Rights Reserved.
 */
package com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.login.FailedLoginException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import com.avispl.symphony.api.dal.error.CommandFailureException;
import com.avispl.symphony.api.dal.error.ResourceNotReachableException;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.common.DecoderConstant;
import com.avispl.symphony.dal.avdevices.encoderdecoder.haivision.x4decoder.dto.authetication.AuthenticationCookie;

/**
 * Wrong credentials are reported as a login error (FailedLoginException), not as an API / not-reachable error (SYAL-2273).
 */
@Tag("unitTest")
public class AuthenticationErrorTest {

	/** Communicator whose login POST behaves as configured, without a device. */
	static class TestCommunicator extends HaivisionX4DecoderCommunicator {
		Exception loginError;
		String sessionIdOnSuccess;

		@Override
		protected <Request> ResponseEntity<?> doPost(String uri, Request request) throws Exception {
			if (loginError != null) {
				throw loginError;
			}
			if (sessionIdOnSuccess != null) {
				getCookie(this).setSessionID(sessionIdOnSuccess);
			}
			return ResponseEntity.ok().build();
		}
	}

	private TestCommunicator communicator;

	private static AuthenticationCookie getCookie(HaivisionX4DecoderCommunicator communicator) {
		try {
			Field field = HaivisionX4DecoderCommunicator.class.getDeclaredField("authenticationCookie");
			field.setAccessible(true);
			return (AuthenticationCookie) field.get(communicator);
		} catch (ReflectiveOperationException e) {
			throw new IllegalStateException(e);
		}
	}

	@BeforeEach
	void setUp() throws Exception {
		communicator = new TestCommunicator();
		communicator.setHost("127.0.0.1");
		communicator.setLogin("admin");
		communicator.setPassword("wrong");
		Field field = HaivisionX4DecoderCommunicator.class.getDeclaredField("authenticationCookie");
		field.setAccessible(true);
		field.set(communicator, new AuthenticationCookie());
	}

	private void login() throws Throwable {
		Method method = HaivisionX4DecoderCommunicator.class.getDeclaredMethod("retrieveSessionFromDecoder");
		method.setAccessible(true);
		try {
			method.invoke(communicator);
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}

	@Test
	void testUnauthorizedIsLoginError() {
		communicator.loginError = new FailedLoginException("Invalid authentication credentials");
		Assertions.assertThrows(FailedLoginException.class, this::login);
	}

	@Test
	void testForbiddenIsLoginError() {
		communicator.loginError = new CommandFailureException("127.0.0.1", "/apis/authentication", "Forbidden", 403);
		Assertions.assertThrows(FailedLoginException.class, this::login);
	}

	@Test
	void testSuccessWithoutSessionCookieIsLoginError() {
		Assertions.assertThrows(FailedLoginException.class, this::login);
	}

	@Test
	void testUnreachableStaysNotReachable() {
		communicator.loginError = new ResourceNotReachableException("Cannot reach resource");
		Assertions.assertThrows(ResourceNotReachableException.class, this::login);
	}

	@Test
	void testSuccessfulLogin() throws Throwable {
		communicator.sessionIdOnSuccess = "abc";
		login();
		Assertions.assertEquals("abc", getCookie(communicator).getSessionID());
	}

	/**
	 * A non-2xx response to the login request is passed on to the RestTemplate error handling (which maps 401 to
	 * FailedLoginException) instead of being turned into ResourceNotReachableException by the interceptor.
	 */
	@Test
	void testInterceptorPassesUnauthorizedResponseOn() throws Exception {
		Field field = HaivisionX4DecoderCommunicator.class.getDeclaredField("haivisionInterceptor");
		field.setAccessible(true);
		ClientHttpRequestInterceptor interceptor = (ClientHttpRequestInterceptor) field.get(communicator);

		HttpRequest request = new HttpRequest() {
			public HttpMethod getMethod() { return HttpMethod.POST; }
			public URI getURI() { return URI.create("https://127.0.0.1:443/apis/authentication"); }
			public HttpHeaders getHeaders() { return new HttpHeaders(); }
			public Map<String, Object> getAttributes() { return new HashMap<>(); }
		};
		ClientHttpResponse response = new ClientHttpResponse() {
			public HttpStatusCode getStatusCode() { return HttpStatus.UNAUTHORIZED; }
			public String getStatusText() { return "Unauthorized"; }
			public void close() { }
			public InputStream getBody() { return new ByteArrayInputStream(new byte[0]); }
			public HttpHeaders getHeaders() { return new HttpHeaders(); }
		};
		ClientHttpRequestExecution execution = (req, body) -> response;

		getCookie(communicator).setSessionID("old");
		ClientHttpResponse result = interceptor.intercept(request, new byte[0], execution);

		Assertions.assertSame(response, result);
		Assertions.assertNull(getCookie(communicator).getSessionID());
		Assertions.assertNotNull(DecoderConstant.GETTING_SESSION_ID_ERR);
	}
}
