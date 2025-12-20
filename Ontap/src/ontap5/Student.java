package ontap5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Student {
	private int id;
	private String name;
	private double grade;
	public Student(int id, String name, double grade) {
		this.id = id;
		this.name = name;
		this.grade = grade;
	}
	public Student() {
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
	public double getGrade() {
		return grade;
	}
	public void setGrade(double grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", grade=" + grade + "]";
	}
	public void write(RandomAccessFile raf, int nameSize) throws IOException {
		raf.writeInt(id);
		int len = nameSize/2;
		char c;
		for (int i = 0; i <len; i++) {
			c=0;
			if(i<name.length()) {
				c=name.charAt(i);
			}
			raf.writeChar(c);
		}
		raf.writeDouble(grade);
	}
	public void read(RandomAccessFile raf, int nameSize) throws IOException {
		id=raf.readInt();
		int len = nameSize/2;
		name="";
		char c;
		for (int i = 0; i <len; i++) {
			 c=raf.readChar();
			 if(c!=0) {
				 name+=c;
			 }
		}
		grade=raf.readDouble();
		
	}
	
	
}
