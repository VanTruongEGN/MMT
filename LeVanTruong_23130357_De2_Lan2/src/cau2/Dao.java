package cau2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Dao{
	Connection cnn;
	public Dao() throws ClassNotFoundException, SQLException {
		String drive = "net.ucanaccess.jdbc.UcanaccessDriver";
		String file = "D:\\hoc tap\\NetworkComputing\\LeVanTruong_23130357_De2_Lan2\\src\\bank.accdb";
		String url ="jdbc:ucanaccess://"+file;
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

	 
	public String getNumberAccount(String username) throws SQLException   {
		String sql = "SELECT numberAccount FROM users WHERE username = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, username);
		ResultSet rs = pre.executeQuery();
		if(rs.next()) {
			return rs.getString(1);
		}
		return null;
	}

	 
	public double getBalance(String numberAccount)  throws SQLException  {
		String sql = "SELECT balance FROM users WHERE numberAccount = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, numberAccount);
		ResultSet rs = pre.executeQuery();
		if(rs.next()) {
			return rs.getDouble(1);
		}
		return -1;
	}

	 
	public boolean deposit(String numberAccount, double value)throws SQLException    {
		String sql = "UPDATE users SET balance = balance + value WHERE numberAccount = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, numberAccount);
		int row = pre.executeUpdate();
		return row>0;
	}

	 
	public boolean withdraw(String numberAccount, double value)  throws SQLException  {
		if(getBalance(numberAccount)<value) {
			return false;
		}
		String sql = "UPDATE users SET balance = balance - value WHERE numberAccount = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, numberAccount);
		int row = pre.executeUpdate();
		return row>0;
	}

	 
	public List<Report> getReport(String numberAccount) throws SQLException   {
		List<Report> list = new ArrayList<Report>();
		String sql = "SELECT * FROM logs WHERE numberAccount = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, numberAccount);
		ResultSet rs = pre.executeQuery();
		while(rs.next()) {
			String number = rs.getString("numberAccount");
			LocalDate date = rs.getDate("date").toLocalDate();
			String action = rs.getString("action");
			double value = rs.getDouble("value");
			list.add(new Report(number, date, action, value));
		}
		return list;
	}

	 
	public void insertReport(String numberAccount, String action, double value)throws SQLException  {
		LocalDate date = LocalDate.now();
		String sql = "INSERT INTO logs(numberAccount, date, action, value) VALUES(?,?,?,?)";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, numberAccount);
		pre.setDate(2, java.sql.Date.valueOf(date));
		pre.setString(3, action);
		pre.setDouble(4, value);
		pre.executeUpdate();
	}
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Dao dao = new Dao();
		System.out.println(dao.checkUser("marco"));
	}
}
