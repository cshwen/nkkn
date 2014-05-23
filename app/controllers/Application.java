package controllers;

import models.Book;
import models.Category;
import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
	public static Result index() { // 首页待定
		// return redirect(routes.Application.page(0));
		return ok(index.render("Your new application is ready.",
				User.getUser(session().get("username")), Category.findAll(),
				Book.findPageBooks(21, 0)));
	}

	public static Result page(int page) { // 分页待定
		return ok(index.render("page:	" + page,
				User.getUser(session().get("username")), Category.findAll(),
				Book.findPageBooks(21, page)));
	}

	public static Result tab(String sign, int page_now) { // 导航
		return ok(show.render(Category.getTitle(sign) + "\t--" + page_now,
				User.getUser(session().get("username")), Category.findAll(),
				sign, page_now, Category.getPages(page_now,sign), Category.showBook(sign, 21, page_now)));
	}

	public static Result login() {
		return ok(login.render(Form.form(Login.class),
				Form.form(Register.class)));
	}

	public static Result authenticate() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		Form<Register> registerForm = Form.form(Register.class)
				.bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(login.render(loginForm, registerForm));
		} else {
			session().clear();
			session("username", loginForm.get().username);
			return redirect(routes.Application.index());
		}
	}

	public static Result logout() {
		session().clear();
		flash("success", "您已退出登录。");
		return redirect(routes.Application.login());
	}

	public static class Login {
		public String username;
		public String password;

		public String validate() {
			if (User.authenticate(username, password) == null) {
				return "用户名或密码无效";
			}
			return null;
		}
	}

	public static Result registerUser() {
		// Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		// Form<Register> registerForm = Form.form(Register.class)
		// .bindFromRequest();
		// if (registerForm.hasErrors()) {
		// return badRequest(views.html.login.render(loginForm, registerForm));
		// } else {
		// session().clear();
		// session("username", registerForm.get().username);
		// return redirect(routes.Application.index());
		// }
		Form<Register> registerForm = Form.form(Register.class)
				.bindFromRequest();
		if (registerForm.hasErrors()) {
			return badRequest();
		} else {
			session().clear();
			session("username", registerForm.get().username);
			return ok();
		}
	}

	public static class Register {
		public String username;
		public String password;
		public String password_repeat;
		public String email;

		public String validate() {
			if (User.isExistUser(username) == true)
				return "用户名已存在啦！";
			User.create(username, password, email);
			return null;
		}
	}

	public static Result isExistUser(String username) {
		boolean result = User.isExistUser(username);
		if (result == true)
			return badRequest("该用户已存在！");
		else
			return ok("该用户名可注册。");
	}

	public static Result javascriptRoutes() {
		response().setContentType("text/javascript");
		return ok(Routes.javascriptRouter("jsRoutes",
				controllers.routes.javascript.Application.registerUser(),
				controllers.routes.javascript.Application.isExistUser(),
				controllers.routes.javascript.Carts.addCart(),
				controllers.routes.javascript.Carts.view()));
	}
}
