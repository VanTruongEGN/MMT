package TCP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	BufferedReader netIn;
	PrintWriter netOut;
	Socket socket;
	BufferedReader userIn;
	public Client() throws UnknownHostException, IOException {
		socket = new Socket("localhost",12345);
		netIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		netOut = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
 public void mainProcess() throws IOException {
	 String greeting = netIn.readLine();
	 System.out.println(greeting);
	 while (true) {
		userIn = new BufferedReader(new InputStreamReader(System.in));
		String command = userIn.readLine();
		netOut.println(command);
		netOut.flush();
		String reponse = netIn.readLine();
		System.out.println(reponse);
		if(reponse.equalsIgnoreCase("Bye Client")) {
			netIn.close();
			netOut.close();
			userIn.close();
			break;
		}
		
	}
 }
 public static void main(String[] args) throws UnknownHostException, IOException {
	new Client().mainProcess();
}
}	
