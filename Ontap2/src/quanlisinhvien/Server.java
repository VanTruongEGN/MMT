package quanlisinhvien;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(54321);
		System.out.println("Waiting");
		while(true) {
			Socket socket = serverSocket.accept();
			new ServerProcess(socket).start();
			System.out.println(socket.getLocalAddress()+" connected");
		}
	}
	
}
