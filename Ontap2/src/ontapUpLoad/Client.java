package ontapUpLoad;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Client {
	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	BufferedReader userIn;
	public Client() throws UnknownHostException, IOException {
		socket = new Socket("localhost", 12345);
		try {
			netIn = new DataInputStream(socket.getInputStream());
			netOut = new DataOutputStream(socket.getOutputStream());
			userIn = new BufferedReader(new InputStreamReader(System.in));
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
	}
	public void run() throws IOException {
		System.out.println(netIn.readUTF());

		while(true) {
			String command = userIn.readLine();
		    netOut.writeUTF(command);
		    netOut.flush();
			if(command.equalsIgnoreCase("EXIT")) {
				System.out.println(netIn.readUTF());
				break;
			}
			StringTokenizer token = new StringTokenizer(command);
			String request = token.nextToken();
			String src = token.nextToken();
			String dest = token.nextToken();
			File file = new File(src);
			netOut.writeLong(file.length());
			int data;
			byte[] buffer = new byte[102400];
			FileInputStream fis = new FileInputStream(file);
			
			while((data=fis.read(buffer))!=-1) {
				netOut.write(buffer, 0, data);
			}
			fis.close();
			netOut.flush();
			System.out.println(netIn.readUTF());
		}
	}
	public static void main(String[] args) throws UnknownHostException, IOException {
		new Client().run();
	}
}
