package resource;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import service.AccountFundingService;
import dto.Accounts;
import dto.UserInfo;

@Path("/")
public class AccountFundingResource {
	
	Logger logger = Logger.getLogger("AccountFundingResource");
	
	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserInfo doLogin(@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("device") String device) {
		AccountFundingService service = new AccountFundingService();
		return service.doLogin(username, password, device, "Y");
	}

	@Path("/retrieve-accounts")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<Accounts> retrieveAccounts(@FormParam("pin") String pin) {
		AccountFundingService service = new AccountFundingService();
		return service.getAccountsForUser(pin);
	}

	@Path("/retrieve-linkedaccounts")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<Accounts> retrieveLinkedAccounts(@FormParam("accountnumber") String accountnumber) {
		AccountFundingService service = new AccountFundingService();
		return service.getLinkedAccounts(accountnumber);
	}

	@Path("/submit-funding")
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	public int submitFunding(
			@FormParam("orchestrationid") String orchestrationid,
			@FormParam("pin") String pin,
			@FormParam("fromaccount") String from,
			@FormParam("toaccount") String to,
			@FormParam("amount") String amount, @FormParam("date") String date,@FormParam("balance") String balance) {
		AccountFundingService service = new AccountFundingService();
		return service.submitFunding(orchestrationid, pin, from, to, amount,
				date,balance);
	}
	
	@POST
	@Path("/check-session")
	@Produces(MediaType.APPLICATION_JSON)
	public UserInfo checkSessionExists(@FormParam("deviceid") String deviceid){
		AccountFundingService service = new AccountFundingService();
		logger.log(Level.INFO,deviceid);
		return service.checkSessionExists(deviceid);
	}
	
	@Path("/retrieve-userinfo")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserInfo getUserInfo(@FormParam("pin") String pin){
		AccountFundingService service = new AccountFundingService();
		return service.getUserInformation(pin);
	}
	

}
