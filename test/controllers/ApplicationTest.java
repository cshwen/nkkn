package controllers;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;

import static org.junit.Assert.*;
import static play.test.Helpers.*;

import play.mvc.Result;
import play.test.WithApplication;

public class ApplicationTest extends WithApplication {

	@Before
	public void setUp() {
		start();
	}

	@Test
	public void authenticateSuccess() {
		Result result = callAction(
				controllers.routes.ref.Application.authenticate(),
				fakeRequest().withFormUrlEncodedBody(
						ImmutableMap.of("username", "csw", "password", "csw")));
		assertEquals(303, status(result));
		assertEquals("csw", session(result).get("username"));
		assertEquals("1", session(result).get("userid"));
	}

	@Test
	public void authenticateFailure() {
		Result result = callAction(
				controllers.routes.ref.Application.authenticate(),
				fakeRequest().withFormUrlEncodedBody(
						ImmutableMap.of("username", "csw", "password", "sbp")));
		assertEquals(400, status(result));
		assertNull(session(result).get("username"));
	}

	@Test
	public void authenticated() {
		Result result = callAction(
				controllers.routes.ref.Application.index(),
				fakeRequest().withSession("username", "csw").withSession(
						"userid", "1"));
		assertEquals(200, status(result));
	}

	@Test
	public void notAuthenticated() {
		Result result = callAction(controllers.routes.ref.Application.logout(),
				fakeRequest());
		assertEquals(303, status(result));
		assertEquals("/login", header("Location", result));
	}
}
