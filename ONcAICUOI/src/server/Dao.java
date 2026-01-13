package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Dao {
	public Connection cnn;
	

	 public Dao() throws ClassNotFoundException, SQLException {
	        initConnect();
	    }

	    private void initConnect() throws SQLException, ClassNotFoundException {
	        String drive = "net.ucanaccess.jdbc.UcanaccessDriver";
	        String file = "D:\\hoc tap\\NetworkComputing\\LeVanTruong_23130357_De2_Lan2\\src\\bank.accdb";
	        String url = "jdbc:ucanaccess://" + file;
	        Class.forName(drive);
	        cnn = DriverManager.getConnection(url);
	    }
	 
	public boolean checkUser(String username) throws SQLException   {
		String sql = "SELECT * FROM users WHERE username = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, username);
		ResultSet rs = pre.executeQuery();
		return rs.next();
	}

	 
	public boolean login(String username, String pass)throws SQLException   {
		String sql = "SELECT * FROM users WHERE username = ? AND pass = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, username);
		pre.setString(2, pass);
		ResultSet rs = pre.executeQuery();
		return rs.next();
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Dao dao = new Dao();
		System.out.println(dao.checkUser("marco"));
	}

	
}
