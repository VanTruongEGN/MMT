package ontap4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import onbt4.PackAndUnpack;

public class RAFList {
	public static void packF(String folder, String packedFile) throws IOException{
		List<File> listF = getList(folder);
		RandomAccessFile raf = new RandomAccessFile(packedFile, "rw");
		int FileNumber = listF.size();
		raf.writeInt(FileNumber);
		long[] header = new long[listF.size()];
		int index = 0;
		for (File f : listF) {
			header[index++] = raf.getFilePointer();
			raf.writeLong(0);
			raf.writeLong(f.length());
			raf.writeUTF(f.getName());
		}
		index=0;
		long pos;
		for (File f : listF) {
			pos = raf.getFilePointer();
			raf.seek(header[index++]);
			raf.writeLong(pos);
			raf.seek(pos);
			byte[] buff = new byte[102400];
			int byteRead;
			FileInputStream fis = new FileInputStream(f);
			while((byteRead=fis.read(buff))!=-1) {
				raf.write(buff, 0, byteRead);
			}
			fis.close();
		}
		raf.close();
	}
	public static void unPack(String packedFile, String extractFile, String destFile) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(packedFile, "rw");
		int numFile = raf.readInt();
		long pos;
		long len;
		String name;
		for (int i = 0; i < numFile; i++) {
			pos = raf.readLong();
			len = raf.readLong();
			name = raf.readUTF();
			if(name.equalsIgnoreCase(extractFile)) {
				raf.seek(pos);
				byte[] buff = new byte[102400];
				int byteRead;
				FileOutputStream fos = new FileOutputStream(new File(destFile));
				while (len>0 &&(byteRead=raf.read(buff))!=-1) {
					int remain = (int) (len>byteRead?byteRead:len);
					fos.write(buff, 0, remain);
					len-=remain;
				}
				fos.close();
				break;
			}
		}
		raf.close();
	}
	public static List<File> getList(String folder){
		File file = new File(folder);
		List<File> list = new ArrayList<>();
		for (File f : file.listFiles()) {
			if(f.isFile()) {
				list.add(f);
			}
		}
		return list;
	}
	public static void main(String[] args) throws IOException {
		String src = "D:\\hoc tap\\data";
		String filePack = "D:\\hoc tap\\data.pack";
		
//		packF(src, filePack);
		unPack(filePack, "data.xlsx", "D:\\hoc tap\\data(extract).xlsx");

		
		
	}
}
