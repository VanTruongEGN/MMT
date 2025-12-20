package bt5;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RAFList {
	private static final int NAME_SIZE = 50;
	RandomAccessFile raf;
	int recordSize;
	int headerSize=4+4;
	int count = 0;
	int nameSize = NAME_SIZE;
	public RAFList(String file) throws IOException {
		raf = new RandomAccessFile(new File(file),"rw");
		recordSize = nameSize + 4 + 8;
		if (raf.length()>0) {
			count = raf.readInt();
			recordSize = raf.readInt();
		}else {
			raf.write(count);
			raf.write(recordSize);
		}
	}
	public void addStudent(Student st) throws IOException {
		long pos = count *recordSize + headerSize;
		raf.seek(pos);
		st.write(raf, nameSize);
		count++;
		raf.seek(0);
		raf.writeInt(count);
	}
	public Student get(int index) throws IOException {
		long pos = index * recordSize + headerSize;
		raf.seek(pos);
		Student st = new Student();
		st.read(raf, nameSize);
		return st;
	}
	public static void main(String[] args) throws IOException {
		String src = "D:\\hoc tap\\NetworkComputing\\Ontap\\sinhvien.txt";
		RAFList raf = new RAFList(src);
		raf.addStudent(new Student(0,"Le Van Truong", 9));
		System.out.println(raf.get(1));
	}
}
