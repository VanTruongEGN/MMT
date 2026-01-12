package cau1;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Song {
	private String genre;
	private String name;
	private float size;
	public Song() {
		
	}
	public Song(String genre, String name, float size) {
		super();
		this.genre = genre;
		this.name = name;
		this.size = size;
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
		return "Song [genre=" + genre + ", name=" + name + ", size=" + size + "]";
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
			return;
		}
		char c;
		int len = nameSize/2;
		for (int i = 0; i < len; i++) {
			c='0';
			if(i<name.length()) {
				c=name.charAt(i);
			}
			raf.writeChar(c);
		}
		raf.writeFloat(size);		
	}
	
	public void read(RandomAccessFile raf, int nameSize) throws IOException {
		int kind = raf.readByte();
		switch (kind) {
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
		this.name="";
		char c;
		int len = nameSize/2;
		for (int i = 0; i < len; i++) {
			c=raf.readChar();
			if(c!='0') {
				this.name+=c;
			}
		}
		this.size = raf.readFloat();	
	}
	
}
