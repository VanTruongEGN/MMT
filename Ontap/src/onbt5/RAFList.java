package onbt5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RAFList {
	public final int NAME_SIZE = 50;
	private int headerSize = 4 + 4;
	private int nameSize = NAME_SIZE;
	private int count = 0;
	private int recordSize;
	private RandomAccessFile raf;

	public RAFList(String file) throws IOException {
		raf = new RandomAccessFile(new File(file), "rw");
		recordSize = nameSize + 4 + 8;
		if (raf.length() > 0) {
			count = raf.readInt();
			recordSize = raf.readInt();
		} else {
			raf.writeInt(count);
			raf.writeInt(recordSize);
		}
	}

	public void close() throws IOException {
		raf.close();
	}

	public void addStudent(Student st) throws IOException {
		long pos = count * recordSize + headerSize;
		raf.seek(pos);
		st.write(raf, nameSize);
		raf.seek(0);
		count++;
		raf.writeInt(count);
	}

	public Student get(int index) throws IOException {
		long pos = index * recordSize + headerSize;
		raf.seek(pos);

		Student st = new Student();
		st.read(raf, nameSize);
		return st;
	}

	public boolean fixInfor(Student st) throws IOException {
		long pos;
		for (int i = 0; i < count; i++) {
			pos = i*recordSize + headerSize;
			raf.seek(pos);
			
			if(raf.readInt()==st.getId()) {
				raf.seek(pos); 
				st.write(raf, nameSize);
				return true;
			}
		}
		return false;
	}
	public Student findStudent(int id) throws IOException {
		long pos;
		Student temp;
		for (int i = 0; i < count; i++) {
			pos = i*recordSize + headerSize;
			raf.seek(pos);
			
			if(raf.readInt()==id) {
				raf.seek(pos);
				temp = new Student();
				temp.read(raf, nameSize);
				return temp;
			}
		}
		return null;
	}

	public static void main(String[] args) throws IOException {
		String src = "D:\\hoc tap\\NetworkComputing\\Ontap\\sinhvien.dat";
		RAFList raf = new RAFList(src);
//		raf.addStudent(new Student(1,"Le Van Truong", 9));
		System.out.println(raf.get(0));
//		raf.addStudent(new Student(2,"Le Van Truong", 9));
		System.out.println(raf.get(1));
//		raf.addStudent(new Student(3,"Le Van Truong", 9));
		System.out.println(raf.get(2));
		
		System.out.println(raf.fixInfor(new Student(3, "Le Van Tin",9.8)));
		System.out.println(raf.get(2));
		System.out.println(raf.findStudent(1));

	}

}
