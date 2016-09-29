package context;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import util.AccountFundingConstants;
import database.DataBaseSingleton;

public class ServerContextListener implements ServletContextListener{
	
	Logger logger = Logger.getLogger("ServerContextListener");
	
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.log(Level.INFO,"ContextDestroyed");
	}

	public void contextInitialized(ServletContextEvent event) {
		
		try {
			String deployedServerName = InetAddress.getLocalHost().getHostName();
			if(deployedServerName!=null){
				if(deployedServerName.contains("rhcloud")){
					DataBaseSingleton.createInstance(AccountFundingConstants.SERVER_NAME);
				}else{
					DataBaseSingleton.createInstance("LocalHost");
				}
			}
		} catch (UnknownHostException e) {
			logger.log(Level.SEVERE,e.toString());
		}
		
	}
	
	

}
