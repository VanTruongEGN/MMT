package cau1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class PackUnPack {
	public static final int fileNameSizeSet = 40;
	public static void pack(String folder, String packedFile, String extension) throws IOException {
		List<File> listF = getList(folder, extension);
		RandomAccessFile raf = new RandomAccessFile(packedFile, "rw");
		raf.writeInt(listF.size());
		raf.writeInt(fileNameSizeSet);
		for (File file : listF) {
			raf.writeLong(file.length());
			int nameLen = fileNameSizeSet/2;
			String nameFile = file.getName();
			char c;
			for (int i = 0; i < fileNameSizeSet; i++) {
				c=' ';
				if(i<nameFile.length()) {
					c=nameFile.charAt(i);
				}
				raf.writeChar(c);
			}
		}
		for (File file : listF) {
			byte[] buffer = new byte[102400];
			int len;
			FileInputStream fis = new FileInputStream(file);
			while ((len = fis.read(buffer)) != -1) {
				raf.write(buffer, 0, len);
			}
			fis.close();
		}
		raf.close();
	}
	 public static boolean unPack(String packedFile, String extractFile, String destFile)throws IOException {

	        RandomAccessFile raf = new RandomAccessFile(packedFile, "r");

	        int totalFile = raf.readInt();
	        int fileNameSize = raf.readInt();

	        long[] sizes = new long[totalFile];
	        String[] names = new String[totalFile];

	        for (int i = 0; i < totalFile; i++) {
	            sizes[i] = raf.readLong();

	            String fileName = "";
	            for (int j = 0; j < fileNameSize; j++) {
	                char c = raf.readChar();
	                if (c != ' ') {
	                    fileName += c;
	                }
	            }
	            names[i] = fileName;
	        }
	        long posData = raf.getFilePointer();
	        long offset = posData;
	        for (int i = 0; i < totalFile; i++) {
				if(names[i].equals(extractFile)) {
					raf.seek(offset);
					FileOutputStream fos = new FileOutputStream(destFile);
					byte[] buffer = new byte[102400];
					long remain = sizes[i];
					int len;
					while(remain>0 && (len=raf.read(buffer))!=-1) {
						int byteRead = (int) (remain>len?len:remain);
						fos.write(buffer, 0, byteRead);
						remain-=byteRead;
					}
					fos.close();
					raf.close();
					return true;
				}
				offset+=sizes[i];
			}
	        raf.close();
	        return false;
	    }


	public static List<File> getList(String folder, String extension) {
		List<File> listF = new ArrayList<>();
		File file = new File(folder);
		File[] fileEx = file.listFiles();
		for (File f : fileEx) {
			if (f.getName().endsWith(extension)) {
				listF.add(f);
			}
		}
		return listF;
	}

	public static void main(String[] args) throws IOException {
		String src = "D:\\hoc tap\\data";
		String filePack = "D:\\hoc tap\\data.pack";

		pack(src, filePack,".xlsx");
//		unPack(filePack, "data.xlsx", "D:\\hoc tap\\data(extract).xlsx");
		System.out.println(unPack(filePack, "data4.xlsx", "D:\\hoc tap\\data4(extract).xlsx"));

	}
}
