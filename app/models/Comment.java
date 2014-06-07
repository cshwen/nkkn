package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.avaje.ebean.Page;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Comment extends Model {
	@Id
	public Long id;
	@Required
	public String content;
	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date time;

	@ManyToOne
	public User user;
	@ManyToOne
	public Book book;

	public static void create(String content, User user, Book book) {
		Comment ct = new Comment();
		ct.content = content;
		ct.time = new Date();
		ct.user = user;
		ct.book = book;
		ct.save();
	}

	public static Finder<Long, Comment> find = new Finder<Long, Comment>(
			Long.class, Comment.class);

	public static Page<Comment> page(int page, int pageSize, String sortBy,
			String order, String filter) { // admin显示
		return find.where().ilike("content", "%" + filter + "%")
				.orderBy(sortBy + " " + order).findPagingList(pageSize)
				.getPage(page);
	}
}
