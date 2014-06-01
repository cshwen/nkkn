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

import com.avaje.ebean.Page;

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

	// 多订单一状态
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

	public static void setPayment(long id) {
		Orderof odf = find.ref(id);
		odf.cartState = CartState.getPayment();
		odf.save();
	}

	public static Page<Orderof> page(int page, int pageSize, String sortBy,
			String order, String filter) { // admin显示
		return find.where().ilike("record", "%" + filter + "%")
				.orderBy(sortBy + " " + order).fetch("orderItem")
				.findPagingList(pageSize).setFetchAhead(false).getPage(page);
	}
}
