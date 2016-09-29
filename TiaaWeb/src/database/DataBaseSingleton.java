package database;

public class DataBaseSingleton {

	private static DataBaseConnection instance;
	private static String connection;
	
	public static DataBaseConnection getInstance(){
		
		if(instance ==null){
			instance = createInstance(connection);
		}
		return instance;
	}

	public static DataBaseConnection createInstance(String conn){
		connection = conn;
		return new DatabaseFactory().getDatBaseObject(connection);
	}
	
}
