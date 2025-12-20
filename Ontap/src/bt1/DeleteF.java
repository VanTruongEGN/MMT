package bt1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DeleteF {
	public static boolean deleteF(String path) {
		File file = new File(path);
		
		if(!file.exists()) {
			return true;
		}
		
		if(file.isFile()) {
			return file.delete();
		}
		
		File[] listF = file.listFiles();
		
		if (listF != null) {
			for (File f : listF) {
				if(f.isDirectory()) {
					deleteF(f.getAbsolutePath());
				}else {
					f.delete();
				}
				
			}
		}
		return file.delete();
	}
	public static boolean deleteFile(String path) {
		File file = new File(path);
		
		if(!file.exists()) {
			return true;
		}
		
		if(file.isFile()) {
			return file.delete();
		}
		
		File[] listF = file.listFiles();
		
		if (listF != null) {
			for (File f : listF) {
				if(f.isDirectory()) {
					deleteFile(f.getAbsolutePath());
				}else {
					f.delete();
				}
				
			}
			
		}
		return true;
	}
	public static void copyF(String src, String dest) throws IOException {
		FileInputStream fis = new FileInputStream(src);
		FileOutputStream fos = new FileOutputStream(dest);
		byte[] buff = new byte[1024];
		int byteRead;
		while((byteRead=fis.read(buff))!=-1) {
			fos.write(buff,0, byteRead);
			
		}
		fos.close();
		fis.close();
	}
	
	
	public static void main(String[] args) throws IOException {
		String path = "D:\\hoc tap\\NetworkComputing\\data";
		String src = "D:\\hoc tap\\HCI\\Presentation1.pptx";
		String dest = "D:\\hoc tap\\HCI\\Presentation4.pptx";
		
//		System.out.println(deleteFile(path));
		copyF(src, dest);
	}
}
