package controllers;

import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Users extends Controller {
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
			if (User.changePwd(Long.valueOf(session().get("userid")), oldpwd, newpwd)) {
				flash("pwdinfo", "密码修改完成！");
			} else {
				flash("pwdinfo", "当前密码输入错误！");
			}
		}
		return redirect(routes.Users.pwd());
	}
}
