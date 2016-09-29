package database;

import java.sql.Statement;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class DbConnection implements DataBaseConnection{

	public Statement getConnection() {
		Statement ps = null;
		try {
			
			BasicDataSource source = new BasicDataSource();
			source.setDriverClassName("com.mysql.jdbc.Driver");
			source.setUsername("adminFaYvjh7");
			source.setPassword("tJDjTVHnzWU4");
			source.setUrl("jdbc:mysql://127.3.191.2:3306/tiaaweb");
			
			java.sql.Connection connection = source.getConnection();
		
			ps = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;
	}

}
