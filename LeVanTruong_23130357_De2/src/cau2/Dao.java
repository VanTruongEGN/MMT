package cau2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Dao {
	Connection cnn;

    private static Dao instance;

    public static synchronized Dao getInstance() throws ClassNotFoundException, SQLException {
        if (instance == null) {
            instance = new Dao();
        }
        return instance;
    }
    
	public Dao() throws SQLException, ClassNotFoundException {
		String drive = "net.ucanaccess.jdbc.UcanaccessDriver";
		String file = "D:\\hoc tap\\NetworkComputing\\LeVanTruong_23130357_De2\\src\\Database1.accdb";
		String url = "jdbc:ucanaccess://" + file;
		Class.forName(drive);
		cnn = DriverManager.getConnection(url);
	}

	public synchronized boolean checkUser(String username) throws SQLException {
		String sql = "SELECT * FROM user WHERE username = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, username);
		ResultSet rs = pre.executeQuery();
		return rs.next();
	}

	public synchronized boolean login(String username, String pass) throws SQLException {
		String sql = "SELECT * FROM user WHERE username = ? AND pass = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, username);
		pre.setString(2, pass);
		ResultSet rs = pre.executeQuery();
		return rs.next();
	}

	public synchronized double getBalance(String accountNumber) throws SQLException {
		String sql = "SELECT balance FROM user WHERE number = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, accountNumber);
		ResultSet rs = pre.executeQuery();
		if(rs.next()) {
			return rs.getDouble(1);
		}
		return -1;
	}

	public synchronized boolean deposit(String accountNumber, double value) throws SQLException {
		if (getBalance(accountNumber)<value) {
			return false;
		}
		String sql = "UPDATE user SET balance = balance - ? WHERE number = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setDouble(1, value);
		pre.setString(2, accountNumber);
		int rows = pre.executeUpdate();
		return rows > 0;
	}

	public synchronized void insertLog(String accountNumber, String action, double value) throws SQLException {
		LocalDate date = LocalDate.now();
		String sql = "INSERT INTO log(number, date, action, value) VALUES(?,?,?,?)";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, accountNumber);
		pre.setDate(2, java.sql.Date.valueOf(date));
		pre.setString(3, action);
		pre.setDouble(4, value);
		pre.executeUpdate();
	}

	public synchronized boolean withdraw(String accountNumber, double value) throws SQLException {
		String sql = "UPDATE user SET balance = balance + ? WHERE number = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setDouble(1, value);
		pre.setString(2, accountNumber);
		int rows = pre.executeUpdate();
		return rows > 0;
	}

	public synchronized List<Log> getLog(String accountNumber) throws SQLException {
		List<Log> list = new ArrayList<Log>();
		String sql = "SELECT * FROM log WHERE number = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, accountNumber);
		ResultSet rs = pre.executeQuery();
		while (rs.next()) {
			String numberAccount = rs.getString("number");
			LocalDate date = rs.getDate("date").toLocalDate();
			String action = rs.getString("action");
			double value = rs.getDouble("value");
			list.add(new Log(numberAccount, date, action, value));
		}
		return list;
	}
	public synchronized String getNumberAccount(String username) throws SQLException {
		String sql = "SELECT number FROM user WHERE username = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, username);
		ResultSet rs = pre.executeQuery();
		if(rs.next()) {
			return rs.getString("number");
		}
		return null;
		
	}
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
	    Dao dao = new Dao();
	    System.out.println(dao.getNumberAccount("vantruong"));
	}


}
