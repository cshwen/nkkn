package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

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
}
