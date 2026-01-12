package ontapUpLoad;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerProcess extends Thread{
	Socket socket;
	DataInputStream netIn;
	DataOutputStream netOut;
	public ServerProcess(Socket socket) {
		this.socket = socket;
		try {
			netIn = new DataInputStream(socket.getInputStream());
			netOut = new DataOutputStream(socket.getOutputStream());
		} catch (Exception e) {
			// TODO: handle exception
			e.getStackTrace();
		}
	}
	
	public void run() {
		try {
			netOut.writeUTF("System is ready");
			netOut.flush();
			String command;
			while(true) {
				command = netIn.readUTF();
				if(command.equalsIgnoreCase("EXIT")) {
					netOut.writeUTF("Goodbye Client");
				    netOut.flush();
				    break;
				}
				StringTokenizer token = new StringTokenizer(command);
				String request = token.nextToken();
				String src = token.nextToken();
				String dest = token.nextToken();
				int data;
				byte[] buffer = new byte[102400];
				FileOutputStream fos = new FileOutputStream(new File(dest));
				long size = netIn.readLong();
				
				while(size>0) {
					int byteRead = (int) ( size>buffer.length?buffer.length:size);
					data=netIn.read(buffer, 0, byteRead);
					if (data == -1) break; 
				    fos.write(buffer, 0, data);
				    size -= data;
				}
				netOut.flush();
				netOut.writeUTF("Download success");
				netOut.flush();
				fos.close();
			}
			socket.close();
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
	
}
