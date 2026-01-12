package uploadvadownload;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class ClientUpload {
	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	BufferedReader userIn;
	BufferedInputStream bis;
	
public ClientUpload() {
	try {
		socket = new Socket("localhost", 12345);
		netIn = new DataInputStream(socket.getInputStream());
		netOut = new DataOutputStream(socket.getOutputStream());
		String greeting = netIn.readUTF();
		System.out.println(greeting);
	} catch (Exception e) {
		// TODO: handle exception
	}
}
public void upload() {
	userIn = new BufferedReader(new InputStreamReader(System.in));
	try {
		String command = userIn.readLine();
		StringTokenizer tokenizer = new StringTokenizer(command);
		String request = tokenizer.nextToken();
		String sourceFile = tokenizer.nextToken();
		String destFile = tokenizer.nextToken();
		File fileSource = new File(sourceFile);
		if(!fileSource.exists()) {
			netOut.writeUTF("File not found");
			System.out.println("File not found");
			netOut.flush();
			return;
		}else {
			netOut.writeUTF("FileCheckOK");
			netOut.writeUTF(destFile);
			netOut.writeLong(fileSource.length());
			netOut.flush();
			bis = new BufferedInputStream(new FileInputStream(fileSource));
			int data;
			byte[] buffer = new byte[102400];
			while((data=bis.read(buffer))!=-1) {
				netOut.write(buffer, 0, data);
			}
			netOut.flush();
			System.out.println(netIn.readUTF());
			netOut.close();
			netIn.close();
			bis.close();
			
		}
	} catch (Exception e) {
		// TODO: handle exception
	}
}
public static void main(String[] args) {
	new ClientUpload().upload();
}
}
