package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Page;

import play.data.format.Formats;
import play.data.validation.Constraints.Email;
import play.db.ebean.Model;

@Entity
public class User extends Model {
	@Id
	public Long id;
	@Column(columnDefinition = "char(32)")
	public String username;
	@Column(columnDefinition = "char(32)")
	public String password;
	@Email
	public String email;
	public String phone;
	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date regtime;

	// 一角色属一权限
	@ManyToOne
	public Role role;

	// 一人多评论
	@OneToMany(cascade = CascadeType.ALL)
	public List<Comment> comments = new ArrayList<Comment>();

	// 一购物车多商品
	@OneToMany(cascade = CascadeType.ALL)
	public List<CartItem> cart = new ArrayList<CartItem>();

	// 一人多历史订单
	@OneToMany(cascade = CascadeType.ALL)
	public List<Orderof> orders = new ArrayList<Orderof>();

	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = Role.getGeneralUser();
		this.regtime = new Date();
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

	public static void auBook(User user, long bookId, int num, long cartId) { // 添加或修改要购买的图书
		if (cartId == 0) {
			boolean isExistCart = true;
			for (CartItem it : user.cart) {
				if (it.book.id == bookId) {
					it.setNum(it.num + num);
					isExistCart = false;
					break;
				}
			}
			if (isExistCart == true) {
				CartItem ci = new CartItem(Book.findBook_id(bookId));
				ci.setNum(num);
				user.cart.add(ci);
			}
			user.save();
		} else {
			if (num > 0) {
				for (CartItem it : user.cart) {
					if (it.id == cartId) {
						it.setNum(num);
						it.update();
						break;
					}
				}
			} else
				CartItem.find.ref(cartId).delete();
		}
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

	public static void clearBook(long id) { // 清空购物车
		Ebean.createSqlUpdate("delete from cart_item where user_id = :id")
				.setParameter("id", id).execute();
	}

	public static Long getRole(Long id) {
		return User.find.ref(id).role.id;
	}

	public static Page<User> page(int page, int pageSize, String sortBy,
			String order, String filter) { // admin显示
		return find.where().ilike("username", "%" + filter + "%")
				.orderBy(sortBy + " " + order).fetch("orders").fetch("cart")
				.findPagingList(pageSize).setFetchAhead(false).getPage(page);
	}
}
