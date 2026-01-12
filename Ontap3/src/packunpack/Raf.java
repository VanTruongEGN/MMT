package packunpack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class Raf {
	
	public static void pack(String folder, String packedFile) throws IOException {
		List<File> listF = getList(folder);
		RandomAccessFile raf = new RandomAccessFile(packedFile, "rw");
		int index =0;
		long[] headerPos = new long[listF.size()];
		raf.writeInt(listF.size());
		for (File file : listF) {
			headerPos[index++] = raf.getFilePointer();
			raf.writeLong(0);
			raf.writeLong(file.length());
			raf.writeUTF(file.getName());
			
		}
		index=0;
		for (File file : listF) {
			long pos = raf.getFilePointer();
			raf.seek(headerPos[index++]);
			raf.writeLong(pos);
			raf.seek(pos);
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[102400];
			int len;
			while((len=fis.read(buffer))!=-1) {
				raf.write(buffer, 0, len);
			}
			fis.close();
		}
		raf.close();
		
	}
	public static void unPack(String packedFile, String extractFile, String destFile) throws IOException {
		RandomAccessFile raf = new RandomAccessFile(packedFile, "rw");
		long pos;
		long lenF;
		String name;
		int numF = raf.readInt();
		for (int i = 0; i < numF; i++) {
			pos = raf.readLong();
			lenF = raf.readLong();
			name = raf.readUTF();
			if(name.equalsIgnoreCase(extractFile)) {
				raf.seek(pos);
				FileOutputStream fos = new FileOutputStream(destFile);
				byte[] buffer = new byte[102400];
				int byteRead;
				while (lenF>0 && (byteRead = raf.read(buffer))!=-1) {
					int remain = (int) (lenF>byteRead?byteRead:lenF);
					fos.write(buffer, 0, remain);
					lenF-=remain;
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
		
		pack(src, filePack);
//		unPack(filePack, "data.xlsx", "D:\\hoc tap\\data(extract).xlsx");
	}

}
