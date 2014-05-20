package controllers;

import models.Book;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import static play.data.Form.*;

public class Books extends Controller {
	public static Result index() {
		return TODO;
		// return ok(index.render("Your new application is ready.",
		// User.getUser(session().get("username"))));
	}

	public static Result view(Long bookid) {
		if (bookid <= 0 || bookid > User.find.all().size()) {
			return redirect(routes.Application.index());
		}
		return ok(views.html.book.index.render(User.getUser(session().get("username")),
				Book.findBook_id(bookid)));
	}
	
	public static Result search(){
		String keyword=form().bindFromRequest().get("wd");
		return ok(views.html.book.search.render(Book.search(keyword)));
	}
}
