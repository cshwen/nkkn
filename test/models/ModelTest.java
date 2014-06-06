package models;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import play.test.WithApplication;
import static play.test.Helpers.*;
import com.avaje.ebean.Ebean;

public class ModelTest extends WithApplication {
	@Before
	public void setUp() {
		start(fakeApplication(inMemoryDatabase()));
	}

	@Test
	public void testNewUser() { // 新建用户
		User.create("sbone", "sbsbsb", "sb@test.com");
		User bob = User.find.where().eq("username", "sbone").findUnique();
		assertNotNull(bob);
		assertEquals("sbone", bob.username);
	}

	@Test
	public void testAuthenticateUser() { // 登录校验
		new User("sbone", "sbsbsb", "sb@test.com").save();
		assertNotNull(User.authenticate("sbone", "sbsbsb"));
		assertNull(User.authenticate("sbtwo", "sbsbsb"));
		assertNull(User.authenticate("sbone", "one"));
	}

	@Test
	public void testIsExistUser() { // 是否存在
		boolean sign = User.isExistUser("csw");
		boolean fsign = User.isExistUser("sb");
		assertTrue(sign);
		assertFalse(fsign);
	}

	@Test
	public void testChangeInfo() { // 修改用户信息
		User test = User.getIdUser("1");
		User.changeInfo(test.id, "cshwen@cshwen.com", "13710310504");
		assertNotEquals("icshwen@gmail.com", test.email);
		assertEquals("cshwen@cshwen.com", test.email);
		assertNotEquals("12345678901", test.phone);
		assertEquals("13710310504", test.phone);
	}

	@Test
	public void testChangePwd() { // 修改密码
		User test = User.getIdUser("1");
		User.changePwd(test.id, "csw", "123456");
		assertNotEquals("csw", test.password);
		assertEquals("123456", test.password);
	}

	@Test
	public void testSearchBooks() { // 关键字搜索图书（内存数据库没图书）
		List<Book> searchisbn = Book.search("9787501765805", 0).books;
		List<Book> keyname = Book.search("javaweb", 0).books;
		assertTrue("nothing", searchisbn.size() == 1);
		assertTrue("nothing", keyname.size() > 0);
		assertFalse("u r right", searchisbn.size() == 1);
		assertFalse("u r right", keyname.size() > 0);
	}

	@Test
	public void testNewCart() { // 订单状态
		CartState cstate = CartState.getNewCart();
		assertSame(1L, cstate.id);
		assertNotSame(2L, cstate.id);
		assertEquals("未付款", cstate.name);
		assertNotEquals("已付款", cstate.name);
	}
}
