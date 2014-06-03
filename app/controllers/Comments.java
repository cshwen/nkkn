package controllers;

import models.Book;
import models.Comment;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

public class Comments extends Controller {
	public static Result send(long bookId, String content) {
		Comment.create(content, User.getIdUser(session().get("userid")),
				Book.find.ref(bookId));
		return ok();
	}
}
