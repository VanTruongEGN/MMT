package cau1;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Song {
	private String genre;
	private String name;
	private float size;
	public Song(String genre, String name, float size) {
		super();
		this.genre = genre;
		this.name = name;
		this.size = size;
	}
	public Song() {
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getSize() {
		return size;
	}
	public void setSize(float size) {
		this.size = size;
	}
	@Override
	public String toString() {
		return "genre=" + genre + ", name=" + name + ", size=" + size + "\n";
	}
	public void write(RandomAccessFile raf, int nameSize) throws IOException {
		switch (genre) {
		case "POP": {
			raf.writeByte(1);
			break;
		}
		case "ROCK": {
			raf.writeByte(2);
			break;
		}
		case "JAZZ": {
			raf.writeByte(3);
			break;
		}
		
		default:
			return ;
		}
		int namelen = nameSize/2;
		char c;
		for (int i = 0; i < namelen; i++) {
			c=0;
			if(i<name.length()) {
				c=name.charAt(i);
			}
			raf.writeChar(c);
		}
		raf.writeFloat(size);
	}
	public void read(RandomAccessFile raf, int nameSize) throws IOException {
		int genre = raf.readByte();
		switch (genre) {
		case 1: {
			this.genre="POP";
			break;
		}
		case 2: {
			this.genre="ROCK";
			break;
		}
		case 3: {
			this.genre="JAZZ";
			break;
		}
		default:
			return;
		}
		int namelen = nameSize/2;
		char c;
		this.name="";
		for (int i = 0; i < namelen; i++) {
			c=raf.readChar();
			if(c!=0) {
				this.name+=c;
			}
		}
		this.size = raf.readFloat();

		
	}
	
}
