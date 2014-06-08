import java.util.ArrayList;
import java.util.List;

import models.Book;
import models.Category;
import models.User;

import org.junit.Test;
import controllers.Application.Login;
import controllers.Application.Register;
import static play.test.Helpers.contentType;
import static play.test.Helpers.contentAsString;
import static org.fest.assertions.Assertions.assertThat;

import play.mvc.Content;
import play.data.Form;
import views.html.help;
import views.html.index;

public class TemplateTest {

	@Test
	public void loginTemplate() {
		List<Category> cl = new ArrayList<Category>();
		Category cc = new Category();
		cc.num = "0";
		cc.name = "未知";
		cl.add(cc);
		cc.num = "A";
		cc.name = "马列毛邓";
		cl.add(cc);
		User user = new User("cshwen", "test", "test@qq.com");
		List<Book> bl = new ArrayList<Book>();
		Book bk = new Book();
		bk.id = (long) 111;
		bk.title = "图书一本测试的";
		bk.price="CNY29.80";
		bl.add(bk);
		Content html = views.html.index.render("欢迎光临", user, cl, bl);
		assertThat(contentType(html)).isEqualTo("text/html");
		assertThat(contentAsString(html)).contains("图书一本测试的"); 
		assertThat(contentAsString(html)).contains("马列毛邓"); 
	}

	@Test
	public void helpTemplate() {
		User user = new User("cshwen", "test", "test@qq.com");
		Content html = views.html.help.render(user);
		assertThat(contentType(html)).isEqualTo("text/html");
		assertThat(contentAsString(html)).contains("cshwen");
		assertThat(contentAsString(html)).contains("退出");
	}
}
