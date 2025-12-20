package ontap5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RAFList {
	private int studentNumber = 0;
	private int headerSize = 8;
	private int nameSize = 50;
	private int recordSize;
	RandomAccessFile raf;

	public RAFList(String src) throws IOException {
		raf = new RandomAccessFile(new File(src), "rw");
		if (raf.length() > 0) {
			studentNumber = raf.readInt();
			recordSize = raf.readInt();
		} else {
			recordSize = nameSize + 4 + 8;
			raf.writeInt(studentNumber);
			raf.writeInt(recordSize);
		}
	}

	public void addStudent(Student st) throws IOException {
		long pos = studentNumber * recordSize + headerSize;
		raf.seek(0);
		studentNumber++;
		raf.writeInt(studentNumber);
		raf.seek(pos);
		st.write(raf, nameSize);
	}

	public Student getStudent(int index) throws IOException {
		Student st = new Student();
		long pos = index * recordSize + headerSize;
		raf.seek(pos);
		st.read(raf, nameSize);
		return st;
	}
	public void updateStudent(int index, Student newSt) throws IOException{
		long pos = index * recordSize + headerSize;
		raf.seek(pos);
		newSt.write(raf, nameSize);
	}
	public Student findStudent(int id) throws IOException {
		Student st=new Student();
		raf.seek(0);
		int countS = raf.readInt();
		long pos;
		for (int i = 0; i < countS; i++) {
			pos = i*recordSize + headerSize;
			raf.seek(pos);
			st.read(raf, nameSize);
			if(st.getId()==id) {
				return st;
			}
		}
		return null;
	}
			

	public static void main(String[] args) throws IOException {
		String src = "D:\\hoc tap\\NetworkComputing\\data\\sv.txt";
		RAFList raf = new RAFList(src);
		raf.updateStudent(1,new Student(2, "Lê Vân Tín", 9));
		System.out.println(raf.findStudent(2));
	
		
	}
}
