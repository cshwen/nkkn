package controllers;

import java.awt.print.Book;

import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Books extends Controller {
	public static Result index() {
		return TODO;
//		return ok(index.render("Your new application is ready.",
//				User.getUser(session().get("username"))));
	}
}
