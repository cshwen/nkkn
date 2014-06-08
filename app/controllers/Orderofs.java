package controllers;

import static play.data.Form.form;

import com.avaje.ebean.Ebean;

import models.Book;
import models.Orderof;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

@Security.Authenticated(Secured.class)
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

	public static Result edit(Long id) {
		Form<Orderof> orderFrom = form(Orderof.class).fill(
				Orderof.find.byId(id));
		return ok(views.html.order.edit.render(
				User.getUser(session().get("username")), id, orderFrom,Orderof.find.byId(id)));
	}

	public static Result update(Long id) {
		Form<Orderof> orderFrom = form(Orderof.class).bindFromRequest();
		if (orderFrom.hasErrors()) {
			return badRequest(views.html.order.edit.render(
					User.getIdUser(session().get("userid")), id, orderFrom,Orderof.find.byId(id)));
		}
		Ebean.createSqlUpdate(
				"update orderof set cart_state_id = :state where id = :id")
				.setParameter("id", id)
				.setParameter("state", orderFrom.get().cartState.id).execute();
		flash("success", "Orderof " + orderFrom.get().record
				+ " has been updated");
		return redirect(routes.Orderofs.list(0, "id", "asc", ""));
	}
}
