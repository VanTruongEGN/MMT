package tcpproduct;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dao {
	public static Connection cnn = null;
	public static Dao instance = null;
	
	private Dao() throws ClassNotFoundException, SQLException {
		if(cnn == null) {
			initConnection();
		}
	}

	public synchronized static void initConnection() throws ClassNotFoundException, SQLException {
		if(cnn==null) {
			String drive = "net.ucanaccess.jdbc.UcanaccessDriver";
			String file = "D:\\hoc tap\\NetworkComputing\\LeVanTruong_23130357_De1\\src\\products.accdb";
			String url = "jdbc:ucanaccess://"+file;
			Class.forName(drive);
			cnn = DriverManager.getConnection(url);
		}
	}
	public synchronized static Dao getInstance() throws ClassNotFoundException, SQLException {
		if(instance==null) {
			instance = new Dao();
		}
		return instance;
	}
	public boolean checkPro(int id) throws SQLException {
		String sql = "SELECT * FROM products WHERE id = ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setInt(1, id);
		ResultSet rs = pre.executeQuery();
		return rs.next();
	}
	public boolean add(Product p) throws SQLException {
		if(checkPro(p.getProductId())) {
			return false;
		}
		String sql = "INSERT INTO products(id, name, count, price) VALUES(?,?,?,?)";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setInt(1, p.getProductId());
		pre.setString(2, p.getName());
		pre.setInt(3, p.getCount());
		pre.setDouble(4, p.getPrice());
		
		int row = pre.executeUpdate();
		return row>0;
	}
	public boolean buy(int id) throws SQLException {
		String sql = "UPDATE products SET count = count - 1 WHERE id = ? AND count >0";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setInt(1, id);
		int row = pre.executeUpdate();
		return row>0;
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
	public List<Product> name(String namep) throws SQLException{
		List<Product> list = new ArrayList<Product>();
		String sql = "SELECT * FROM products WHERE name LIKE ?";
		PreparedStatement pre = cnn.prepareStatement(sql);
		pre.setString(1, "%"+namep+"%");
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

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		Dao dao = getInstance();
		System.out.println(dao.checkPro(1));
	}
	
}
