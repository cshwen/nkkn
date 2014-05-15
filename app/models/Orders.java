package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.format.Formats;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Orders extends Model {
	@Id
	public Long id;
	public double sum;
	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date time;
	public String state;

	// 一订单多商品
	@OneToMany(cascade = CascadeType.ALL)
	public List<OrderItem> orderItem = new ArrayList<OrderItem>();
}
