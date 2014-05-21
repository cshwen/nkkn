package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;
@Entity
public class Category extends Model {
	@Id
	public Long id;
	public String category_num;
	public String category_name;
}
