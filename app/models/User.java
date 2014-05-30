package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.avaje.ebean.Ebean;

import play.data.format.Formats;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class User extends Model {
	@Id
	public Long id;
	@Required
	@Column(columnDefinition = "char(32)")
	public String username;
	@Required
	@Column(columnDefinition = "char(32)")
	public String password;
	@Email
	public String email;
	public int role;
	public String phone;
	@Formats.DateTime(pattern = "yyyy-MM-dd")
	public Date regtime;

	// 一人多评论
	@OneToMany(cascade = CascadeType.ALL)
	public List<Comment> comments = new ArrayList<Comment>();

	// 一购物车多商品
	@OneToMany(cascade = CascadeType.ALL)
	public List<CartItem> cart = new ArrayList<CartItem>();

	// 一人多历史订单
	@OneToMany(cascade = CascadeType.ALL)
	public List<Orders> orders = new ArrayList<Orders>();

	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = 3;
	}

	public static Finder<Long, User> find = new Finder<Long, User>(Long.class,
			User.class);

	public static User authenticate(String username, String password) { // 验证用户
		return find.where().eq("username", username).eq("password", password)
				.findUnique();
	}

	public static User create(String username, String password, String email) { // 创建用户
		User user = new User(username, password, email);
		user.save();
		return user;
	}

	public static User getUser(String username) {
		return find.where().eq("username", username).findUnique();
	}
	
	public static User getIdUser(String userid) {
		return User.find.ref(Long.valueOf(userid));
	}

	public static boolean isExistUser(String username) { // 用户名是否存在
		User user = find.where().eq("username", username).findUnique();
		if (user == null)
			return false;
		else
			return true;
	}

	public static void changeInfo(long id, String email, String phone) { // 修改电话/邮箱
		User user = User.find.ref(id);
		user.phone = phone;
		user.email = email;
		user.save();
	}
	
	public static boolean changePwd(long id, String oldpwd, String newpwd) { // 修改密码
		User user = User.find.ref(id);
		if (!user.password.equals(oldpwd))
			return false;
		else {
			user.password = newpwd;
			user.update();
			return true;
		}
	}

	public static List<CartItem> getCart(User user) { // 获取购物车列表信息
		return user.cart;
	}

	public static void addBook(User user, long bookid) { // 添加到购物车
		boolean nullsign = true;
		for (CartItem it : user.cart) {
			if (it.book.id == bookid) {
				it.setNum(it.num + 1);
				nullsign = false;
				break;
			}
		}
		if (nullsign == true) {
			CartItem ci = new CartItem(Book.findBook_id(bookid));
			ci.setNum(1);
			user.cart.add(ci);
		}
		user.save();
	}

	public static void alterBook(User user, long cartId, int num) { // 修改购买数量
		for (CartItem it : user.cart) {
			if (it.id == cartId) {
				it.setNum(num);
				it.update();
				break;
			}
		}
	}

	public static void deleteBook(User user, long cartId) { // 删除购物车某书
		CartItem.find.ref(cartId).delete();
		// Ebean.createSqlUpdate("delete from cart_item where id = :id ")
		// .setParameter("id", cartId).execute();
	}

	public static void clearBook(User user) { // 清空购物车
		user.cart.clear();
		user.save();
	}

	public static int getRole(Long id) {
		return User.find.ref(id).role;
	}
}
