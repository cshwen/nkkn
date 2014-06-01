package controllers;

import java.util.Date;

import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Users extends Controller {
	public static Result UserAdminHome = redirect(routes.Users.list(0,
			"username", "asc", ""));

	public static Result info() {
		return ok(views.html.user.info.render(User.getUser(session().get(
				"username"))));
	}

	public static Result pwd() {
		return ok(views.html.user.pwd.render(User.getUser(session().get(
				"username"))));
	}

	public static Result updateInfo() {
		DynamicForm requestData = Form.form().bindFromRequest();
		String phone = requestData.get("info_phone");
		String email = requestData.get("info_email");
		User.changeInfo(Long.valueOf(session().get("userid")), email, phone);
		return redirect(routes.Users.info());
	}

	public static Result changePwd() {
		DynamicForm requestData = Form.form().bindFromRequest();
		String oldpwd = requestData.get("oldpwd");
		String newpwd = requestData.get("newpwd");
		String new2pwd = requestData.get("new2pwd");
		if (!newpwd.equals(new2pwd)) {
			flash("pwdinfo", "两次密码不一致！");
		} else {
			if (User.changePwd(Long.valueOf(session().get("userid")), oldpwd,
					newpwd)) {
				flash("pwdinfo", "密码修改完成！");
			} else {
				flash("pwdinfo", "当前密码输入错误！");
			}
		}
		return redirect(routes.Users.pwd());
	}

	public static Result list(int page, String sortBy, String order,
			String filter) {
		return ok(views.html.user.list.render(
				User.getIdUser(session().get("userid")),
				User.page(page, 10, sortBy, order, filter), sortBy, order,
				filter));
	}

	public static Result create() {
		Form<User> userForm = Form.form(User.class);
		return ok(views.html.user.create.render(
				User.getUser(session().get("username")), userForm));
	}

	public static Result save() {
		Form<User> userForm = Form.form(User.class).bindFromRequest();
		if (userForm.hasErrors()) {
			return badRequest(views.html.user.create.render(
					User.getUser(session().get("username")), userForm));
		}
		User newuser = userForm.get();
		newuser.regtime = new Date();
		newuser.save();
		flash("success", "User " + userForm.get().username
				+ " has been created");
		return UserAdminHome;
	}

	public static Result edit(Long id) {
		Form<User> userForm = Form.form(User.class).fill(User.find.byId(id));
		return ok(views.html.user.edit.render(
				User.getUser(session().get("username")), id, userForm));
	}

	public static Result update(Long id) {
		Form<User> userForm = Form.form(User.class).bindFromRequest();
		if (userForm.hasErrors()) {
			return badRequest(views.html.user.edit.render(
					User.getUser(session().get("username")), id, userForm));
		}
		userForm.get().update(id);
		flash("success", "User " + userForm.get().username
				+ " has been updated");
		return UserAdminHome;
	}

	public static Result delete(Long id) {
		User.find.ref(id).delete();
		flash("success", "该用户已删除");
		return UserAdminHome;
	}
}
