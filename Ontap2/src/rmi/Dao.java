package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dao extends UnicastRemoteObject implements ISearch{
	Connection cnn;
	public Dao() throws RemoteException, ClassNotFoundException, SQLException {
		String drive = "net.ucanaccess.jdbc.UcanaccessDriver";
		String file = "D:\\hoc tap\\NetworkComputing\\Ontap2\\src\\quanlisinhvien\\data.accdb";
		String url = "jdbc:ucanaccess://" +file;
		Class.forName(drive);
		cnn=DriverManager.getConnection(url);

	}
	@Override
	public boolean checkUser(String username) throws RemoteException {
		try {
			String sql = "SELECT * FROM user WHERE username = ?";
			PreparedStatement pre = cnn.prepareStatement(sql);
			pre.setString(1, username);
			ResultSet rs = pre.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new RemoteException(e.getMessage());
		}
	}
	@Override
	public boolean login(String username, String pass) throws RemoteException {
		try {
			String sql = "SELECT * FROM user WHERE username = ? AND pass = ?";
			PreparedStatement pre = cnn.prepareStatement(sql);
			pre.setString(1, username);
			pre.setString(2, pass);
			ResultSet rs = pre.executeQuery();
			return rs.next();
		} catch (SQLException e) {
			throw new RemoteException(e.getMessage());
		}
	}
	@Override
	public Student findById(int id) throws RemoteException {
		try {
			Student st = null;
			String sql = "SELECT * FROM student WHERE id = ?";
			PreparedStatement pre = cnn.prepareStatement(sql);
			pre.setInt(1, id);
			ResultSet rs = pre.executeQuery();
			if(rs.next()) {
				int ids = rs.getInt("id");
				String name = rs.getString("name");
				double grade = rs.getDouble("grade");
				st=new Student(ids, name, grade);
			}
			return st;
		} catch (SQLException e) {
			throw new RemoteException(e.getMessage());
		}
	}
	@Override
	public List<Student> findByName(String name) throws RemoteException {
		try {
			List<Student> list = new ArrayList<Student>();
			String sql = "SELECT * FROM student WHERE name LIKE ?";
			PreparedStatement pre = cnn.prepareStatement(sql);
			pre.setString(1, "%"+name);
			ResultSet rs = pre.executeQuery();
			while(rs.next()) {
				int ids = rs.getInt("id");
				String names = rs.getString("name");
				double grade = rs.getDouble("grade");
				list.add(new Student(ids, names, grade));
			}
			return list;
		} catch (SQLException e) {
			throw new RemoteException(e.getMessage());
		}
	}
	public static void main(String[] args) throws ClassNotFoundException, SQLException, RemoteException {
		Dao dao = new Dao();
		System.out.println(dao.checkUser("marco"));
		System.out.println(dao.findById(2));
		System.out.println(dao.findByName("marco"));
	}
}
