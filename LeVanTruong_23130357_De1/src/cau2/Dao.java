package cau2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dao {
	Connection cnn;
	public Dao() throws ClassNotFoundException, SQLException {
		String drive = "net.ucanaccess.jdbc.UcanaccessDriver";
		String file ="D:\\hoc tap\\NetworkComputing\\LeVanTruong_23130357_De1\\src\\products.accdb";
		String url ="jdbc:ucanaccess://"+ file;
		Class.forName(drive);
		cnn = DriverManager.getConnection(url);
	}
	public boolean findProduct(int id) throws SQLException {
		String sql = "SELECT * FROM products WHERE id = ?";
		
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setInt(1, id);
		ResultSet rs = pre.executeQuery();
		return rs.next();
		
	}
	public boolean add(Product p) throws SQLException {
		int id = p.getProductId();
		String name = p.getName();
		int count = p.getCount();
		double price = p.getPrice();
		if(findProduct(id)) {
			return false;
		}
		
		String sql = "INSERT INTO products(id, name, count, price) VALUES(?,?,?,?)";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setInt(1, id);
		pre.setString(2, name);
		pre.setInt(3, count);
		pre.setDouble(4, price);
		int row = pre.executeUpdate();
		
		return row>0;
	}
	public boolean buy(int id) throws SQLException {
		String sql = "UPDATE products SET count = count - 1 WHERE id = ? AND count > 0";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setInt(1, id);
		int rows = pre.executeUpdate();
		return rows > 0;
	}
	public List<Product> price(double from, double to) throws SQLException{
		List<Product> list = new ArrayList<Product>();
		String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setDouble(1, from);
		pre.setDouble(2, to);
		ResultSet rs = pre.executeQuery();
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int count = rs.getInt("count");
			double price = rs.getDouble("price");
			list.add(new Product(id, name, count, price));
		}
		return list;
	}
	public List<Product> name(String nameP) throws SQLException{
		List<Product> list = new ArrayList<Product>();
		String sql = "SELECT * FROM products WHERE name LIKE ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, "%"+nameP+"%");
		ResultSet rs = pre.executeQuery();
		while(rs.next()) {
			int id = rs.getInt("id");
			String name = rs.getString("name");
			int count = rs.getInt("count");
			double price = rs.getDouble("price");
			list.add(new Product(id, name, count, price));
		}
		return list;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Dao dao = new Dao();
		System.out.println(dao.price(1000, 50000));
		System.out.println(dao.name("heo"));
	}
}
