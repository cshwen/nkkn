package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Book extends Model {
	@Id
	public Long id;
	@Required
	public String name;
	public String author;
	public String publisher;
	public String ISBN;
	public double price;
	@Formats.DateTime(pattern = "yyyy-MM-dd")
	public Date pubtime;
	public int pages;
	public String summary;
	public double score;
	public int stock;
	public String imgPath;

	// 一书多评论
	@OneToMany(cascade = CascadeType.ALL)
	public List<Comment> comments = new ArrayList<Comment>();

	public static Finder<Long, Book> find = new Finder<Long, Book>(Long.class,
			Book.class);

	public static List<Book> findAll() {
		return find.all();
	}

	public static List<Book> findPageBooks(int pagesize, int page) {
		return find.findPagingList(pagesize).getPage(page).getList();
	}

	public static Book findBook_id(long bookid) {
		return find.ref(bookid);
	}
}
