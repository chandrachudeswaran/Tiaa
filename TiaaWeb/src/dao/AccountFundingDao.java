package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.DataBaseConnection;
import database.DataBaseSingleton;
import dto.Accounts;
import dto.UserInfo;

public class AccountFundingDao {

	Logger logger = Logger.getLogger("AccountFundingDao");

	public String doLogin(String username, String password, String device,
			String session) {
		String result = null;
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		try {
			ps = dbConnection.getConnection();
			int count = 0;
			String sqlStatement = "select * from login where username='"
					+ username + "' and password='" + password + "'";
			rs = ps.executeQuery(sqlStatement);
			while (rs.next()) {
				count++;
				result = rs.getString("pin");
			}
			if (count == 1) {
				doSessionUpdate(device, username, session);
			}
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString());
		}
		return result;
	}
	

	public List<Accounts> getAccountsForUser(String pin) {

		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		List<Accounts> listOfAccounts = new ArrayList<Accounts>();
		try {
			ps = dbConnection.getConnection();
			String sql = "select * from accounts where pin='" + pin + "'";
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				Accounts account = new Accounts();
				account.setPin(rs.getString("pin"));
				account.setAccountnumber(rs.getString("accountnumber"));
				account.setTypeOfAccount(rs.getString("type"));
				account.setBalance(rs.getDouble("balance"));
				listOfAccounts.add(account);
			}
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString());
		}
		return listOfAccounts;
	}

	public List<Accounts> getLinkedAccounts(String accoutnumber) {
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		List<Accounts> listOfAccounts = new ArrayList<Accounts>();
		try {
			ps = dbConnection.getConnection();
			String sql = "select * from linkedaccounts where linkedaccount='" + accoutnumber + "'";
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				Accounts account = new Accounts();
				account.setPin(rs.getString("pin"));
				account.setBankname(rs.getString("bankname"));
				account.setAccountnumber(rs.getString("accountnumber"));
				account.setTypeOfAccount(rs.getString("type"));
				listOfAccounts.add(account);
			}
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString());
		}
		return listOfAccounts;
	}

	public int submitFunding(String orchestrationid, String pin, String from,
			String to, String amount, String date,String newbalance) {
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		int status = 0;
		try {
			ps = dbConnection.getConnection();
			String sql = "insert into fundactivity (orchestrationid,pin,fromaccount,toaccount,amount,date) values ("
					+ "'"
					+ orchestrationid
					+ "','"
					+ pin
					+ "','"
					+ from
					+ "','"
					+ to
					+ "','"
					+ Double.parseDouble(amount)
					+ "','"
					+ date + "')";
			status = ps.executeUpdate(sql);
			ps.close();
			//updateBalances(pin, to, newbalance);
		} catch (NumberFormatException e) {
			logger.log(Level.SEVERE, e.toString());
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString());
		}

		return status;
	}

	public String checkSessionExists(String deviceid) {
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		String session = "Y";
		String pin = null;
		try {
			ps = dbConnection.getConnection();
			String sql = "select * from login where deviceid='" + deviceid
					+ "' and session='" + session + "'";
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				pin = rs.getString("pin");
			}
			ps.close();
			return pin;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString());
		}

		return pin;
	}

	public UserInfo getUserInformation(String pin) {
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		Statement ps = null;
		ResultSet rs = null;
		UserInfo userInfo = new UserInfo();
		try {
			ps = dbConnection.getConnection();
			String sql = "select * from userinfo where pin='" + pin + "'";
			rs = ps.executeQuery(sql);
			while (rs.next()) {
				userInfo.setPin(rs.getString("pin"));
				userInfo.setFirstname(rs.getString("firstname"));
				userInfo.setLastname(rs.getString("lastname"));
				userInfo.setEmail(rs.getString("email"));
				userInfo.setPhonenumber(rs.getString("phonenumber"));
			}
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE, e.toString());
		}
		return userInfo;
	}

	public int doSessionUpdate(String device, String username, String session) {
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		String sqlStatement = "";
		Statement ps = null;
		int statusUpdate = 0;
		
		try {
			ps = dbConnection.getConnection();
			sqlStatement= "update login set session='"+session+"' where deviceid = '"+device+"' and username='"+username+"'";
			statusUpdate=ps.executeUpdate(sqlStatement);
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		logger.log(Level.INFO,"Update Query Result Value "+statusUpdate);
		return statusUpdate;

	}

	
	public void updateBalances(String pin,String to,String newBalance){
		DataBaseConnection dbConnection = DataBaseSingleton.getInstance();
		String sqlStatement = "";
		Statement ps = null;
		int statusUpdate = 0;
		try {
			ps = dbConnection.getConnection();
			sqlStatement= "update accounts set balance='"+newBalance+"' where pin = '"+pin+"' and accountnumber='"+to+"'";
			statusUpdate=ps.executeUpdate(sqlStatement);
			ps.close();
		} catch (SQLException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		logger.log(Level.INFO,"Update Query Result Value "+statusUpdate);
	}
}
