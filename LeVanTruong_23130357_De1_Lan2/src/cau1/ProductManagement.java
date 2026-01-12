package cau1;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class ProductManagement {
	RandomAccessFile raf;
	int headerSize = 4;
	int nameSize = 40;
	int recordSize;
	int totalPro;
	public ProductManagement(String path) {
		try {
			raf = new RandomAccessFile(new File(path), "rw");
			if(raf.length()>0) {
				raf.seek(0);
				totalPro = raf.readInt();
			}else {
				totalPro =0 ;
				raf.writeInt(totalPro);
			}
			recordSize = nameSize+1+4+4+8;
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void close() throws IOException {
		raf.close();
	}
	public void add(Product p) throws IOException {
		long pos = headerSize + totalPro*recordSize;
		raf.seek(pos);
		raf.writeByte(0);
		p.write(raf, nameSize);
		raf.seek(0);
		raf.writeInt(++totalPro);
	}
	public boolean delete(int productId) throws IOException {
		long pos;
		for (int i = 0; i < totalPro; i++) {
			pos = headerSize + i*recordSize;
			raf.seek(pos);
			System.out.println(pos);
			raf.readByte();
			int id = raf.readInt();
			System.out.println(id);
			if(id==productId) {
				raf.seek(pos);
				raf.writeByte(1);
				return true;
			}
		}
		return false;
	}
	public List<Product> list() throws IOException{
		List<Product> listP = new ArrayList<Product>();
		long pos;
		for (int i = 0; i < totalPro; i++) {
			pos = headerSize + i*recordSize;
			raf.seek(pos);
			if(raf.readByte()==0) {
				Product p = new Product();
				p.read(raf, nameSize);
				listP.add(p);
			}
		}
		return listP;
	}
	public boolean update(int productId, Product p) throws IOException {
		long pos;
		for (int i = 0; i < totalPro; i++) {
			pos = headerSize + i*recordSize;
			raf.seek(pos);
			if(raf.readByte()==0) {
				if(raf.readInt()==productId) {
					raf.seek(pos);
					raf.readByte();
					p.write(raf, nameSize);
					return true;
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		String path = "D:\\hoc tap\\NetworkComputing\\LeVanTruong_23130357_De1_Lan2\\src\\cau1\\product.data";
		ProductManagement pm = new ProductManagement(path);
//		pm.add(new Product(1, "Ca", 20, 100000));
//		pm.add(new Product(2, "bo", 20, 100000));
//		pm.add(new Product(3, "heo", 20, 100000));
		System.out.println(pm.delete(1));
		System.out.println(pm.list());
		System.out.println(pm.update(3, new Product(4, "cho", 10, 3232)));
		System.out.println(pm.list());
		pm.close();
	}
}
