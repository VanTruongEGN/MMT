package quanlisinhvien;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dao {
	Connection conn;
	public Dao() throws ClassNotFoundException, SQLException {
		String drive = "net.ucanaccess.jdbc.UcanaccessDriver";
		String file = "D:\\hoc tap\\NetworkComputing\\Ontap2\\src\\quanlisinhvien\\data.accdb";
		String url = "jdbc:ucanaccess://"+file;
		Class.forName(drive);
		conn = DriverManager.getConnection(url);
	}
	public boolean checkUser(String user) throws SQLException {
		String sql = "SELECT * FROM user WHERE username = ?";
		PreparedStatement pre = conn.prepareStatement(sql);
		pre.setString(1, user);
		ResultSet rs = pre.executeQuery();
		return rs.next();
	}
	public boolean checkPass(String user, String pass) throws SQLException {
		String sql = "SELECT * FROM user WHERE username = ? AND pass = ?";
		PreparedStatement pre = conn.prepareStatement(sql);
		pre.setString(1, user);
		pre.setString(2, pass);
		ResultSet rs = pre.executeQuery();
		return rs.next();
	}
	public Student findById(int ids) throws SQLException {
		Student st = null;
		String sql = "SELECT * FROM student WHERE id = ?";
		PreparedStatement pre = conn.prepareStatement(sql);
		pre.setInt(1, ids);
		ResultSet rs = pre.executeQuery();
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			double grade = rs.getDouble("grade");
			st = new Student(id, name, grade);
		}
		return st;
	}
	public List<Student> findByName(String names) throws SQLException {
		List<Student> list = new ArrayList<Student>();
		String sql = "SELECT * FROM student WHERE name LIKE ?";
		PreparedStatement pre = conn.prepareStatement(sql);
		pre.setString(1, "%"+names);
		ResultSet rs = pre.executeQuery();
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			double grade = rs.getDouble("grade");
			list.add(new Student(id, name, grade));
		}
		return list;
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Dao dao = new Dao();
		System.out.println(dao.checkUser("marco"));
		System.out.println(dao.findById(1));
		System.out.println(dao.findByName("Truong"));
	}
}
