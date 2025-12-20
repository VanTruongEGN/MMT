package onbt4;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.http.WebSocket.Listener;
import java.util.ArrayList;
import java.util.List;

public class PackAndUnpack {

	public static void pack(String folder, String packFile) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(new File(packFile), "rw");
		List<File> list = getFile(folder);
		int index = 0;
		long[] header = new long[list.size()];
		raf.writeInt(list.size());
		for (File f: list) {
			header[index++] = raf.getFilePointer();
			raf.writeLong(0);
			raf.writeUTF(f.getName());
			raf.writeLong(f.length());
		}
		index=0;
		byte[] buff = new byte[1024*1024*100];
		int len;
		long pos;
		
		for (File f: list) {
			pos = raf.getFilePointer();
			raf.seek(header[index++]);
			raf.writeLong(pos);
			raf.seek(pos);
			FileInputStream fis = new FileInputStream(f);
			while((len=fis.read(buff))!=-1) {
				raf.write(buff, 0, len);
			}
			fis.close();
		}
		raf.close();		
	}

	private static List<File> getFile(String folder) {
		List<File> list = new ArrayList<File>();
		File dir = new File(folder);
		for (File f : dir.listFiles())
			if (f.isFile())
				list.add(f);
		return list;
	}
	public static boolean unpack(String filePack, String fileExtract, String dest) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(new File(filePack), "rw");
		int numberFile = raf.readInt();
		long pos;
		String name;
		long len;
		for (int i = 0; i < numberFile; i++) {
			pos = raf.readLong();
			name = raf.readUTF();
			len = raf.readLong();
			if(name.equalsIgnoreCase(fileExtract)) {
				raf.seek(pos);
				byte[] buff = new byte[102400];
				int byteRead;
				FileOutputStream fos = new FileOutputStream(new File(dest));
				while (len>0 && (byteRead=raf.read(buff))!=-1) {
					int remain = (int) (len > byteRead?byteRead:len);
					fos.write(buff, 0, remain);
					len-=byteRead;
				}
				fos.close();
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) throws IOException {
		String src = "D:\\hoc tap\\data";
		String filePack = "D:\\hoc tap\\data.pack";

//		pack(src, filePack);
		unpack(filePack, "data.xlsx", "D:\\hoc tap\\data(extract).xlsx");
	}
}
