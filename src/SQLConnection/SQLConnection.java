package SQLConnection;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;

public class SQLConnection{
	
	private final String bd="alberca";
	private final String user="root";
	private final String pass="";
	private final String dir="jdbc:mysql://localhost:3306/"+bd;
	
	public Connection Conn() {
		Connection con=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=(Connection)DriverManager.getConnection(dir,user,pass);
			System.out.println("Conectado con exito");
		}catch(Exception e){
			System.out.println("No se pudo conectar");
		}
		return con;
	}
	public static void main(String args []) {
		new SQLConnection();
	}
}