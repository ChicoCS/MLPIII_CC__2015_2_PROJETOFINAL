package unipe.br.dados;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectBank {
	
	public Connection getConnection(){
		 Properties pBank = new Properties();
	     try {
	    	 
	    	  pBank.load(new FileInputStream("src/unipe/br/src/database.properties"));
	    	 
	         return DriverManager.getConnection("jdbc:postgresql://"+pBank.getProperty("url")+"/"+pBank.getProperty("database"), pBank);

	     } catch (SQLException | IOException e) {
	         throw new RuntimeException(e);
	     } 
	 }
}
