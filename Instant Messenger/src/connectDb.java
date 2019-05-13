import java.sql.Connection;
import java.sql.DriverManager;

public class connectDb {
	
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://localhost/chatApp";
			String username = "root";
			String password = "";
			
			//Class.forName(driver);
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
			return conn;
		} catch(Exception e) {
			System.out.println(e);
		}
		
		return null;
	}

}
