package bt3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RAF {
	
	public static void pack(String src , String packFile) throws IOException {
		File folder = new File(src);
		if(!folder.exists() || !folder.isDirectory()) return;
		RandomAccessFile raf = new RandomAccessFile(new File(packFile), "rw");
		List<File> listF = getFile(src);
		long[] header = new long[listF.size()];
		int index = 0;
		long pos;
		//header
		raf.writeInt(listF.size()); // so luong file
		for (File file : listF) {
			header[index++] = raf.getFilePointer();
			raf.writeLong(0);
			raf.writeUTF(file.getName());
			raf.writeLong(file.length());
		}
		index=0;
		//data
		for (File file : listF) {
			pos = raf.getFilePointer();
			raf.seek(header[index++]);
			raf.writeLong(pos);
			raf.seek(pos);
			FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
			byte[] buff = new byte[102400];
			int byteRead;
			while((byteRead=fis.read(buff))!=-1) {
				raf.write(buff,0,byteRead);
			}
			fis.close();
		}
		raf.close();
				
	}
	public static boolean unPack(String packFile, String extractFile, String destFile) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(new File(packFile), "rw");
		int numFile = raf.readInt();
		String name;
		long pos,len;
		for (int i = 0; i < numFile; i++) {
			pos = raf.readLong();
			name = raf.readUTF();
			len = raf.readLong();
			if(name.equalsIgnoreCase(extractFile)) {
				FileOutputStream fos = new FileOutputStream(destFile);
				raf.seek(pos);
				byte[] buff = new byte[102400];
				int byteRead;
				while((byteRead=raf.read(buff))!=-1) {
					fos.write(buff, 0, byteRead);
				}
				fos.close();
				return true;
			}
		}
		return false;
	}
	private static List<File> getFile(String folder) {
		// TODO Auto-generated method stub
		List<File> list = new ArrayList<File>();
		File dir = new File(folder);
		for (File f : dir.listFiles())
			if (f.isFile())
				list.add(f);
		return list;
	}
	public static void main(String[] args) throws IOException {
		String src = "D:\\hoc tap\\data";
		String dest = "D:\\hoc tap\\data.xlsx";
//		pack(src, src+".zip");
		String packFile = "D:\\hoc tap\\data.zip";
		
		System.out.println(unPack(packFile, "data.xlsx", dest));
		
		
 	}
}
