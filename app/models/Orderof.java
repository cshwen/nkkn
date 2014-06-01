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

import play.data.format.Formats;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class Orderof extends Model {
	@Id
	public Long id;
	@Column(columnDefinition = "char(32)")
	public String record;
	public double sum;
	public int num;
	@Formats.DateTime(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date time;

	// 一订单一状态
	@ManyToOne
	public CartState cartState;
	// 一订单多商品
	@OneToMany(cascade = CascadeType.ALL)
	public List<OrderItem> orderItem = new ArrayList<OrderItem>();

	public static Finder<Long, Orderof> find = new Finder<Long, Orderof>(
			Long.class, Orderof.class);

	public static Orderof getOrder(String rd) {
		return find.where().eq("record", rd).findUnique();
	}

}
