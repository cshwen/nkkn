package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Category extends Model {
	@Id
	@Column(columnDefinition="char(2)")
	public String num;
	@Column(columnDefinition="char(32)")
	public String category_name;
	// @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	// public List<Book> books = new ArrayList<Book>();
}
