package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.format.Formats;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class User extends Model {
	@Id
	public Long id;
	@Required
	public String username;
	@Required
	public String password;
	@Email
	public String email;
	public int role;
	public String phone;
	@Formats.DateTime(pattern = "yyyy-MM-dd")
	public Date regtime;

	@OneToMany(cascade = CascadeType.ALL)
	public List<Comment> comments = new ArrayList<Comment>();

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
	
	public static User getUser(String username){
		return find.where().eq("username", username).findUnique();
	}

	public static boolean isExistUser(String username) { // 用户名是否存在
		User user = find.where().eq("username", username).findUnique();
		if (user == null)
			return false;
		else
			return true;
	}

	public static boolean changePwd(long id, String pwd) { // 修改密码
		User user = User.find.ref(id);
		user.password = pwd;
		user.update();
		return true;
	}
}
