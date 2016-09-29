package database;

import java.sql.Statement;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class LocalDbConnection implements DataBaseConnection{

	public Statement getConnection() {
		Statement ps = null;
		try {
		
			BasicDataSource source = new BasicDataSource();
			
			source.setDriverClassName("com.mysql.jdbc.Driver");
			source.setUsername("root");
			source.setPassword("root");
			source.setUrl("jdbc:mysql://127.0.0.1:3306/accountfunding");
			java.sql.Connection connection = source.getConnection();
			
			ps = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ps;
	}

}
