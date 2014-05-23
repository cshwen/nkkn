package controllers;

import models.Category;
import models.User;
import play.mvc.Content;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
public class Carts extends Controller {

	public static Result index() {
		return TODO;
		// return ok(index.render("Your new application is ready.",
		// User.getUser(session().get("username"))));
	}

	public static Result view() {
		return ok(views.html.cart.view.render(User.getCart(User
				.getUser(session().get("username")))));
	}

	public static Result addCart(Long bookid) {
		User.addBook(User.getUser(session().get("username")), bookid);
		return ok("已添加至购物车");
	}

	public static Result delCart(Long bookid) {
		User.deleteBook(User.getUser(session().get("username")), bookid);
		return ok();
	}

	public static Result clearCart() {
		User.clearBook(User.getUser(session().get("username")));
		return ok();
	}

	public static Result orderView() {
		User user = User.getUser(session().get("username"));
		return ok(views.html.cart.order.render(user, Category.findAll(),
				User.getCart(user)));
	}
}
