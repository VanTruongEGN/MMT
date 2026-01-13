package tcpproduct;

public class Product {
	private int productId;
	private String name;
	private int count;
	private double price;
	public Product(int productId, String name, int count, double price) {
		super();
		this.productId = productId;
		this.name = name;
		this.count = count;
		this.price = price;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name + ", count=" + count + ", price=" + price + "]";
	}
	
}
