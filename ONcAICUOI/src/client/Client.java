package client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class Client {
	DataInputStream netIn;
	DataOutputStream netOut;
	Socket socket;
	BufferedReader userIn;
	String com,param;
	public Client() {
		try {
			socket = new Socket("127.0.0.1", 55555);
			netIn = new DataInputStream(socket.getInputStream());
			netOut = new DataOutputStream(socket.getOutputStream());
			userIn = new BufferedReader(new InputStreamReader(System.in));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public void run() {
		try {
			System.out.println(netIn.readUTF());
			String res;
			String command;
			while(true) {
				command = userIn.readLine();
			    netOut.writeUTF(command);
			    netOut.flush();
				if(command.equalsIgnoreCase("QUIT")) {
					break;
				}
				System.out.println(netIn.readUTF());
			
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
		
	
	public static void main(String[] args) {
		new Client().run();
	}
}
