package onbt5;

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
	public void write(RandomAccessFile raf, int nameSize) throws IOException {
		raf.writeInt(id);
		int len = nameSize/2;
		char c;
		for (int i = 0; i < len; i++) {
			c=0;
			if(i<name.length()) {
				c = name.charAt(i);
			}
			raf.writeChar(c);
		}
		raf.writeDouble(grade);
	}
	public void read(RandomAccessFile raf, int nameSize) throws IOException {
		this.id = raf.readInt();
		int len = nameSize/2;
		this.name="";
		char c;
		for (int i = 0; i < len; i++) {
			c=raf.readChar();
			if(c!=0) {
				this.name+=c;
			}
		}
		this.grade=raf.readDouble();
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
	
	
}
