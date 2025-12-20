package onbt2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class SplitAndJoin {
	
	public static void splitFile(String src, int pSize) throws IOException {
		File file = new File(src);
		if(file.isDirectory()) {
			return;
		}
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos;
		byte[] buff = new byte[(int) pSize];
		int len;
		int countFile = (int) (file.length()/pSize); 
		long remainder =  (file.length()%pSize); 
		for (int i = 0; i < countFile; i++) {
			fos = new FileOutputStream(file.getAbsolutePath()+createExts(i));
			len = fis.read(buff);
			fos.write(buff, 0, len);
			fos.close();
		}
		if(remainder>0) {
			fos = new FileOutputStream(file.getAbsolutePath()+createExts(countFile));
			len = fis.read(buff);
			fos.write(buff, 0, len);
			fos.close();
		}
		fis.close();
	}
	
	public static String createExts(int numberFile) {
		return numberFile<10?".00"+numberFile:numberFile<100?".0"+numberFile:"."+numberFile;
	}
	public static void joinFile(String pathFile) throws IOException {
		String src = pathFile.substring(0, pathFile.lastIndexOf("."));
		System.out.println(src);
		FileInputStream fis;
		FileOutputStream fos = new FileOutputStream(new File(src+"(copy)"));
		byte[] buff = new byte[1024*1024];
		int len;
		int count = 0;
		String pFile;
		while (true) {
			pFile = src + createExts(count++);
			File file = new File(pFile);
			if(!file.exists()) break;
			fis = new FileInputStream(file);
			while((len=fis.read(buff))!=-1) {
				fos.write(buff,0, len);
			}
			fis.close();
		}
		System.out.println(count);
		fos.close();
	}
	
	public static void main(String[] args) throws IOException {
		String src="D:\\hoc tap\\NetworkComputing\\data\\ETS 2024 final bản đẹp.pdf.005";
//		splitFile(src, 1024*1024);
		joinFile(src);
	}

}
