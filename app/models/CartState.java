package models;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class CartState extends Model {
	@Id
	public Long id;
	@Column(columnDefinition = "char(16)")
	public String name;

	public static Finder<Long, CartState> find = new Finder<Long, CartState>(
			Long.class, CartState.class);

	public static CartState getNewCart() {
		CartState cs = new CartState();
		cs.id = 1L;
		cs.name = "未付款";
		return cs;
	}
	public static CartState getPayment() {
		CartState cs = new CartState();
		cs.id = 2L;
		cs.name = "已付款";
		return cs;
	}

	public static Map<String, String> options() {
		LinkedHashMap<String, String> options = new LinkedHashMap<String, String>();
		for (CartState c : CartState.find.orderBy("name").findList()) {
			options.put(c.id.toString(), c.name);
		}
		return options;
	}
}
