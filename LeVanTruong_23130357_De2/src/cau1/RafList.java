package cau1;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class RafList {
	RandomAccessFile raf;
	int nameSize =50;
	int totalSong ;
	int headerSize;
	int recordSize;
	public RafList(String path) throws IOException {
		raf = new RandomAccessFile(path, "rw");
		if(raf.length()>0) {
			totalSong = raf.readInt();
		}else {
			totalSong = 0;
			raf.writeInt(totalSong);
		}
		headerSize = 4;
		recordSize = nameSize + 1 + 1 + 4;
	}
	public void close() throws IOException {
		raf.close();
	}
	
	public void addSong(Song song) throws IOException {
		long pos = headerSize + totalSong*recordSize;
		raf.seek(pos);
		if(checkName(song.getName())) {
			System.out.println("Tên bài hát đã tồn tại");
			return;
		}
		raf.writeBoolean(false);
		song.write(raf, nameSize);
		raf.seek(0);
		raf.writeInt(++totalSong);
	}
	public boolean checkName(String name) throws IOException {
		long pos;
		for (int i = 0; i < totalSong; i++) {
			pos = headerSize + i*recordSize;
			raf.seek(pos);
			Song song = new Song();
			raf.readBoolean();
			song.read(raf, nameSize);
			if(song.getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}
	public boolean delete(String name) throws IOException {
		long pos;
		for (int i = 0; i < totalSong; i++) {
			pos = headerSize + i*recordSize;
			raf.seek(pos);
			Song song = new Song();
			raf.readBoolean();
			song.read(raf, nameSize);
			if(song.getName().equalsIgnoreCase(name)) {
				raf.seek(pos);
				raf.writeBoolean(true);
				return true;
			}
		}
		
		return false;
	} 
	public List<Song> list() throws IOException{
		List<Song> listSong = new ArrayList<>();
		long pos;
		for (int i = 0; i < totalSong; i++) {
			pos = headerSize + i*recordSize;
			raf.seek(pos);
			boolean deleted = raf.readBoolean();
	        Song song = new Song();
	        song.read(raf, nameSize);
	        if (!deleted) {
	        	listSong.add(song);
	        }
		}
		
		return listSong;
	}
	public boolean update(int index, Song song) throws IOException {
		if(index>=list().size()) {
			return false;
		}
		long pos = headerSize + index*recordSize;
		raf.seek(pos);
		if(raf.readBoolean()) {
			return false;
		}
		
		song.write(raf, nameSize);
		return true;
	}
	public static void main(String[] args) throws IOException {
		String path = "D:\\hoc tap\\NetworkComputing\\LeVanTruong_23130357_De2\\src\\cau1\\playlist.data";
		RafList raf = new RafList(path);
//		raf.addSong(new Song("JAZZ", "Hay Trao cho ANH", 50));
//		raf.addSong(new Song("POP", "Con mua ngang qua", 50));
//		raf.addSong(new Song("ROCK", "Con co be be", 50));
		System.out.println(raf.delete("hay trao cho anh"));
		System.out.println(raf.update(1, new Song("JAZZ", "SOn tung", 50)));
		System.out.println(raf.list());
		raf.close();
	}
}
