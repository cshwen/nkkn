package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import utils.Isbn;

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
	public int sales;

	@OneToOne
	public Category category;

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

	public static List<Book> search(String str) {
		if (Isbn.checkout13(str) || Isbn.checkout10(str)) {
			return find.where().eq("ISBN", str).findList();
		}
		return find
				.where()
				.or(com.avaje.ebean.Expr.like("name", "%" + str + "%"),
						com.avaje.ebean.Expr.like("author", "%" + str + "%"))
				.findPagingList(21).getPage(0).getList();
	}

}
