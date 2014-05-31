package controllers;

import models.Book;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import static play.data.Form.*;

public class Books extends Controller {
	public static Result BookAdminHome = redirect(routes.Books.list(0, "title",
			"asc", ""));

	public static Result index() {
		return TODO;
		// return ok(index.render("Your new application is ready.",
		// User.getUser(session().get("username"))));
	}

	public static Result view(Long bookid) {
		if (bookid <= 0 || bookid > Book.find.all().size()) {
			return redirect(routes.Application.index());
		}
		return ok(views.html.book.index.render(
				User.getUser(session().get("username")),
				Book.findBook_id(bookid)));
	}

	public static Result search() {
		String keyword = form().bindFromRequest().get("wd");
		return ok(views.html.book.search.render(Book.search(keyword)));
	}

	public static Result list(int page, String sortBy, String order,
			String filter) {
		return ok(views.html.book.list.render(
				User.getUser(session().get("username")),
				Book.page(page, 10, sortBy, order, filter), sortBy, order,
				filter));
	}

	public static Result create() {
		Form<Book> bookFrom = form(Book.class);
		return ok(views.html.book.create.render(
				User.getUser(session().get("username")), bookFrom));
	}

	public static Result save() {
		Form<Book> bookFrom = form(Book.class).bindFromRequest();
		if (bookFrom.hasErrors()) {
			return badRequest(views.html.book.create.render(
					User.getUser(session().get("username")), bookFrom));
		}
		bookFrom.get().save();
		flash("success", "Book " + bookFrom.get().title + " has been created");
		return BookAdminHome;
	}

	public static Result edit(Long id) {
		Form<Book> bookFrom = form(Book.class).fill(Book.find.byId(id));
		return ok(views.html.book.edit.render(
				User.getUser(session().get("username")), id, bookFrom));
	}

	public static Result update(Long id) {
		Form<Book> bookFrom = form(Book.class).bindFromRequest();
		if (bookFrom.hasErrors()) {
			return badRequest(views.html.book.edit.render(
					User.getUser(session().get("username")), id, bookFrom));
		}
		bookFrom.get().update(id);
		flash("success", "Book " + bookFrom.get().title + " has been updated");
		return BookAdminHome;
	}

	public static Result delete(Long id) {
		Book.find.ref(id).delete();
		flash("success", "该图书已删除");
		return BookAdminHome;
	}
}
