package service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dao.AccountFundingDao;
import dto.Accounts;
import dto.UserInfo;

public class AccountFundingService {

	Logger logger = Logger.getLogger("AccountFundingService");
	
	private AccountFundingDao accountFundingDao;

	public AccountFundingService() {
		accountFundingDao = new AccountFundingDao();
	}
	public UserInfo doLogin(String username, String password,String device,String session) {
		String result = accountFundingDao.doLogin(username, password, device, session);
		 if(result!=null){
			 return getUserInformation(result);
		 }else{
			 return new UserInfo();
		 }
	}
	
	public List<Accounts> getAccountsForUser(String pin) {
		return accountFundingDao.getAccountsForUser(pin);
	}
	
	public List<Accounts> getLinkedAccounts(String accountnumber){
		return accountFundingDao.getLinkedAccounts(accountnumber);
	}
	
	public int submitFunding(String orchestrationid,String pin,String from,String to,String amount,String date,String currentBalance){
		double balance = Double.parseDouble(currentBalance)+ Double.parseDouble(amount);
		String newBalance = String.valueOf(balance);
		return accountFundingDao.submitFunding(orchestrationid, pin, from, to, amount, date,newBalance);
	}
	
	public UserInfo checkSessionExists(String deviceid){
		logger.log(Level.INFO,deviceid);
		String output = accountFundingDao.checkSessionExists(deviceid);
		if(output!=null){
			return getUserInformation(output);
		}else{
			return new UserInfo();
		}
	}
	
	public UserInfo getUserInformation(String pin){
		return accountFundingDao.getUserInformation(pin);
	}
	
}
