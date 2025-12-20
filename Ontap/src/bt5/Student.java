package bt5;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Student {
		int id;
		String name;
		double grade;

		public Student(int id, String name, double grade) {
			super();
			this.id = id;
			this.name = name;
			this.grade = grade;
		}

		public Student() {
			super();
		}

		@Override
		public String toString() {
			return id + "\t" + name + "\t" + grade;
		}

		public void write(RandomAccessFile raf, int nameSize) throws IOException {
			raf.writeInt(id);
			// name
			int len = nameSize / 2;
			char c;
			for (int i = 0; i < len; i++) {
				c = 0;
				if (i < name.length())
					c = name.charAt(i);
				raf.writeChar(c);
			}
			raf.writeDouble(grade);

		}

		public void read(RandomAccessFile raf, int nameSize) throws IOException {
			id = raf.readInt();
			// name
			name ="";
			int len = nameSize / 2;
			char c;
			for (int i = 0; i < len; i++) {
				c = raf.readChar();
				if (c != 0)
					this.name += c;
			}
			grade = raf.readDouble();
		}
	}

