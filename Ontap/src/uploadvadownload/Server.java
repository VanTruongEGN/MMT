package uploadvadownload;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket serverSocket;
	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	BufferedOutputStream bos;
	public Server() {
		System.out.println("waiting  for client");
		try {
			serverSocket = new ServerSocket(12345);
			socket = serverSocket.accept();
			netIn = new DataInputStream(socket.getInputStream());
			netOut = new DataOutputStream(socket.getOutputStream());
			System.out.println("Client connected");
			netOut.writeUTF("Hello client");
			netOut.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void download() throws IOException {
	    String command = netIn.readUTF();

	    if (command.equalsIgnoreCase("FileCheckOK")) {
	        String dest = netIn.readUTF();
	        long size = netIn.readLong();

	        bos = new BufferedOutputStream(new FileOutputStream(dest));

	        long byteRead = 0;
	        int data;
	        byte[] buffer = new byte[102400];

	        while (byteRead < size && (data = netIn.read(buffer)) != -1) {
	            bos.write(buffer, 0, data);
	            byteRead += data;
	        }

	        bos.flush();
	        System.out.println("Download success");
	        netOut.writeUTF("UPLOAD_SUCCESS");
	        netOut.flush();
	        bos.close();
	        netIn.close();
	        netOut.close();
	     
	    }
	}

	public static void main(String[] args) throws IOException {
		new Server().download();
	}
}
