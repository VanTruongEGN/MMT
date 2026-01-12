package cau1;

import java.io.IOException;
import java.io.RandomAccessFile;

public class SongManagement {
	RandomAccessFile raf;
	int totalSong;
	int headerSize = 4;
	int nameSize = 50;
	int recordSize;
	public SongManagement(String path) {
		try {
			raf = new RandomAccessFile(path, "rw");
			if(raf.length()>0) {
				raf.seek(0);
				totalSong = raf.readInt();
			}else {
				totalSong = 0;
				raf.write(totalSong);
			}
			recordSize = nameSize + 1+1+4;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void close() throws IOException {
		raf.close();
	}

	public void addSong(Song song) throws IOException {
		long pos = headerSize + totalSong*recordSize;
		raf.seek(pos);
		raf.writeBoolean(false);
		song.write(raf,nameSize);
		raf.seek(0);
		raf.writeInt(++totalSong);
		
	}
	public boolean delete(String name) throws IOException {
		long pos;
		for (int i = 0; i < totalSong; i++) {
			pos = headerSize+ i*recordSize;
			raf.seek(pos);
			raf.readBoolean();
			Song song = new Song();
			song.read(raf,nameSize);
			System.out.println(song);
			if(song.getName().equalsIgnoreCase(name)) {
				raf.seek(pos);
				raf.writeBoolean(true);
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException {
		String path = "D:\\hoc tap\\NetworkComputing\\LeVanTruong_23130357_De2_Lan2\\src\\cau1\\playlist.data";
		SongManagement sm = new SongManagement(path);
//		sm.addSong(new Song("JAZZ", "Hay Trao cho ANH", 50));
//		sm.addSong(new Song("POP", "Con mua ngang qua", 50));
//		sm.addSong(new Song("ROCK", "Con co be be", 50));
		System.out.println(sm.delete("hay trao cho anh"));
//		System.out.println(raf.update(1, new Song("JAZZ", "SOn tung", 50)));
//		System.out.println(sm.list());
		sm.close();
	}
}
