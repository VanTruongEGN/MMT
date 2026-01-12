package raf;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Product {
	private int id;
	private String name;
	private int qty;
	private double price;
	public Product(int id, String name, int qty, double price) {
		super();
		this.id = id;
		this.name = name;
		this.qty = qty;
		this.price = price;
	}
	public Product() {
		
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "id=" + id + ", name=" + name + ", qty=" + qty + ", price=" + price + "\n";
	}
	public void write(RandomAccessFile raf, int nameSize) throws IOException {
		raf.writeInt(id);
		char c;
		int len = nameSize/2;
		for (int i = 0; i < len; i++) {
			c=0;
			if(i<name.length()) {
				c=name.charAt(i);
			}
			raf.writeChar(c);
		}
		raf.writeInt(qty);
		raf.writeDouble(price);
	}
	public void read(RandomAccessFile raf, int nameSize) throws IOException {
		this.id = raf.readInt();
		char c;
		int len = nameSize/2;
		this.name="";
		for (int i = 0; i < len; i++) {
			c=raf.readChar();
			if(c!=0) {
				name+=c;
			}
		}
		this.qty =raf.readInt();
		this.price=raf.readDouble();
	}
	
}
