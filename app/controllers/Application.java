package controllers;

import models.User;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
//	@Security.Authenticated(Secured.class)
	public static Result index() {
//		System.out.println(request().username());
//		System.out.println(User.getUser(request().username()));
		return ok(index.render("Your new application is ready.",
				User.getUser(session().get("username"))));
	}

	public static Result login() {
		return ok(views.html.login.render(Form.form(Login.class),
				Form.form(Register.class)));
	}

	public static Result authenticate() {
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		Form<Register> registerForm = Form.form(Register.class)
				.bindFromRequest();
		if (loginForm.hasErrors()) {
			return badRequest(views.html.login.render(loginForm, registerForm));
		} else {
			session().clear();
			session("username", loginForm.get().username);
			return redirect(routes.Application.index());
		}
	}

	public static Result logout() {
		session().clear();
		flash("success", "You've been logged out");
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
		Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
		Form<Register> registerForm = Form.form(Register.class)
				.bindFromRequest();
		if (registerForm.hasErrors()) {
			return badRequest(views.html.login.render(loginForm, registerForm));
		} else {
			session().clear();
			session("username", registerForm.get().username);
			return redirect(routes.Application.index());
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
	
	public static Result javascriptRoutes() {
	    response().setContentType("text/javascript");
	    return ok(
	        Routes.javascriptRouter("jsRoutes",
	        		controllers.routes.javascript.Application.registerUser()
	        )
	    );
	}
}
