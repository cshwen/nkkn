package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class Category extends Model {
	@Id
	@Column(columnDefinition = "char(2)")
	public String num;
	@Column(columnDefinition = "char(32)")
	public String name;
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public List<Book> books = new ArrayList<Book>();

	public static Finder<String, Category> find = new Finder<String, Category>(
			String.class, Category.class);

	public static List<Category> findAll() {
		return find.all();
	}

	public static List<Book> showBook(String sign, int pagesize, int page) {
		Category c = find.where().eq("num", sign).findUnique();
		return c.books.subList(pagesize * page, pagesize * (page + 1));
	}

	public static String getTitle(String sign) {
		return find.where().eq("num", sign).findUnique().name;
	}

	public static int getPages(String sign) {
		System.out.println("pagenum:"
				+ find.where().eq("num", sign).findUnique().books.size());
		int num = find.where().eq("num", sign).findUnique().books.size();
		if (num % 21 == 0)
			num /= 21;
		else
			num = num / 21 + 1;
		return num;
	}
}
