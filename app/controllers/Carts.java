package controllers;

import java.util.Date;
import com.avaje.ebean.Ebean;

import models.Book;
import models.CartItem;
import models.CartState;
import models.Category;
import models.OrderItem;
import models.Orderof;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import utils.Order;

@Security.Authenticated(Secured.class)
public class Carts extends Controller {

	public static Result index() { // 查看购物车
		User user = User.getUser(session().get("username"));
		return ok(views.html.cart.index.render(user, Category.findAll(),
				User.getCart(user)));
	}

	public static Result view() {
		return ok(views.html.cart.view.render(User.getCart(User
				.getUser(session().get("username")))));
	}

	public static Result addCart(Long bookid) { // 添加到购物车（弃）
		User.addBook(User.getUser(session().get("username")), bookid);
		return ok("已添加至购物车");
	}

	public static Result alterCart(Long cartId, int num) { // 修改购物车（弃）
		User.alterBook(User.getIdUser(session().get("userid")), cartId, num);
		return ok("已修改购物车");
	}

	public static Result auCart(long bookId, int num, long cartId) { // C/U购物车
		User.auBook(User.getUser(session().get("username")), bookId, num,
				cartId);
		return ok("购物车中图书已修改");
	}

	public static Result deleteCart(Long cartId) {
		User.deleteBook(User.getUser(session().get("username")), cartId);
		return ok("已从购物车删除");
	}

	public static Result clearCart() {
		User.clearBook(Long.valueOf(session().get("userid")));
		return ok("已清空购物车");
	}

	public static Result refresh() { // 刷新下单
		return ok(views.html.cart.order.render(User.getCart(User
				.getUser(session().get("username")))));
	}

	public static Result checkout() { // 确认订单
		return ok(views.html.cart.checkout.render(
				User.getUser(session().get("username")), Category.findAll(),
				User.getCart(User.getUser(session().get("username")))));
	}

	public static Result push() { // 提交订单
		String orderRecordId = Order.getOrderId(session().get("userid"));
		Ebean.beginTransaction();
		try {
			User user = Ebean.find(User.class, session().get("userid"));
			double sum = 0;
			int num = 0;
			Orderof orders = new Orderof();
			for (CartItem cartItem : user.cart) {
				Book.sell(cartItem.book.id, cartItem.num);
				OrderItem orderItem = new OrderItem(cartItem);
				sum += orderItem.price;
				num += orderItem.num;
				orders.orderItem.add(orderItem);
			}
			orders.record = orderRecordId;
			orders.sum = sum;
			orders.num = num;
			orders.time = new Date();
			orders.cartState = CartState.getNewCart();
			user.orders.add(orders);
			Ebean.save(user);
			clearCart();
			Ebean.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			return ok(views.html.cart.outofstock.render(
					User.getIdUser(session().get("userid")), e.getMessage()));
		} finally {
			Ebean.endTransaction();
		}
		Orderof odf = Orderof.getOrder(orderRecordId);
		return ok(views.html.cart.skip.render(
				User.getIdUser(session().get("userid")), odf));
	}
}
