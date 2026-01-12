package raf;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class RafList {
	private int totalPro;
	private int recordSize;
	private int nameSize = 40;
	private int headerSize;
	RandomAccessFile raf;
	public RafList(String path) throws IOException {
		raf = new RandomAccessFile(new File(path), "rw");
		headerSize = 4;
		if(raf.length()>0) {
			totalPro = raf.readInt();
		}else {
			raf.writeInt(0);
		}
		recordSize = nameSize + 1 + 4 + 4 +8;
	}
	public void close() throws IOException {
		raf.close();
	}
	public void add(Product p) throws IOException {
		long pos = headerSize + totalPro*recordSize;
		raf.seek(0);
		raf.writeInt(++totalPro);
		raf.seek(pos);
		raf.writeByte(0);
		p.write(raf, nameSize);
	}
	public boolean delete(int productId) throws IOException {
		long pos;
		for (int i = 0; i < totalPro; i++) {
			pos = headerSize + i*recordSize;
			raf.seek(pos);
			int deleted = raf.readByte();
			int id = raf.readInt();
			if(id==productId) {
				raf.seek(pos);
				raf.writeByte(1);
				return true;
			}
		}
		return false;
	}
	public List<Product> list() throws IOException{
		List<Product> list = new ArrayList<>();
		long pos;
		for (int i = 0; i < totalPro; i++) {
			pos = headerSize + i*recordSize;
			raf.seek(pos);
			Product p = new Product();
			int deleted = raf.readByte();
			if(deleted!=1) {
				p.read(raf, nameSize);
				list.add(p);
			}

		}
		return list;
	}
	public boolean buy(int id) throws IOException {
		long pos;
		for (int i = 0; i < totalPro; i++) {
			pos = headerSize + i*recordSize;
			raf.seek(pos);
			int deleted = raf.readByte();
			Product p = new Product();
			p.read(raf, nameSize);
			if(p.getId()==id && p.getQty()>0 && deleted==0) {
				p.setQty(p.getQty()-1);
				raf.seek(pos);
				raf.readByte();
				p.write(raf, nameSize);
				return true;
				
			}
		}
		return false;
	}
	public boolean update(int productId, Product newPro) throws IOException {
		long pos;
		for (int i = 0; i < totalPro; i++) {
			pos = headerSize + i*recordSize;
			raf.seek(pos);
			int deleted = raf.readByte();
			Product p = new Product();
			p.read(raf, nameSize);
			if(p.getId()==productId && deleted==0) {
				p = newPro;
				raf.seek(pos+1);
				p.write(raf, nameSize);
				return true;
			}
		}
		return false;
	}
	
	
	
	public static void main(String[] args) throws IOException {
		String path="D:\\hoc tap\\NetworkComputing\\Ontap3\\src\\raf\\product.data";
		RafList raf = new RafList(path);
//		raf.add(new Product(0, "Thịt cá", 20, 5000));
//		raf.add(new Product(1, "Thịt heo", 20, 6000));
//		raf.add(new Product(2, "Thịt bò", 20, 7000));
		System.out.println(raf.delete(0));
		System.out.println(raf.list());
		System.out.println(raf.buy(1));
		System.out.println(raf.list());
		System.out.println(raf.update(1, new Product(1, "thịt chó", 20, 5000)));
		System.out.println(raf.list());
		raf.close();
	}
}
