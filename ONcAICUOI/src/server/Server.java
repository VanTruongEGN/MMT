package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(55555);
		while(true) {
			Socket socket = serverSocket.accept();
			new ServerProcess(socket).start();
		}
	}
}
