package controllers;

import play.mvc.*;
import play.mvc.Http.*;

import models.*;

public class Secured extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("username");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return redirect(routes.Application.login());
	}

	public static boolean isAdminOf(Long id) {
		if (User.getRole(id) == 7)
			return true;
		else
			return false;
	}

	public static boolean isEditorOf(Long id) {
		if (User.getRole(id) == 3)
			return true;
		else
			return false;
	}

}