package models;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Role extends Model {
	@Id
	public Long id;
	@Column(columnDefinition = "char(16)")
	public String name;

	public static Role getGeneralUser() {
		Role role = new Role();
		role.id = 1L;
		role.name = "普通用户";
		return role;
	}

	public static Finder<Long, Role> find = new Finder<Long, Role>(Long.class,
			Role.class);

	public static Map<Long, String> options() {
		LinkedHashMap<Long, String> options = new LinkedHashMap<Long, String>();
		for (Role c : Role.find.orderBy("name").findList()) {
			options.put(c.id, c.name);
		}
		return options;
	}
}
