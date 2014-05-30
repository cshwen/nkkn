package controllers;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;

public class Users extends Controller {
	public static Result index() { 
		return ok(views.html.user.info.render(User.getUser(session().get("username"))));
	}
	public static Result chgPwd() { 
		return ok(views.html.user.pwd.render(User.getUser(session().get("username"))));
	}
}
