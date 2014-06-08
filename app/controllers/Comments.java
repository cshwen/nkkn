package controllers;

import models.Book;
import models.Comment;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class Comments extends Controller {
	public static Result send(long bookId, String content) {
		Comment.create(content, User.getIdUser(session().get("userid")),
				Book.find.ref(bookId));
		return ok();
	}

	public static Result list(int page, String sortBy, String order,
			String filter) {
		return ok(views.html.comment.list.render(
				User.getUser(session().get("username")),
				Comment.page(page, 10, sortBy, order, filter), sortBy, order,
				filter));
	}

	public static Result del(Long id) {
		Comment.find.ref(id).delete();
		return redirect(routes.Users.comments());
	}

	public static Result delete(Long id) {
		Comment.find.ref(id).delete();
		return redirect(routes.Comments.list(0, "id", "asc", ""));
	}
}
