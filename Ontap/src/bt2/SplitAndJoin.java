package bt2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;

public class SplitAndJoin {
	public static void split(String src, long psize) throws IOException {
		FileInputStream fis;
		FileOutputStream fos;
		File file = new File(src);
		if(!file.exists()) return;
		int countFile =(int)(file.length()/psize);
		long remainder = file.length()%psize;
		byte[] buff;
		int byteRead;
		int count=1;
		for (int i = 0; i < countFile; i++) {
			buff = new byte[(int) psize];
			fis = new FileInputStream(file.getAbsolutePath());
			byteRead = fis.read(buff);
			fos = new FileOutputStream(file.getAbsolutePath()+".00"+count);
			fos.write(buff, 0, byteRead);
			count++;
			fis.close();
			fos.close();
		}
		if(remainder>0) {
			int rest = (int) (remainder<psize?remainder:psize);
			buff = new byte[rest];
			fis = new FileInputStream(file.getAbsolutePath());
			byteRead = fis.read(buff);
			fos = new FileOutputStream(file.getAbsolutePath()+createEnd(count));
			fos.write(buff, 0, byteRead);
			fis.close();
			fos.close();
		}
		
	}
	public static String createEnd(int number) {
		return number<10? ".00"+number:(number<100?".0"+number:"."+number);
	}
	public static void join(String src, String dest) throws IOException {

	    FileOutputStream fos = new FileOutputStream(dest);
	    byte[] buffer = new byte[20000];
	    int bytesRead;
	    int count = 1;

	    while (true) {
	        String partName = src + createEnd(count);
	        File partFile = new File(partName);

	        if (!partFile.exists()) break;

	        FileInputStream fis = new FileInputStream(partFile);
	        while ((bytesRead = fis.read(buffer)) != -1) {
	            fos.write(buffer, 0, bytesRead);
	        }
	        fis.close();
	        count++;
	    }

	    fos.close();
	    System.out.println("Join xong!");
	}
	public static void main(String[] args) throws IOException {
		String src = "D:\\hoc tap\\HCI\\Presentation1.pptx";
		split(src, 20000);
		join(src, "D:\\\\hoc tap\\\\HCI\\\\Presentation2.pptx");
	}
}
