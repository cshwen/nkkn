package controllers;

import models.Orderof;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

public class Orderofs extends Controller {
	public static Result payment(Long id) {
		Orderof.setPayment(id);
		return redirect(routes.Users.info());
	}

	public static Result list(int page, String sortBy, String order,
			String filter) {
		return ok(views.html.order.list.render(
				User.getIdUser(session().get("userid")),
				Orderof.page(page, 10, sortBy, order, filter), sortBy, order,
				filter));
	}
}
