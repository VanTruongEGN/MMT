package cau2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(1080);
		System.out.println("waiting");
		while(true) {
			Socket socket = serverSocket.accept();
			new ServerProcess(socket).start();
			System.out.println(socket.getLocalSocketAddress() + " connected");
		}
	}
}
