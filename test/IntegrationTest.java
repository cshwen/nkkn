import org.junit.*;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import play.mvc.*;
import play.test.*;
import play.libs.WS;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {
	@Test
	public void testInServer() {
		running(testServer(3333), new Runnable() {
			public void run() {
				assertThat(
						WS.url("http://localhost:3333").get().get().getStatus())
						.isEqualTo(OK);
			}
		});
	}

	@Test
	public void testInServer2() {
		running(testServer(3333, fakeApplication(inMemoryDatabase())),
				HTMLUNIT, new Callback<TestBrowser>() {
					public void invoke(TestBrowser browser) {
						assertThat(
								WS.url("http://localhost:3333").get().get()
										.getStatus()).isEqualTo(OK);
					}
				});
	}

	@Test
	public void runInBrowser() {
		running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
			public void invoke(TestBrowser browser) {
				browser.goTo("http://localhost:3333");
				assertThat(browser.url()).isEqualTo("http://localhost:3333/");
				assertThat(browser.pageSource()).contains("欢迎光临");
			}
		});
	}

	@Test
	public void runHome() {
		running(testServer(3333), HTMLUNIT, new Callback<TestBrowser>() {
			public void invoke(TestBrowser browser) {
				browser.goTo("http://localhost:3333/");
				browser.$("[href='/help']").click();
				assertThat(browser.url()).isEqualTo(
						"http://localhost:3333/help");
				assertThat(browser.$(".text-warning").getTexts().get(0)).isEqualTo("nkkn网上书店帮助中心");
				browser.$("[href='/login']").click();
				assertThat(browser.url()).isEqualTo(
						"http://localhost:3333/login");
			}
		});
	}
}
