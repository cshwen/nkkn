import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.GET;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.routeAndCall;

import models.Book;

import org.junit.Before;
import org.junit.Test;

import play.mvc.Result;
import play.test.WithApplication;

public class RouterTest extends WithApplication {
	@Before
	public void setUp() {
		start();
	}

	@Test
	public void goodRoute() {
		Result result = routeAndCall(fakeRequest(GET, "/"));
		assertThat(result).isNotNull();
		result = routeAndCall(fakeRequest(GET, "/page/0"));
		assertThat(result).isNotNull();
		result = routeAndCall(fakeRequest(GET, "/tab/C/0"));
		assertThat(result).isNotNull();
		result = routeAndCall(fakeRequest(GET, "/login"));
		assertThat(result).isNotNull();
		result = routeAndCall(fakeRequest(GET, "/books/1"));
		assertThat(result).isNotNull();
	}

	@Test
	public void badRoute() {
		Result result = routeAndCall(fakeRequest(GET, "/abc"));
		assertThat(result).isNull();
	}
}
