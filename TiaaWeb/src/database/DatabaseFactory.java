package database;

import util.AccountFundingConstants;


public class DatabaseFactory {
	
	public DataBaseConnection getDatBaseObject(String connection){
		
		if(connection.equals(AccountFundingConstants.SERVER_NAME)){
			return new DbConnection();
		}else{
			return new LocalDbConnection();
		}
	}

}
