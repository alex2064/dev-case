package info.devcase.main;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class DbTest {
	@Test
	public void test() throws Exception {
		Class.forName("org.mariadb.jdbc.Driver"); // 마리아DB

		Connection con = DriverManager.getConnection("jdbc:mariadb://localhost:3306/devcase", "root", "alex2064");// 마리아DB
		System.out.println(con);
	}
}
