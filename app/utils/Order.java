package utils;

import java.util.Date;

public class Order {

	/**
	 * @userid 用户名
	 */
	public static String getOrderId(String userid) {
		StringBuilder sb = new StringBuilder();
		sb.append(new Date().getTime());
		int len = 32 - sb.length() - userid.length();
		for (int i = 0; i < len; i++)
			sb.append('0');
		sb.append(userid);
		return sb.toString();
	}
}