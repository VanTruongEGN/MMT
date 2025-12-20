package bt6;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StudentManagement {
	public List<Student> loadData(String stFile, String gradeFile, String charset) throws IOException {
		List<Student> list = new ArrayList<>();
		String line;
		StringTokenizer st;
		
		//stFile
		int id, bYear;
		String name;
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(stFile), Charset.forName(charset)));
		br.read();
		while((line = br.readLine()) != null) {
			st = new StringTokenizer(line, "\t");
			id = Integer.parseInt(st.nextToken());
			name = st.nextToken();
			bYear = Integer.parseInt(st.nextToken());
			list.add(new Student(id, name, bYear));
		}
		
		//gradeFile
		br = new BufferedReader(new InputStreamReader(new FileInputStream(gradeFile), Charset.forName(charset)));
		br.read();
		while((line = br.readLine()) != null) {
			st = new StringTokenizer(line, "\t");
			id = Integer.parseInt(st.nextToken());
			int count = 0;
			double total = 0;
			while(st.hasMoreTokens()) {
				count++;
				total += Double.parseDouble(st.nextToken());
			}
			double grade = total / count;
			for(Student s : list) {
				if(id == s.getId()) {
					s.grade = grade;
				}
			}
		}
		return list;
	}
	
	public void export(List<Student> list, String textFile, String charSet) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(textFile), Charset.forName(charSet)));
		String line;
		for(Student st : list) {
    		line = st.id + "\t" + st.name + "\t" + st.bYear + "\t" + st.grade;
    		pw.println(line);
		}
		pw.close();
	}
	public static void main(String[] args) throws IOException {
		StudentManagement m = new StudentManagement();
		List<Student> students = m.loadData("D:\\hoc tap\\NetworkComputing\\Ontap\\src\\bt6\\diem\\sv.txt","D:\\hoc tap\\NetworkComputing\\Ontap\\src\\bt6\\diem\\diem.txt",  "UTF-16BE");
		for (Student student : students) {
			System.out.println(student);
		}

		m.export(students, "output.txt", "UTF-16BE");
	}
}
